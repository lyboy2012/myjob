/**
 * Project Name:UrbanInspector
 * File Name:Util.java
 * Package Name:com.weeon.urbaninspector.util
 * Date:2013-9-22上午11:23:41
 * Copyright (c) 2013, ly.boy2012@gmail.com All Rights Reserved.
 *
 */

package cn.weeon.job.common;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;

/**
 * ClassName:Util <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2013-9-22 上午11:23:41 <br/>
 *
 * @author liying
 * @see
 * @since JDK 1.6
 */
public class Util {
    private static  final String TAG = "cn.weeon.job.common.Util";

    public static boolean NetWorkIsAvaliable(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            // do something
            // 能联网
            return true;
        } else {
            // do something
            // 不能联网
            return false;
        }

    }


    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void setNetworkMethod(final Context context) {
        // 提示对话框
        Builder builder = new Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = null;
                // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        }).show();
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * A safer decodeStream method rather than the one of {@link android.graphics.BitmapFactory}
     * which will be easy to get OutOfMemory Exception while loading a big image
     * file.
     *
     * @param uri
     * @param width
     * @param height
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Bitmap safeDecodeStream(Context ctx, Uri uri, int width, int height) throws FileNotFoundException {
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        ContentResolver resolver = ctx.getContentResolver();

        if (width > 0 || height > 0) {
            // Decode image size without loading all data into memory
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new BufferedInputStream(resolver.openInputStream(uri), 16 * 1024), null, options);

            int w = options.outWidth;
            int h = options.outHeight;
            while (true) {
                if ((width > 0 && w / 2 < width) || (height > 0 && h / 2 < height)) {
                    break;
                }
                w /= 2;
                h /= 2;
                scale *= 2;
            }
        }

        // Decode with inSampleSize option
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return BitmapFactory.decodeStream(new BufferedInputStream(resolver.openInputStream(uri), 16 * 1024), null, options);
    }

    /**
     * A safer decodeStream method rather than the one of {@link android.graphics.BitmapFactory}
     * which will be easy to get OutOfMemory Exception while loading a big image
     * file.
     *
     * @param is
     * @param width
     * @param height
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Bitmap safeDecodeStream(InputStream is, int width, int height) throws FileNotFoundException {
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        // android.content.ContentResolver resolver =
        // this.ctx.getContentResolver();

        if (width > 0 || height > 0) {
            // Decode image size without loading all data into memory
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new BufferedInputStream(is, 16 * 1024), null, options);

            int w = options.outWidth;
            int h = options.outHeight;
            while (true) {
                if ((width > 0 && w / 2 < width) || (height > 0 && h / 2 < height)) {
                    break;
                }
                w /= 2;
                h /= 2;
                scale *= 2;
            }
        }

        // Decode with inSampleSize option
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return BitmapFactory.decodeStream(new BufferedInputStream(is, 16 * 1024), null, options);
    }

    /**
     * A safer decodeStream method rather than the one of {@link android.graphics.BitmapFactory}
     * which will be easy to get OutOfMemory Exception while loading a big image
     * file.
     *
     * @param url
     * @param width
     * @param height
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static Bitmap safeDecodeStream(String url, int width, int height) throws FileNotFoundException {
        int scale = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        // android.content.ContentResolver resolver =
        // this.ctx.getContentResolver();
        File file = new File(url);
        if (width > 0 || height > 0) {
            // Decode image size without loading all data into memory
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new BufferedInputStream(new FileInputStream(file), 16 * 1024), null, options);

            int w = options.outWidth;
            int h = options.outHeight;
            while (true) {
                if ((width > 0 && w / 2 < width) || (height > 0 && h / 2 < height)) {
                    break;
                }
                w /= 2;
                h /= 2;
                scale *= 2;
            }
        }

        // Decode with inSampleSize option
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        return BitmapFactory.decodeStream(new BufferedInputStream(new FileInputStream(file), 16 * 1024), null, options);
    }

    public static Uri getResourceUri(Context context, int res) {
        try {
            Context packageContext = context.createPackageContext(context.getPackageName(), Context.CONTEXT_RESTRICTED);
            Resources resources = packageContext.getResources();
            String appPkg = packageContext.getPackageName();
            String resPkg = resources.getResourcePackageName(res);
            String type = resources.getResourceTypeName(res);
            String name = resources.getResourceEntryName(res);

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
            uriBuilder.encodedAuthority(appPkg);
            uriBuilder.appendEncodedPath(type);
            if (!appPkg.equals(resPkg)) {
                uriBuilder.appendEncodedPath(resPkg + ":" + name);
            } else {
                uriBuilder.appendEncodedPath(name);
            }
            return uriBuilder.build();

        } catch (Exception e) {
            return null;
        }
    }

    public static String getStringAssets(Context context, String fileName) {

        InputStream is = null;

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            is = context.getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }


    public static <T> T getEnum(Class<T> clazz, int index) {
        T[] c = clazz.getEnumConstants();
        return c[index - 1];
    }

    /**
     *
     * @param context
     * @param username
     * @param password
     */
    public static void setHistoryUserInfo(Context context, String username, String password) {
        SharedPreferences historyUserInfo = context.getSharedPreferences("historyUserInfo",
                Context.MODE_PRIVATE);
        Editor editor = historyUserInfo.edit();
        editor.putString(username, password);
        editor.commit();
    }
    public  static  String[] getHistoryUserInfo(Context context){

        SharedPreferences historyUserInfo = context.getSharedPreferences("historyUserInfo", Context.MODE_PRIVATE);
        Log.d(TAG, historyUserInfo.getAll().size() + "------------");
        String[] userNames = new String[historyUserInfo.getAll().size()];// sp.getAll().size()返回的是有多少个键值对

        userNames = historyUserInfo.getAll().keySet().toArray(new String[0]);
        return userNames;

    }

    public static void setUserInfo(Context context, String userId) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo",

                Context.MODE_PRIVATE);
        Editor editor = userInfo.edit();
        editor.clear().commit();

        editor.putString("userId", userId);
        editor.commit();
    }

    public static String getUserInfo(Context context) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo",

                Context.MODE_PRIVATE);


        String userId = userInfo.getString("userId", "");
        if (userId.equals("")) {
            return null;
        }


        return userId;

    }

    public static String replaceSessionId(String url, String sessionId) {

        return url.replaceAll("(?i)\\{sessionId\\}", sessionId);
    }

    public static String getSessionId(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString("jsessionid");
    }

    public static boolean validateByReg(String target, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(target);
        return m.matches();
    }

    // 验证身份证号码
    public static String IDCardValidate(String IDStr) throws ParseException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
            errorInfo = "身份证生日不在有效范围。";
            return errorInfo;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        return "";
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    public static boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
