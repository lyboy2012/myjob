package cn.weeon.job.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * ClassName:   BaseActivity
 * Reason:	 TODO ADD REASON.
 * Date:    2014/10/22
 *
 * @author liying
 * @version 1.0
 */
public abstract class BaseActivity extends Activity {

    protected Button backBtn, moreBtn;
    private TextView title;

    /**
     *
     * initHeader:(初始化header). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author liying
     * @param titleStr
     * @since JDK 1.6
     */
    protected void initHeader(String titleStr) {
        backBtn = (Button) findViewById(R.id.back_btn);
        moreBtn = (Button) findViewById(R.id.more_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });
        title = (TextView) findViewById(R.id.header_title);
        title.setText(titleStr);
    }

    /**
     *
     * init:(初始化方法). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author liying
     * @since JDK 1.6
     */
    protected void init(String titleStr) {
        initHeader(titleStr);
    }
}