package cn.weeon.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.weeon.job.Adapter.JobAdapter;
import cn.weeon.job.activity.R;
import cn.weeon.job.common.BitmapCache;
import cn.weeon.job.common.HttpUtil;
import cn.weeon.job.common.RefreshLayout;
import cn.weeon.job.common.UTF8JsonObjectRequest;

public class SearchResultActivity extends BaseActivity {
    private RefreshLayout swipeContainer;

    private ListView jobList;//job list

    private List<Map<String, Object>> dataSource;
    private JobAdapter adapter;
    private View mListViewFooter;
    RequestQueue mQueue = null;
    ImageLoader imageLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init("查询结果");
    }

    @Override
    protected void init(String titleStr) {
        super.init(titleStr);
        mListViewFooter = LayoutInflater.from(this).inflate(R.layout.listview_footer, null,
                false);

        mQueue = HttpUtil.getRequestQueue(this);
        imageLoader = new ImageLoader(mQueue, new BitmapCache());


        jobList = (ListView) findViewById(R.id.job_list);


        swipeContainer = (RefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setListView(jobList);
        dataSource = new ArrayList<Map<String, Object>>();
        adapter = new JobAdapter(this, dataSource,imageLoader);
        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JobAdapter.ViewHolder viewHolder = (JobAdapter.ViewHolder) view.getTag();

                Intent intent = new Intent(SearchResultActivity.this, JobDetailActivity.class);
                intent.putExtra("time", viewHolder.getTime());
                intent.putExtra("jobId", viewHolder.getJobId());

                startActivity(intent);

            }
        });
        // jobList.addFooterView(mListViewFooter);
        swipeContainer.setLoading(true);
        jobList.setAdapter(adapter);
        fetchJobsAsync(0);

        //设置颜色
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                updateJobsAsync();
            }
        });
        swipeContainer.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {

                fetchJobsAsync(1);

            }
        });

    }

    public void updateJobsAsync() {//更新
        UTF8JsonObjectRequest jsonObjectRequest = new UTF8JsonObjectRequest("http://192.168.100.64:8888/jobs", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        List<Map<String, Object>> list = HttpUtil.parseJobs(response);
                        // swipeContainer.setEnabled(false);
                        dataSource.clear();
                        Iterator<Map<String, Object>> it = list.iterator();
                        while (it.hasNext()) {
                            dataSource.add(it.next());
                        }

                        adapter.notifyDataSetChanged();
                        swipeContainer.setRefreshing(false);

                        //swipeContainer.setEnabled(false);
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }


        });
        mQueue.add(jsonObjectRequest);


    }

    public void fetchJobsAsync(int page) {

        UTF8JsonObjectRequest jsonObjectRequest = new UTF8JsonObjectRequest("http://192.168.100.64:8888/jobs", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        List<Map<String, Object>> list = HttpUtil.parseJobs(response);
                        // swipeContainer.setEnabled(false);

                        Iterator<Map<String, Object>> it = list.iterator();
                        while (it.hasNext()) {
                            dataSource.add(it.next());
                        }

                        adapter.notifyDataSetChanged();

                        swipeContainer.setLoading(false);

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }


        });
        mQueue.add(jsonObjectRequest);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
