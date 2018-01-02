package com.example.networktest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Looper;
import android.widget.Button;
import android.widget.ListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import bean.Javabean;

public class NetworkTest extends AppCompatActivity{

    private ArrayList<String> user_info = new ArrayList<String>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_test);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        usernameEdit = (EditText) findViewById(R.id.get_username);
        passwordEdit = (EditText) findViewById(R.id.get_password);
        login = (Button) findViewById(R.id.login);
        rememberPass = (CheckBox) findViewById(R.id.checkBox);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String username = pref.getString("username", "");
            String password = pref.getString("password", "");
            usernameEdit.setText(username);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String username = usernameEdit.getText().toString();
                            String password = passwordEdit.getText().toString();
                            String login_responce = Get_login_responce("http://bkjws.sdu.edu.cn/b/ajaxLogin", username, password);
                            if (login_responce.equals("\"success\"")) {
                                editor = pref.edit();
                                if (rememberPass.isChecked()) {
                                    editor.putBoolean("remember_password", true);
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                } else {
                                    editor.clear();
                                }
                                editor.apply();
                                user_info.add(username);
                                user_info.add(password);
                                Intent intent = new Intent(NetworkTest.this, Main2Activity.class);
                                intent.putStringArrayListExtra("data", user_info);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                Toast.makeText(NetworkTest.this, login_responce, Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
    //验证登陆账号
    public static String Get_login_responce(String url, String username, String password) {
        try {
            password = Md5(password);
            Map<String, String> map = new HashMap<String, String>();
            map.put("j_username", username);
            map.put("j_password", password.toLowerCase());
            Connection conn = Jsoup.connect(url);
            conn.ignoreContentType(true);
            conn.method(Method.POST);
            conn.data(map);
            conn.followRedirects(false);
            Response response = conn.execute();
            String responce = response.body();
            return responce;
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

}