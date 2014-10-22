package cn.weeon.job.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class JobDetailActivity extends BaseActivity {

    private static String TAG = "cn.weeon.job.activity.JobDetailActivity";
    private PopupWindow menuPopup;
    private View commonHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        init("工作详细");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }




    @Override
    protected void init(String titleStr) {
        super.init(titleStr);
        commonHeader = findViewById(R.id.common_header);
        initMenu();
        moreBtn.setVisibility(View.VISIBLE);
        moreBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                    menuPopup.showAtLocation(findViewById(R.id.common_header),
                            Gravity.RIGHT | Gravity.TOP, 0,
                            commonHeader.getHeight());


            }

        });
    }
    private void initMenu() {

        View popupView = getLayoutInflater().inflate(
                R.layout.menu_popupwindow, null);

        RadioGroup rg = (RadioGroup) popupView.findViewById(R.id.radio_group);

        RadioButton radio0 = (RadioButton) getLayoutInflater().inflate(
                R.layout.menu_radio_item, rg, false);
        radio0.setText("默认");
        radio0.setId(R.id.menu_id_01);
        rg.addView(radio0);
        RadioButton radio1 = (RadioButton) getLayoutInflater().inflate(
                R.layout.menu_radio_item, rg, false);
        radio1.setText("推荐");
        radio1.setId(R.id.menu_id_02);
        rg.addView(radio1);
        RadioButton radio2 = (RadioButton) getLayoutInflater().inflate(
                R.layout.menu_radio_item, rg, false);

        radio2.setText("价格↑");
        radio2.setId(R.id.menu_id_03);
        rg.addView(radio2);
        RadioButton radio3 = (RadioButton) getLayoutInflater().inflate(
                R.layout.menu_radio_item, rg, false);

        radio3.setText("价格↓");
        radio3.setId(R.id.menu_id_04);
        rg.addView(radio3);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG," id"+checkedId);
                switch (checkedId) {
                    case R.id.menu_id_01:
                        break;
                    case R.id.menu_id_02:
                        break;
                    case R.id.menu_id_03:
                        break;
                    case R.id.menu_id_04:
                        break;
                    default:
                        break;
                }

				/*Toast.makeText(HotelActivity.this, checkedId+"", Toast.LENGTH_LONG)
						.show();*/
                menuPopup.dismiss();

            }

        });

        menuPopup = new PopupWindow(popupView, AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT, true);
        menuPopup.setTouchable(true);
        menuPopup.setOutsideTouchable(true);
        menuPopup.setBackgroundDrawable(new BitmapDrawable(getResources()));
    }
}
