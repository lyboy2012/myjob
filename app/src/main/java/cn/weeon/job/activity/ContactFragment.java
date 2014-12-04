package cn.weeon.job.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.weeon.job.Adapter.ContactAdapter;
import cn.weeon.job.common.HttpUtil;
import cn.weeon.job.common.Sidebar;
import cn.weeon.job.common.UTF8JsonObjectRequest;


public class ContactFragment extends BaseFragment {

    private ListView mListView;
    private ContactAdapter adapter;
    private List<Map<String, Object>> list;
    private WindowManager mWindowManager;
    private TextView mDialogLetter;
    private Sidebar mSideBar;
    private RequestQueue mQueue;
    private ImageLoader imageLoader;

    @Override
    public int getLayout() {
        return R.layout.fragment_contact;
    }

    @Override
    public void init() {

        mQueue = ((IndexActivity) this.getActivity()).getQueue();
        imageLoader = ((IndexActivity) this.getActivity()).getImageLoader();

        mListView = (ListView) view.findViewById(R.id.contacts);
        mSideBar = (Sidebar) view.findViewById(R.id.side_bar);
        mDialogLetter = (TextView) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.dialog_letter, null);
        initDialogLetter();

        list = new ArrayList<Map<String, Object>>();
        adapter = new ContactAdapter(this.getActivity(), list,imageLoader);
        mListView.setAdapter(adapter);
        mSideBar.setContactAdapter(adapter);





        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactAdapter.ViewHolder viewHolder = (ContactAdapter.ViewHolder) view.getTag();


                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + viewHolder.getTelephone()));
                startActivity(intent);
            }
        });
        fetchContactsAsync();
    }



    private void initDialogLetter() {
        mWindowManager = (WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE);
        mDialogLetter = (TextView) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.dialog_letter, null);
        mDialogLetter.setVisibility(View.INVISIBLE);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mDialogLetter, lp);
        mSideBar.setmDialogLetter(mDialogLetter);
        mSideBar.setListView(mListView);

    }
    public void fetchContactsAsync() {

        UTF8JsonObjectRequest jsonObjectRequest = new UTF8JsonObjectRequest("http://192.168.100.64:8888/contacts", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        List<Map<String, Object>> contacts = HttpUtil.parseContacts(response);

                        Iterator<Map<String, Object>> it = contacts.iterator();
                        while (it.hasNext()) {
                            list.add(it.next());
                        }
                        adapter.sortList();
                        adapter.notifyDataSetChanged();




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
