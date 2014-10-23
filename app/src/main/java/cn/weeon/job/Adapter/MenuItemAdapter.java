package cn.weeon.job.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.weeon.job.activity.R;

/**
 * ClassName:   MenuItemAdapter
 * Reason:	 TODO ADD REASON.
 * Date:    2014/10/23
 *
 * @author liying
 * @version 1.0
 */
public class MenuItemAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;

    public MenuItemAdapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
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

            convertView = layoutInflater.inflate(R.layout.menu_popwindow_item, parent,
                    false);
            ImageView popMenuIco = (ImageView) convertView
                    .findViewById(R.id.pop_ico);
            TextView popmenuName = (TextView) convertView.findViewById(R.id.pop_name);


            viewHolder.popMenuIco = popMenuIco;
            viewHolder.popmenuName = popmenuName;


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.popMenuIco.setImageResource((Integer) data.get(position).get("pop_ico"));

        viewHolder.popmenuName.setText((String) data.get(position).get("pop_name"));

        return convertView;
    }

    public class ViewHolder {
        private ImageView popMenuIco;
        private TextView popmenuName;


        public TextView getPopmenuName() {
            return popmenuName;
        }
    }
}
