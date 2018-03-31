package com.ddrx.ddrxfront;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ddrx.ddrxfront.Controller.AddCardController;
import com.ddrx.ddrxfront.Model.ModelInput;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddCardAdapter adapter = null;
    public final static int GET_MODEL_INFO = 1;
    public final static int DATA_ERROR = 2;

    private static class MyHandler extends Handler {

        private final WeakReference<AddCardActivity> mActivity;

        public MyHandler(AddCardActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case DATA_ERROR:
                    Toast.makeText(mActivity.get(), "系统错误！", Toast.LENGTH_SHORT).show();
                    break;
                case GET_MODEL_INFO:
                    List<ModelInput> inputs = (List<ModelInput>) msg.obj;
                    mActivity.get().adapter = new AddCardAdapter(inputs, mActivity.get());
                    mActivity.get().recyclerView.setAdapter(mActivity.get().adapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        recyclerView = findViewById(R.id.AC_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        long CT_id = intent.getLongExtra("CT_id", 0);
        AddCardController controller = new AddCardController(new MyHandler(this), this);
        controller.getModelInputs(CT_id);
        Button commit_btn = findViewById(R.id.AC_commit_card);
        commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, AddNewWarehouseActivity.class);
                if(adapter == null){
                    Toast.makeText(AddCardActivity.this, "未选择模型！", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<Integer, View> entry_manager = adapter.getEntryManager();

                }
            }
        });
    }

}