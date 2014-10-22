package cn.weeon.job.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.weeon.job.common.BitmapCache;
import cn.weeon.job.common.HttpUtil;

public class IndexActivity extends FragmentActivity implements SettingFragment.OnFragmentInteractionListener{
    private static String TAG = "cn.weeon.job.activity.FragmentActivity";
    RequestQueue mQueue = null;
    ImageLoader imageLoader = null;




    private FragmentTabHost tabHost;
    private LayoutInflater inflater;
    private ViewPager realTabContent;
    private List<Fragment> list = new ArrayList<Fragment>();
    private int imageViewArray[] = { R.drawable.nav_record, R.drawable.nav_contact, R.drawable.nav_user,
             R.drawable.nav_setting };// 图标

    private Class fragmentArray[] = { JobFragment.class, ContactFragment.class,
            UserFragment.class, SettingFragment.class };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        inflater = LayoutInflater.from(this);
        init();
    }
    private void init(){
        mQueue =  HttpUtil.getRequestQueue(this);
        imageLoader = new ImageLoader(mQueue, new BitmapCache());
        initTabHost();
        initPager();
    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }
    public RequestQueue getQueue(){
        return mQueue;
    }
    private void initTabHost() {

        realTabContent = (ViewPager) findViewById(R.id.realTabContent);
        realTabContent.setOnPageChangeListener(new ViewPagerListener());



        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);
        tabHost.setOnTabChangedListener(new TabHostListener());
        int size = fragmentArray.length;
        for (int i = 0; i < size; i++) {
            tabHost.addTab(tabHost.newTabSpec(getResources().getStringArray(R.array.menu_text)[i]).setIndicator(getItemView(i)), fragmentArray[i],
                    null);
        }

        // 设置默认显示布局
        tabHost.setCurrentTab(0);
    }

    private View getItemView(int index) {
        View view = inflater.inflate(R.layout.nav_item,  // tab widget is the parent 不加tabwidget
                // 不显示
                null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(imageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        // 从资源文件读取tabhost 标签内容
        textView.setText(getResources().getStringArray(R.array.menu_text)[index]);

        Log.d(TAG, getResources().getStringArray(R.array.menu_text)[index]);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void initPager() {
        JobFragment jf = new JobFragment();
        ContactFragment cf = new ContactFragment();
        UserFragment uf = new UserFragment();
        SettingFragment sf = new SettingFragment();
        list.add(jf);
        list.add(cf);
        list.add(uf);
        list.add(sf);
        realTabContent.setAdapter(new IndexFragmentAdapter(getSupportFragmentManager()));
    }

    class IndexFragmentAdapter extends FragmentPagerAdapter {

        public IndexFragmentAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    private class TabHostListener implements TabHost.OnTabChangeListener {
        @Override
        public void onTabChanged(String tabId) {
            int position = tabHost.getCurrentTab();
            realTabContent.setCurrentItem(position);
        }
    }

    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int index) {
            TabWidget widget = tabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            tabHost.setCurrentTab(index);
            widget.setDescendantFocusability(oldFocusability);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(mQueue!=null){
            mQueue.cancelAll(this);
        }

    }
}
