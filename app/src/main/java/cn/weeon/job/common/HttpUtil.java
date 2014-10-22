/**
 * Project Name:UrbanInspector
 * File Name:HttpUtil.java
 * Package Name:com.weeon.urbaninspector.util
 * Date:2013-11-25下午3:14:39
 * Copyright (c) 2013, ly.boy2012@gmail.com All Rights Reserved.
 *
 */

package cn.weeon.job.common;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:HttpUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2013-11-25 下午3:14:39 <br/>
 * 
 * @author liying
 * @version
 * @since JDK 1.6
 * @see
 */
public class HttpUtil {
    public static RequestQueue getRequestQueue(Context context){
        return Volley.newRequestQueue(context);
    }

    public static List<Map<String,Object>> parseJobs(JSONObject response){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map= null;

        try {
            if(response.getInt("state")==1){

                JSONArray jobs = response.getJSONArray("data");
                JSONObject job=null;
                for (int i = 0; i < jobs.length(); i++) {
                    job = jobs.optJSONObject(i);
                     map = new HashMap<String,Object>();
                     String jobName = job.getString("jobName");
                    String jobProName = job.getString("jobProName");
                    String jobEndTime = job.getString("jobEndTime");
                    String jobAssignPerson = job.getString("jobAssignPerson");
                    map.put("jobName", jobName);
                    map.put("jobProName", jobProName);
                    map.put("jobEndTime", jobEndTime);
                    map.put("jobAssignPerson", jobAssignPerson);
                    list.add(map);
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
