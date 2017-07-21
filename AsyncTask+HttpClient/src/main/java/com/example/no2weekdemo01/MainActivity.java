package com.example.no2weekdemo01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import static android.R.attr.bitmap;

/* private String TITLE;
        private String IMAGEURL;
        private String FROMNAME;
        private String SHOWTIME;
* */
public class MainActivity extends AppCompatActivity {
    private List<Bean.DataBean> list;
    private String path = "http://www.93.gov.cn/93app/data.do?channelId=0&startNum=0";
    private ListView listView;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                //利用Seriazable接口方法将bean对象加入bundle中
                bundle.putSerializable("bean", list.get(i));
                intent.setClass(MainActivity.this,Main2Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {

            private MyAdapter adapter;

            @Override//子线程
            protected String doInBackground(Void... voids) {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(path);
                HttpResponse response = null;
                try {
                    response = defaultHttpClient.execute(httpGet);
                    StatusLine line = response.getStatusLine();
                    int code = line.getStatusCode();
                    if (code == 200) {
                        //5.服务器通过流写给客户端的数据,把它成一个实体
                        HttpEntity entity = response.getEntity();
                        //获取输入流
                        InputStream inputStream = entity.getContent();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];

                        while ((len = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                            s = baos.toString();
                        }
                        return s;
                    } else {
                        Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override//主线程
            protected void onPostExecute(String string) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(s, Bean.class);
                list = bean.getData();

                adapter = new MyAdapter(MainActivity.this, list);
                listView.setAdapter(adapter);

                super.onPostExecute(string);
            }
        };
        asyncTask.execute();
    }
}
