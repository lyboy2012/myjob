package cn.weeon.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    static final String TAG ="cn.weeon.job.Activity.WelcomeActivity";
    private Timer timer = null;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }

        };

        timer = new Timer();
        timer.schedule(task, 2000);// 开启定时器，delay 1s后执行task
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


}
