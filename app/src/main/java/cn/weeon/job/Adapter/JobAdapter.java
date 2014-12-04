package cn.weeon.job.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;
import java.util.Map;

import cn.weeon.job.activity.R;

/**
 * ClassName:   JobAdapter
 * Reason:	 TODO ADD REASON.
 * Date:    2014/10/20
 *
 * @author liying
 * @version 1.0
 */
public class JobAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;

    public JobAdapter(Context context, List<Map<String, Object>> data,ImageLoader imageLoader) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.job_item, parent,
                    false);
            NetworkImageView jobAssignPersonImg = (NetworkImageView) convertView
                    .findViewById(R.id.job_assign_person_img);//头像
            TextView jobName = (TextView) convertView.findViewById(R.id.job_name);//工作名称
            TextView jobProName = (TextView) convertView
                    .findViewById(R.id.job_project_name);//项目名称
            TextView jobEndTime = (TextView) convertView
                    .findViewById(R.id.job_end_time);//结束时间
            TextView jobAssignPerson = (TextView) convertView
                    .findViewById(R.id.job_assign_person);//分配人


            viewHolder.jobAssignPersonImg = jobAssignPersonImg;
            viewHolder.jobName = jobName;
            viewHolder.jobProName = jobProName;
            viewHolder.jobEndTime = jobEndTime;
            viewHolder.jobAssignPerson = jobAssignPerson;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.jobAssignPersonImg.setDefaultImageResId(R.drawable.ic_launcher);
        viewHolder.jobAssignPersonImg.setErrorImageResId(R.drawable.ic_launcher);
        viewHolder.jobAssignPersonImg.setImageUrl("http://192.168.100.64:8888/images/ttx.png",
                imageLoader);
        viewHolder.jobName.setText((String) data.get(position).get("jobName"));
        viewHolder.jobProName.setText(Html.fromHtml("<font color='#5cb85c'>" + data.get(position).get("jobProName")+"</font>"));
        viewHolder.jobEndTime.setText(Html.fromHtml("配送<font color='#d9534f'>" + data.get(position).get("jobEndTime") + "</font>"));
        viewHolder.jobAssignPerson.setText(Html.fromHtml("由<font color='#f0ad4e'>" + data.get(position).get("jobAssignPerson") + "</font>配送"));
        viewHolder.jobId = (String)data.get(position).get("jobId");
        viewHolder.time =  (String)data.get(position).get("jobEndTime");
        return convertView;
    }

    public class ViewHolder {
        NetworkImageView jobAssignPersonImg;
        TextView jobName;
        TextView jobProName;
        TextView jobEndTime;
        TextView jobAssignPerson;
        String jobId;
        String time;

        public String getJobId() {
            return jobId;
        }



        public String getTime() {
            return time;
        }


    }
}
