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

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> user_info = new ArrayList<String>();
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra("data");
        username = data.get(0);
        password = data.get(1);
        Button getCourses = (Button) findViewById(R.id.get_courses);
        Button getScore = (Button) findViewById(R.id.get_score);
        Button getbxqScore = (Button) findViewById(R.id.get_bxqscore);
        Button getTiCeScore = (Button) findViewById(R.id.get_ticescore);
        getCourses.setOnClickListener(this);
        getScore.setOnClickListener(this);
        getbxqScore.setOnClickListener(this);
        getTiCeScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.get_courses){
            list.clear();
            getCourse();
        }else if(v.getId() == R.id.get_score){
            user_info.add(username);
            user_info.add(password);
            Intent intent = new Intent(Main2Activity.this, Lnscores.class);
            intent.putStringArrayListExtra("data", user_info);
            startActivity(intent);
        }else if (v.getId() == R.id.get_ticescore){
            list.clear();
            getTiCeScore();
        }else if (v.getId() == R.id.get_bxqscore){
            list.clear();
            user_info.add(username);
            user_info.add(password);
            Intent intent = new Intent(Main2Activity.this, Scores_Activity.class);
            intent.putStringArrayListExtra("data", user_info);
            startActivity(intent);
        }
    }

    //存体测成绩数据到List并 传数据到下个活动
    private void GetTiCeList(List data){
        for(int i=0; i< data.size(); i++) {
            String a = data.get(i).toString();
            Map b = DataToMap(a);
            StringBuilder ticescores = new StringBuilder();
            ticescores.append("项目 ");
            ticescores.append(b.get("xiangmu"));
            ticescores.append(" 成绩 ");
            ticescores.append(b.get("chengji"));
            ticescores.append(" 得分 ");
            ticescores.append(b.get("defeng"));
            ticescores.append(" 等级 ");
            if(b.get("dengji")!=" ") {
                ticescores.append(b.get("dengji"));
            }else {
                ticescores.append("无");
            }
            list.add(ticescores.toString());
        }
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putStringArrayListExtra("data", list);
        startActivity(intent);
    }
    ////获取体测成绩
    private void getTiCeScore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Cookie = getTiYuCookie("http://schoolsports.infosport.com.cn/Login.aspx", username, password);
                    Document tice =GetTiCeContent("http://schoolsports.infosport.com.cn/StudentTestScoreSearch.aspx", Cookie);
                    Elements tr = tice.select("table[width=97%]");//标签选择
                    Elements Score = tice.select("span[id=MainContent2_LblTotalScore]");//标签选择
                    Elements level = tice.select("span[id=MainContent2_LblClassLevel]");//标签选择
                    list.add("总分  "+Score.text()+"  等级  "+level.text());
                    List ti = getTiCeData(tr);
                    GetTiCeList(ti);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //模拟登录体测
    public static Document GetTiCeContent(String url, String cookie) {
        try {
            Document Doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .cookie("ASP.NET_SessionId", cookie)
                    .get();
            return Doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //体育学院体测cookie
    public static String getTiYuCookie(String url,String username, String password){
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", username);
            map.put("password", password);
            Connection conn = Jsoup.connect(url);
            conn.ignoreContentType(true);
            conn.method(Method.POST);
            conn.data(map);
            conn.followRedirects(false);
            Response response = conn.execute();
            String sessionId = response.cookie("ASP.NET_SessionId");
            return sessionId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //得到体测数据
    public static List getTiCeData(Elements tr){
        ArrayList li = new ArrayList();
        for (Element element : tr) {
            Elements aNode = element.select("tr[height=30px]");//标签选择
            for (Element e : aNode) {
                Elements a = e.select("td[class=tdListCenter]");//标签选择
                ArrayList list = new ArrayList();
                for (Element c : a){
                    list.add(c.text());
                }
                li.add(list);
            }
        }
        return li;
    }
    //体测数据转化为map
    public static Map<String, String> DataToMap(String DataString) {
        String data[] = DataString.substring(1,DataString.length()-1).split(",");
        Map<String, String> map=new HashMap<String, String>();
        map.put("xiangmu",data[0]);
        map.put("chengji",data[1]);
        map.put("defeng",data[2]);
        map.put("fujiafeng",data[3]);
        map.put("dengji",data[4]);
        return map;
    }
    //获取成绩数据
    private void getScore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sessionId = GetCookie("http://bkjws.sdu.edu.cn/b/ajaxLogin",username,password);
                    List<Javabean.ObjectBean.AaDataBean> data = GetChenJi(sessionId);
                    getScoreData(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //存成绩数据到List并 传数据到下个活动
    private void getScoreData(List<Javabean.ObjectBean.AaDataBean> data){
        for(int i=0; i< data.size(); i++) {
            StringBuilder scores = new StringBuilder();
            scores.append(data.get(i).getKcm());
            scores.append(" ");
            scores.append(data.get(i).getKcsx());
            scores.append(" 成绩");
            scores.append(data.get(i).getKscj());
            scores.append(" 等级");
            scores.append(data.get(i).getWfzdj());
            scores.append(" 绩点");
            scores.append(data.get(i).getWfzjd());
            list.add(scores.toString());
        }
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putStringArrayListExtra("data", list);
        startActivity(intent);
    }
    //获取课表
    private void getCourse(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sessionId = GetCookie("http://bkjws.sdu.edu.cn/b/ajaxLogin",username,password);
                    Document content = GetContent("http://bkjws.sdu.edu.cn/f/xk/xs/bxqkb", sessionId);
                    GetList(content);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //存课程数据到List并 传数据到下个活动
    private void GetList(Document content){
        ArrayList data = GetData(content);
        for(int i=0; i< data.size(); i++) {
            String a = data.get(i).toString();
            Map b = StringToList(a);
            StringBuilder sb = new StringBuilder();
            sb.append(b.get("kechengming"));
            sb.append(" ");
            sb.append(b.get("teacher"));
            sb.append(" ");
            sb.append(b.get("place"));
            sb.append(" 星期");
            sb.append(b.get("zhouji"));
            sb.append("第");
            sb.append(b.get("dijijie"));
            sb.append("节课");
            list.add(sb.toString());
        }
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putStringArrayListExtra("data", list);
        startActivity(intent);
    }
    //模拟登陆 得到课表页面
    public static Document GetContent(String url, String cookie) {
        try {
            Document Doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .cookie("JSESSIONID", cookie)
                    .get();
            return Doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //获取 Cookie
    public static String GetCookie(String url,String username, String password){
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
            String sessionId = response.cookie("JSESSIONID");
            return sessionId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //处理课表页面获得课表的课程信息
    public static ArrayList GetData(Document doc){
        ArrayList AllList = new ArrayList();
        Elements tr = doc.getElementsByTag("tr");//标签选择
        for (Element element : tr) {
            Elements aNode = element.getElementsByTag("td");//标签选择
            ArrayList list = new ArrayList();
            for (Element e : aNode) {
                Elements a = e.getElementsByTag("td");//标签选择
                list.add(a.text());
            }
            if (list.size() == 12){
                AllList.add(list);
            }else {
                continue;
            }
        }
        return AllList;
    }
    //将课程信息转化为map
    public static Map<String, String> StringToList(String DataString) {
        String d[] = DataString.substring(0,DataString.length()-1).split(",");
        Map<String, String> map=new HashMap<String, String>();
        map.put("kexuhao",d[1]);
        map.put("kechengming",d[2]);
        map.put("kaikexueyuan",d[6]);
        map.put("teacher",d[7]);
        map.put("shangkezhou",d[8]);
        map.put("zhouji",d[9]);
        map.put("dijijie",d[10]);
        map.put("place",d[11]);
        return map;
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
            conn.method(Method.POST);
            conn.cookie("JSESSIONID", sessionId);
            conn.data("aoData", aodata);
            conn.followRedirects(false);
            Response response = conn.execute();
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
    //得到本学期绩点的List
    public static List GetbxqChenJi(String sessionId){
        try {
            Connection conn = Jsoup.connect("http://bkjws.sdu.edu.cn/b/cj/cjcx/xs/list");
            String aodata = "[{\"name\":\"sEcho\",\"value\":1},{\"name\":\"iColumns\",\"value\":8},{\"name\":\"sColumns\",\"value\":\"\"},{\"name\":\"iDisplayStart\",\"value\":0},{\"name\":\"iDisplayLength\",\"value\":-1},{\"name\":\"mDataProp_0\",\"value\":\"function\"},{\"name\":\"mDataProp_1\",\"value\":\"kch\"},{\"name\":\"mDataProp_2\",\"value\":\"kcm\"},{\"name\":\"mDataProp_3\",\"value\":\"kxh\"},{\"name\":\"mDataProp_4\",\"value\":\"xf\"},{\"name\":\"mDataProp_5\",\"value\":\"kssj\"},{\"name\":\"mDataProp_6\",\"value\":\"kscjView\"},{\"name\":\"mDataProp_7\",\"value\":\"kcsx\"},{\"name\":\"iSortingCols\",\"value\":0},{\"name\":\"bSortable_0\",\"value\":false},{\"name\":\"bSortable_1\",\"value\":false},{\"name\":\"bSortable_2\",\"value\":false},{\"name\":\"bSortable_3\",\"value\":false},{\"name\":\"bSortable_4\",\"value\":false},{\"name\":\"bSortable_5\",\"value\":false},{\"name\":\"bSortable_6\",\"value\":false},{\"name\":\"bSortable_7\",\"value\":false}]";
            conn.ignoreContentType(true);
            conn.method(Method.POST);
            conn.cookie("JSESSIONID", sessionId);
            conn.data("aoData", aodata);
            conn.followRedirects(false);
            Response response = conn.execute();
            List<Javabean.ObjectBean.AaDataBean> res = GetbxqCj(response.body());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //解析本学期绩点json数组
    public static List GetbxqCj(String json){
        Javabean resultBean = new Gson().fromJson(json ,Javabean.class);
        //对象中拿到集合
        List<bean.Javabean.ObjectBean.AaDataBean> userBeanList = resultBean.getObject().getAaData();
        return userBeanList;
    }
    //获取本学期成绩数据
    private  void getbxqScore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sessionId = GetCookie("http://bkjws.sdu.edu.cn/b/ajaxLogin",username,password);
                    List<Javabean.ObjectBean.AaDataBean> data = GetbxqChenJi(sessionId);
                    getbxqScoreData(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //存本学期成绩数据到List并 传数据到下个活动
    private  void getbxqScoreData(List<Javabean.ObjectBean.AaDataBean> data){
        for(int i=0; i< data.size(); i++) {
            StringBuilder scores = new StringBuilder();
            scores.append(data.get(i).getKcm());
            scores.append(" 学分");
            scores.append(data.get(i).getXf());
            scores.append(" 考试成绩");
            scores.append(data.get(i).getQmcj());
            scores.append(" 平时成绩");
            scores.append(data.get(i).getPscj());
            scores.append(" 总成绩");
            scores.append(data.get(i).getKscj());
            scores.append(" 等级");
            scores.append(data.get(i).getWfzdj());
            scores.append(" 绩点");
            scores.append(data.get(i).getWfzjd());
            list.add(scores.toString());
        }
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        intent.putStringArrayListExtra("data", list);
        startActivity(intent);
    }
}

