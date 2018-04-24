package com.zhx.app.eventbusdemmp;

import android.util.Log;

/**
 * Created by Administrator on 2018/4/24.
 */

public class User extends UserEvent {
   public int username=2;

    @Override
    public void getUser() {
        Log.d("_tag", "user: ");
    }
}
