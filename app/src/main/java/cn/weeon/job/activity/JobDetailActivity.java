package cn.weeon.job.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.weeon.job.Adapter.MenuItemAdapter;

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

                menuPopup.showAsDropDown(moreBtn);
                  /*  menuPopup.showAtLocation(findViewById(R.id.common_header),
                            Gravity.RIGHT | Gravity.TOP, 0,
                            commonHeader.getHeight());*/


            }

        });
    }

    private void initMenu() {

        View popupView = getLayoutInflater().inflate(
                R.layout.menu_popupwindow, null);

        ListView popMenus = (ListView) popupView.findViewById(R.id.menu_list);

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        int menuViewArray[] = {R.drawable.nav_record, R.drawable.nav_contact, R.drawable.nav_user,
                R.drawable.nav_setting};
        Map<String, Object> map;
        for (int i = 0; i < menuViewArray.length; i++) {
            map = new HashMap<String, Object>();
            map.put("fun_id",i);
            map.put("pop_ico", menuViewArray[i]);
            map.put("pop_name", (getResources().getStringArray(R.array.menu_text)[i]));
            data.add(map);

        }


        MenuItemAdapter adapter = new MenuItemAdapter(this, data);
        popMenus.setAdapter(adapter);
        popMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItemAdapter.ViewHolder holder = (MenuItemAdapter.ViewHolder) view.getTag();
                Toast.makeText(JobDetailActivity.this,holder.getPopmenuName().getText(),Toast.LENGTH_LONG).show();
                menuPopup.dismiss();

            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;//宽度
        // int height = dm.heightPixels ;//高度
        menuPopup = new PopupWindow(popupView, width / 2,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        menuPopup.setTouchable(true);
        menuPopup.setOutsideTouchable(true);
        menuPopup.setBackgroundDrawable(new BitmapDrawable(getResources()));
    }
}
