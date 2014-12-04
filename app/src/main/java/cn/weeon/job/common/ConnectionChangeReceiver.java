/**
 * Project Name:hengdian-film
 * File Name:ConnectionChangeReceiver.java
 * Package Name:com.ly.hengdian_film.common
 * Date:2014-3-11下午8:44:08
 * Copyright (c) 2014, ly.boy2012@gmail.com All Rights Reserved.
 *
 */

package cn.weeon.job.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.weeon.job.activity.IndexActivity;
import cn.weeon.job.activity.WelcomeActivity;


/**
 * ClassName:ConnectionChangeReceiver Reason: TODO ADD REASON. Date: 2014-3-11
 * 下午8:44:08
 * 
 * @author liying
 * @version 1.0
 * @since JDK 1.6
 * @see
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
	private IndexActivity index;

	public ConnectionChangeReceiver(IndexActivity index) {
		super();
		this.index = index;
	}
	public ConnectionChangeReceiver() {
		super();

	}

	private static final String TAG = ConnectionChangeReceiver.class
			.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "网络状态改变");

		/*boolean success = false;

		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// State state = connManager.getActiveNetworkInfo().getState();
		// 获取WIFI网络连接状态
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// 判断是否正在使用WIFI网络
		if (State.CONNECTED == state) {
			success = true;
		}
		// 获取GPRS网络连接状态
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		// 判断是否正在使用GPRS网络
		if (State.CONNECTED == state) {
			success = true;
		}

		if (!success) {
			//Toast.makeText(context,	"没网络",	Toast.LENGTH_LONG).show();
		}else{
			//Toast.makeText(context,	"有网络",	Toast.LENGTH_LONG).show();
			welcome.init();
		}*/
		if(index != null){
			index.changeJobFragmentState();
		}
		
	}

}
