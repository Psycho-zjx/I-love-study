package com.example.networktest;

import android.content.Context;
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

public class Lnscores extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private String username;
    private String password;
    public Context context;
    public  String url = "http://bkjws.sdu.edu.cn/b/ajaxLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lnscores);
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
    //获取成绩数据
    private List<Javabean.ObjectBean.AaDataBean> getScore(){
        String sessionId = GetCookie("http://bkjws.sdu.edu.cn/b/ajaxLogin",username,password);
        List<Javabean.ObjectBean.AaDataBean> data = GetChenJi(sessionId);
        return data;
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
    //得到绩点的List
    public static List GetChenJi(String sessionId){
        try {
            Connection conn = Jsoup.connect("http://bkjws.sdu.edu.cn/b/cj/cjcx/xs/lscx");
            String aodata ="[{\"name\":\"sEcho\",\"value\":1},{\"name\":\"iColumns\",\"value\":10},{\"name\":\"sColumns\",\"value\":\"\"},{\"name\":\"iDisplayStart\",\"value\":0},{\"name\":\"iDisplayLength\",\"value\":20},{\"name\":\"mDataProp_0\",\"value\":\"xnxq\"},{\"name\":\"mDataProp_1\",\"value\":\"kch\"},{\"name\":\"mDataProp_2\",\"value\":\"kcm\"},{\"name\":\"mDataProp_3\",\"value\":\"kxh\"},{\"name\":\"mDataProp_4\",\"value\":\"xf\"},{\"name\":\"mDataProp_5\",\"value\":\"kssj\"},{\"name\":\"mDataProp_6\",\"value\":\"kscjView\"},{\"name\":\"mDataProp_7\",\"value\":\"wfzjd\"},{\"name\":\"mDataProp_8\",\"value\":\"wfzdj\"},{\"name\":\"mDataProp_9\",\"value\":\"kcsx\"},{\"name\":\"iSortCol_0\",\"value\":5},{\"name\":\"sSortDir_0\",\"value\":\"desc\"},{\"name\":\"iSortingCols\",\"value\":1},{\"name\":\"bSortable_0\",\"value\":false},{\"name\":\"bSortable_1\",\"value\":false},{\"name\":\"bSortable_2\",\"value\":false},{\"name\":\"bSortable_3\",\"value\":false},{\"name\":\"bSortable_4\",\"value\":false},{\"name\":\"bSortable_5\",\"value\":true},{\"name\":\"bSortable_6\",\"value\":false},{\"name\":\"bSortable_7\",\"value\":false},{\"name\":\"bSortable_8\",\"value\":false},{\"name\":\"bSortable_9\",\"value\":false}]";
            conn.ignoreContentType(true);
            conn.method(Connection.Method.POST);
            conn.cookie("JSESSIONID", sessionId);
            conn.data("aoData", aodata);
            conn.followRedirects(false);
            Connection.Response response = conn.execute();
            List<Javabean.ObjectBean.AaDataBean> res = GetCj(response.body());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //解析绩点json数组
    public static List GetCj(String json){
        Javabean resultBean = new Gson().fromJson(json ,Javabean.class);
        //对象中拿到集合
        List<bean.Javabean.ObjectBean.AaDataBean> userBeanList = resultBean.getObject().getAaData();
        return userBeanList;
    }

    class MyAsyncTask extends AsyncTask<String, Void , List<Javabean.ObjectBean.AaDataBean>> {

        @Override
        protected List doInBackground(String... params) {
            List<Javabean.ObjectBean.AaDataBean> all = getScore();
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
            mRecyclerView.addItemDecoration(new MyDecoration(Lnscores.this, MyDecoration.VERTICAL_LIST));
        }

    }
}
