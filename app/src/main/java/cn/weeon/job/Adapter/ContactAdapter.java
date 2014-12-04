/**
 * Project Name:TContact
 * File Name:ContactAdapter.java
 * Package Name:com.ly.tcontact
 * Date:2013-9-24下午9:39:56
 * Copyright (c) 2013, ly.boy2012@gmail.com All Rights Reserved.
 *
 */

package cn.weeon.job.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.weeon.job.activity.R;
import cn.weeon.job.common.PingYinUtil;

/**
 * ClassName:ContactAdapter Reason: TODO ADD REASON. Date: 2013-9-24 下午9:39:56
 *
 * @author liying
 * @version 1.0
 * @see
 * @since JDK 1.6
 */
public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String, Object>> list;
    private ImageLoader imageLoader;

    public ContactAdapter(Context context, List<Map<String, Object>> list, ImageLoader imageLoader) {
        super();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.imageLoader = imageLoader;
    }
    public void sortList(){
        Collections.sort(list, new PinyinComparator());

    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(
                    R.layout.contact_list_item, parent, false);
            LinearLayout contactItemBg = (LinearLayout) convertView.findViewById(R.id.contact_item_bg);
            TextView catalog = (TextView) convertView
                    .findViewById(R.id.item_catalog);
            NetworkImageView imageHead = (NetworkImageView) convertView
                    .findViewById(R.id.contact_head_img);
            TextView title = (TextView) convertView
                    .findViewById(R.id.contact_item_title);
            TextView desc = (TextView) convertView
                    .findViewById(R.id.contact_item_desc);

            viewHolder = new ViewHolder(catalog, imageHead, title, desc, contactItemBg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String catalog = PingYinUtil.converterToFirstSpell((String) list.get(position).get("userName")).substring(0, 1);
        if (position == 0) {
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText(catalog);
        } else {
            String lastCatalog = PingYinUtil.converterToFirstSpell((String) list.get(position-1).get("userName")).substring(0, 1);
            if (catalog.equals(lastCatalog)) {
                viewHolder.catalog.setVisibility(View.GONE);
            } else {
                viewHolder.catalog.setVisibility(View.VISIBLE);
                viewHolder.catalog.setText(catalog);
            }
        }


        viewHolder.img.setDefaultImageResId(R.drawable.ic_launcher);
        viewHolder.img.setErrorImageResId(R.drawable.ic_launcher);
        viewHolder.img.setImageUrl((String) list.get(position).get("head"),
                imageLoader);


        viewHolder.title.setText((String) list.get(position).get("userName"));
        viewHolder.desc.setText((String) list.get(position).get("telephone"));

        viewHolder.telephone = (String) list.get(position).get("telephone");
        return convertView;
    }

    public final class ViewHolder {// 辅助视图
        LinearLayout contactItemBg;
        NetworkImageView img;
        TextView title;
        TextView desc;

        TextView catalog;// 目录
        String telephone;


        public ViewHolder(TextView catalog, NetworkImageView img, TextView title,
                          TextView desc, LinearLayout contactItemBg) {
            super();
            this.img = img;
            this.title = title;
            this.desc = desc;

            this.catalog = catalog;
            this.contactItemBg = contactItemBg;

        }

        public String getTelephone() {
            return telephone;
        }
    }

    public void setSection(LinearLayout header, String label) {
        TextView text = new TextView(context);
        header.setBackgroundColor(0xffaabbcc);
        text.setTextColor(Color.WHITE);
        text.setText(label.substring(0, 1).toUpperCase());
        text.setTextSize(20);
        text.setPadding(5, 0, 0, 0);
        text.setGravity(Gravity.CENTER_VERTICAL);
        header.addView(text);
    }

    @Override
    public Object[] getSections() {

        return null;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < list.size(); i++) {
            String l = PingYinUtil.converterToFirstSpell((String) list.get(i).get("userName")).substring(0, 1);
            char firstChar = l.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public int getSectionForPosition(int position) {

        // TODO Auto-generated method stub
        return 0;
    }

    private class PinyinComparator implements Comparator<Map<String,Object>> {


        @Override
        public int compare(Map lhs, Map rhs) {
            String str1 = PingYinUtil.getPingYin((String) lhs.get("userName"));
            String str2 = PingYinUtil.getPingYin((String) rhs.get("userName"));
            return str1.compareTo(str2);
        }

    }


}
