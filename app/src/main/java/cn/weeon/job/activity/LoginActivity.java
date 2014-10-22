package cn.weeon.job.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import cn.weeon.job.common.HttpUtil;


public class LoginActivity extends Activity {
    static final String TAG = "cn.weeon.job.activity.LoginActivity";

    private AutoCompleteTextView userName;//用户输入框
    private EditText password;//密码输入框
    private Button loginBtn;//登录按钮
    RequestQueue mQueue = null;
    private View delBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mQueue = HttpUtil.getRequestQueue(this);
        init();
    }

    private void init() {//初始化view
        userName = (AutoCompleteTextView) findViewById(R.id.user_name);
        delBtn = findViewById(R.id.del_btn);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login_btn);

        initUserNameAutoComplete();
        initLoginBtn();
        initDelBtn();
    }

    private void initDelBtn(){
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName.setText("");
                password.setText("");
            }
        });

    }
    private void initLoginBtn() {//初始化登录按钮
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                finish();
               /* JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.100.64:8888", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAG", response.toString());
                                startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                                finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                mQueue.add(jsonObjectRequest);
*/

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mQueue!=null){
            mQueue.cancelAll(this);
        }

    }

    private void initUserNameAutoComplete() {//初始化用户名自动匹配
        userName.setThreshold(1);
        userName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, R.layout.login_auto_item, R.id.login_auto_item, new String[]{"abc","adc","afc", "bb"});

                userName.setAdapter(adapter);// 设置数据适配器
                userName.setAdapter(adapter);// 设置数据适配器

                if (!TextUtils.isEmpty(charSequence)) {
                    delBtn.setVisibility(View.VISIBLE);
                } else {
                    delBtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

}

