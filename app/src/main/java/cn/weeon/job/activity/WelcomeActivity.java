package cn.weeon.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.Timer;
import java.util.TimerTask;

import cn.weeon.job.common.ConnectionChangeReceiver;
import cn.weeon.job.common.Util;

public class WelcomeActivity extends Activity {

    static final String TAG ="cn.weeon.job.Activity.WelcomeActivity";
    private Timer timer = null;
   // private ConnectionChangeReceiver receiver;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
      /*  receiver = new ConnectionChangeReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);*/

        init();

    }

    public void init(){



       // boolean isAvaliable = Util.NetWorkIsAvaliable(this);
       // if (isAvaliable) {// 网络可用
            final String userId = Util.getUserInfo(this);


            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this, userId !=null ?IndexActivity.class: LoginActivity.class));
                    finish();
                }

            };

            timer = new Timer();
            timer.schedule(task, 2000);// 开启定时器，delay 1s后执行task





       // } else {// 网络不可用 打开网络设置 并关闭 应用

           // Util.setNetworkMethod(this);
            //finish();
            //System.exit(0);
       // }
    }
    /**
     *
     * @param menu
     * @return
     */
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();// 销毁定时器
        }

        Log.d(TAG, "cancel timer");
        super.onDestroy();
       // unregisterReceiver(receiver);

    }

}
