package com.zhx.app.eventbusdemmp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SecActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView receive_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);


        initView();
    }

    private void initView() {
        findViewById(R.id.sendData).setOnClickListener(this);
        findViewById(R.id.receive).setOnClickListener(this);
        receive_data = (TextView)findViewById(R.id.receive_data);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendData:
                //发送事件
                EventBus.getDefault().post(new UserEvent("Mr.sorrow", "123456"));
                finish();
                break;
            case R.id.receive:
                //要接收时开始注册
                EventBus.getDefault().register(SecActivity.this);
                break;
        }
    }

    //处理事件逻辑
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveEventBus(MessageEvent messageEvent) {
        Toast.makeText(this,messageEvent.toString(),Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEventBusdata(MessageEvent messageEvent) {
        receive_data.setText(messageEvent.toString());
        Toast.makeText(this,messageEvent.toString(),Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(SecActivity.this);
    }

}
