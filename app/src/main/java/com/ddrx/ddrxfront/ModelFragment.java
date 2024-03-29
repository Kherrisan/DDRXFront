package com.ddrx.ddrxfront;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ddrx.ddrxfront.Controller.InitUpdateDatabase;
import com.ddrx.ddrxfront.Controller.UpdateModelFragmentController;
import com.ddrx.ddrxfront.Controller.UpdateModelFragmentController;
import com.ddrx.ddrxfront.Model.CardModel;
import com.ddrx.ddrxfront.Model.ModelIntro;
import com.ddrx.ddrxfront.Utilities.OKHttpClientWrapper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModelFragment extends Fragment {


    private RecyclerView model_list_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CardModel> model_list;
    private UpdateModelFragmentController controller;
    private SwipeRefreshLayout swipeRefreshLayout;
    ModelFragment.MyHandler handler;

    public static final int EMPTY_LIST = 2;
    public static final int UPDATE_UI = 3;

    private static class MyHandler extends Handler {

        private final WeakReference<ModelFragment> mFragment;

        public MyHandler(ModelFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case InitUpdateDatabase.NETWORK_ERROR: {
                    Toast.makeText(mFragment.get().getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
                    break;
                }
                case InitUpdateDatabase.UPDATE_MODEL_SUCCESS: {
                    mFragment.get().controller.getModelListFromDB();
                    break;
                }
                case EMPTY_LIST: {
                    Snackbar.make(mFragment.get().model_list_recycler_view, "空空如也", Snackbar.LENGTH_SHORT).setAction("创建新模版", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mFragment.get().getActivity(), AddNewModelActivity.class);
                            mFragment.get().getActivity().startActivity(intent);
                        }
                    }).show();
                    break;
                }
                case UPDATE_UI: {
                    mFragment.get().model_list = (List<CardModel>) msg.obj;
                    ModelListAdapter modelListAdapter = new ModelListAdapter((List<CardModel>) msg.obj);
                    mFragment.get().model_list_recycler_view.setAdapter(modelListAdapter);
//                    modelListAdapter.getModel_list().clear();
//                    modelListAdapter.getModel_list().addAll((List<CardModel>) msg.obj);
//                    modelListAdapter.notifyDataSetChanged();
                    mFragment.get().model_list_recycler_view.getAdapter().notifyDataSetChanged();
                    mFragment.get().swipeRefreshLayout.setRefreshing(false);
                }
            }
        }
    }

    public ModelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("ddrx", "onHiddenChangedModelFragment");
        controller.getModelListFromDB();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model_list = new ArrayList<>();
        handler = new ModelFragment.MyHandler(this);
        controller = new UpdateModelFragmentController(handler, getContext());
        controller.getModelListFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model, container, false);
        model_list_recycler_view = view.findViewById(R.id.model_list);
        model_list_recycler_view.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        model_list_recycler_view.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = view.findViewById(R.id.model_fragment_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                InitUpdateDatabase.updateCardModelDatabase(getContext(), handler, OKHttpClientWrapper.Companion.getInstance(getActivity()));
            }
        });

        return view;
    }

}