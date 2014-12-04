package cn.weeon.job.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import cn.weeon.job.common.ConnectionChangeReceiver;
import cn.weeon.job.common.EmptyLayout;
import cn.weeon.job.common.HttpUtil;
import cn.weeon.job.common.RefreshLayout;
import cn.weeon.job.common.UTF8JsonObjectRequest;
import cn.weeon.job.common.Util;


public class JobFragment extends BaseFragment {
    private static String TAG = "cn.weeon.job.activity.JobFragment";
    private RefreshLayout swipeContainer;
    private ListView jobList;//job list

    private RequestQueue mQueue;
    private ImageLoader imageLoader;
    private List<Map<String, Object>> dataSource;
    private JobAdapter adapter;
    private View mListViewFooter;

    private EmptyLayout mEmptyLayout; // this is required to show different layouts (loading or empty or error)


    @Override
    public int getLayout() {
        return R.layout.fragment_job;
    }


    public void showNetWorksState(boolean isAvaliable){
        if(this.getActivity()==null){return;}

        String state;
        if(!isAvaliable){
            //网络不可用
              state = "网络不可用！";
        }else{
            //网络可用
            state = "网络可用！";
        }
        Toast.makeText(this.getActivity(),state,Toast.LENGTH_LONG).show();
    }

    @Override
    public void init() {

        mListViewFooter = LayoutInflater.from(this.getActivity()).inflate(R.layout.listview_footer, null,
                false);

        mQueue = ((IndexActivity) this.getActivity()).getQueue();
        imageLoader = ((IndexActivity) this.getActivity()).getImageLoader();
        jobList = (ListView) view.findViewById(R.id.job_list);


        swipeContainer = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeContainer.setListView(jobList);
        dataSource = new ArrayList<Map<String, Object>>();
        adapter = new JobAdapter(this.getActivity(), dataSource,imageLoader);
        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JobAdapter.ViewHolder viewHolder = (JobAdapter.ViewHolder) view.getTag();

                Intent intent = new Intent(JobFragment.this.getActivity(), JobDetailActivity.class);
                intent.putExtra("time", viewHolder.getTime());
                intent.putExtra("jobId", viewHolder.getJobId());

                startActivity(intent);

            }
        });
       // jobList.addFooterView(mListViewFooter);
        swipeContainer.setLoading(true);
        jobList.setAdapter(adapter);
        fetchJobsAsync(0);
//网络加载出现问题时候显示
        mEmptyLayout = new EmptyLayout(this.getActivity(),jobList);
        mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobFragment.this.getActivity(), "Try again button clicked", Toast.LENGTH_LONG).show();
            }
        });
        //设置颜色
        swipeContainer.setColorSchemeResources( android.R.color.holo_orange_dark,
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
    public void fetchJobsAsync(final  int page) {

        UTF8JsonObjectRequest jsonObjectRequest = new UTF8JsonObjectRequest("http://192.168.100.64:8888/jobs", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        List<Map<String, Object>> list = HttpUtil.parseJobs(response);
                        if(page==0 && list.isEmpty()){
                            if(!Util.NetWorkIsAvaliable(JobFragment.this.getActivity())){
                                mEmptyLayout.setErrorMessage("加载数据失败，请检查您的网络！");
                                mEmptyLayout.showError();
                            }else{
                                mEmptyLayout.setEmptyMessage("目前还没有订货单哦！");
                                mEmptyLayout.showEmpty();
                            }


                        }
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
