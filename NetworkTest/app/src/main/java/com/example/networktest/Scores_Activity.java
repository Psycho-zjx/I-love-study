package com.example.networktest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Javabean;

public class Scores_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private String username;
    private String password;
    public  String url = "http://bkjws.sdu.edu.cn/b/ajaxLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra("data");
        username = data.get(0);
        password = data.get(1);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(LayoutManager);
        new MyAsyncTask().execute(url);
    }


    //获取 Cookie
    public static String GetCookie(String url, String username, String password) {
        try {
            password = Md5(password);
            Map<String, String> map = new HashMap<String, String>();
            map.put("j_username", username);
            map.put("j_password", password.toLowerCase());
            Connection conn = Jsoup.connect(url);
            conn.ignoreContentType(true);
            conn.method(Connection.Method.POST);
            conn.data(map);
            conn.followRedirects(false);
            Connection.Response response = conn.execute();
            String sessionId = response.cookie("JSESSIONID");
            return sessionId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Md5加密函数
    public static String Md5(String str) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            for (byte b : digest) {
                int x = b & 0xff;  // 将byte转换2位的16进制int类型数
                String s = Integer.toHexString(x); // 将一个int类型的数转为2位的十六进制数
                if (s.length() == 1) {
                    s = "0" + s;
                }
                buffer.append(s);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //得到本学期绩点的List
    public static List GetbxqChenJi(String sessionId) {
        try {
            Connection conn = Jsoup.connect("http://bkjws.sdu.edu.cn/b/cj/cjcx/xs/list");
            String aodata = "[{\"name\":\"sEcho\",\"value\":1},{\"name\":\"iColumns\",\"value\":8},{\"name\":\"sColumns\",\"value\":\"\"},{\"name\":\"iDisplayStart\",\"value\":0},{\"name\":\"iDisplayLength\",\"value\":-1},{\"name\":\"mDataProp_0\",\"value\":\"function\"},{\"name\":\"mDataProp_1\",\"value\":\"kch\"},{\"name\":\"mDataProp_2\",\"value\":\"kcm\"},{\"name\":\"mDataProp_3\",\"value\":\"kxh\"},{\"name\":\"mDataProp_4\",\"value\":\"xf\"},{\"name\":\"mDataProp_5\",\"value\":\"kssj\"},{\"name\":\"mDataProp_6\",\"value\":\"kscjView\"},{\"name\":\"mDataProp_7\",\"value\":\"kcsx\"},{\"name\":\"iSortingCols\",\"value\":0},{\"name\":\"bSortable_0\",\"value\":false},{\"name\":\"bSortable_1\",\"value\":false},{\"name\":\"bSortable_2\",\"value\":false},{\"name\":\"bSortable_3\",\"value\":false},{\"name\":\"bSortable_4\",\"value\":false},{\"name\":\"bSortable_5\",\"value\":false},{\"name\":\"bSortable_6\",\"value\":false},{\"name\":\"bSortable_7\",\"value\":false}]";
            conn.ignoreContentType(true);
            conn.method(Connection.Method.POST);
            conn.cookie("JSESSIONID", sessionId);
            conn.data("aoData", aodata);
            conn.followRedirects(false);
            Connection.Response response = conn.execute();
            List<Javabean.ObjectBean.AaDataBean> res = GetbxqCj(response.body());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解析本学期绩点json数组
    public static List GetbxqCj(String json) {
        Javabean resultBean = new Gson().fromJson(json, Javabean.class);
        //对象中拿到集合
        List<bean.Javabean.ObjectBean.AaDataBean> userBeanList = resultBean.getObject().getAaData();
        return userBeanList;
    }

    //获取本学期成绩数据
    private  List<Javabean.ObjectBean.AaDataBean> getbxqScore() {
        String sessionId = GetCookie(url, username, password);
        List<Javabean.ObjectBean.AaDataBean> res = GetbxqChenJi(sessionId);
        return res;
    }

    class MyAsyncTask extends AsyncTask<String, Void , List<Javabean.ObjectBean.AaDataBean>> {

        @Override
        protected List doInBackground(String... params) {
            List<Javabean.ObjectBean.AaDataBean> all = getbxqScore();
            return all;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Javabean.ObjectBean.AaDataBean> list) {
            super.onPostExecute(list);
            mProgressBar.setVisibility(View.GONE);
            ScoresAdapter adapter = new ScoresAdapter(list);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.addItemDecoration(new MyDecoration(Scores_Activity.this, MyDecoration.VERTICAL_LIST));
        }

    }

}
