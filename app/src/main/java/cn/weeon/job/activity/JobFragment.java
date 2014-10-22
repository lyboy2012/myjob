package cn.weeon.job.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.weeon.job.Adapter.JobAdapter;
import cn.weeon.job.common.HttpUtil;
import cn.weeon.job.common.RefreshLayout;
import cn.weeon.job.common.UTF8JsonObjectRequest;


public class JobFragment extends BaseFragment {
    private static String TAG = "cn.weeon.job.activity.JobFragment";
    private RefreshLayout swipeContainer;

    private ListView jobList;//job list

    private RequestQueue mQueue;
    private List<Map<String, Object>> dataSource;
    private JobAdapter adapter;
    private View mListViewFooter;
    @Override
    public int getLayout() {
        return R.layout.fragment_job;
    }




    @Override
    public void init() {
        mListViewFooter = LayoutInflater.from(this.getActivity()).inflate(R.layout.listview_footer, null,
                false);

        mQueue = ((IndexActivity) this.getActivity()).getQueue();

        jobList = (ListView) view.findViewById(R.id.job_list);


        swipeContainer = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeContainer.setListView(jobList);
        dataSource = new ArrayList<Map<String, Object>>();
        adapter = new JobAdapter(this.getActivity(), dataSource);
        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(JobFragment.this.getActivity(), JobDetailActivity.class));

            }
        });
       // jobList.addFooterView(mListViewFooter);
        swipeContainer.setLoading(true);
        jobList.setAdapter(adapter);
        fetchJobsAsync(0);

        //设置颜色
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
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
}
