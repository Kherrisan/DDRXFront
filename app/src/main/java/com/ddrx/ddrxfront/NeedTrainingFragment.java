package com.ddrx.ddrxfront;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ddrx.ddrxfront.Controller.NeedTrainingController;
import com.ddrx.ddrxfront.Model.NeedTrainingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NeedTrainingFragment extends Fragment {

    private class MyHandler extends Handler {

        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NeedTrainingController.EMPTY_LIST:
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    Snackbar.make(recyclerView, "空空如也的训练场", Snackbar.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case NeedTrainingController.UPDATE_UI:
                    needTrainingItemList = (List<NeedTrainingItem>) msg.obj;
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    break;
            }
//            view.invalidate();
        }
    }

    private List<NeedTrainingItem> needTrainingItemList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private NeedTrainingController controller;
    private View view;

    public NeedTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateNeedTrainingFromDB();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NeedTrainingController(new MyHandler(), getContext());
        needTrainingItemList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_need_training, container, false);
        recyclerView = view.findViewById(R.id.need_training_list);
        swipeRefreshLayout = view.findViewById(R.id.need_trainning_fragment_swipe_refresh);
        progressBar = view.findViewById(R.id.progressbar_need_training_updating);
        textView = view.findViewById(R.id.text_need_training_hint);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(new NeedTrainingAdapter(needTrainingItemList));
        progressBar.setVisibility(View.VISIBLE);

        this.view = view;

        return view;
    }

}
