package com.zhx.app.eventbusdemmp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus的使用
 * 1.从A界面进入B界面，并且把B界面的值传到A界面
 * 方法步骤：1》A界面直接跳转 2》B界面通过EventBus.getDefault().post() 方法传值
 * 3》A界面接收传的值 定义处理接收的方法  @Subscribe(threadMode = ThreadMode.MAIN)
 *
 * 2.粘性事件
 之前说的使用方法，都是需要先注册(register)，再post,才能接受到事件；
 如果你使用postSticky发送事件，那么可以不需要先注册，也能接受到事件，
 也就是一个延迟注册的过程。
 普通的事件我们通过post发送给EventBus，发送过后之后当前已经订阅过
 的方法可以收到。但是如果有些事件需要所有订阅了该事件的方法都能执行呢？
 例如一个Activity，要求它管理的所有Fragment都能执行某一个事件，
 但是当前我只初始化了3个Fragment，如果这时候通过post发送了事件，那么
 当前的3个Fragment当然能收到。但是这个时候又初始化了2个Fragment，那么我必须重新发送事件，
 这两个Fragment才能执行到订阅方法。
 粘性事件就是为了解决这个问题，通过 postSticky 发送粘性事件，这个事件不会只
 被消费一次就消失，而是一直存在系统中，知道被 removeStickyEvent 删除掉。
 那么只要订阅了该粘性事件的所有方法，只要被register 的时候，就会被检测到，并且执行。订阅的方法需要添加 sticky = true 属性。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        initData();

        UserEvent userEvent=new User();
        Log.d("_tag", "onCreate:  "+userEvent.username);
        userEvent.getUser();
    }

    private void initData() {
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    private void initview() {
        findViewById(R.id.tv_anther_activity).setOnClickListener(this);
        findViewById(R.id.tv_send).setOnClickListener(this);
        findViewById(R.id.tv__pusend).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_anther_activity:
                startActivity(new Intent(MainActivity.this, SecActivity.class));
                break;
            case R.id.tv_send:   //
                EventBus.getDefault().postSticky(new MessageEvent("粘性事件", "urgent"));
                startActivity(new Intent(MainActivity.this, SecActivity.class));
                break;
            case R.id.tv__pusend:
                EventBus.getDefault().post(new MessageEvent("普通事件","zhx"));
                startActivity(new Intent(MainActivity.this, SecActivity.class));
                break;
        }
    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UserEvent userEvent) {
        Toast.makeText(this, "" + userEvent.toString(), Toast.LENGTH_SHORT).show();
    }


}
