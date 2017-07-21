package com.example.administrator.postdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private String path="http://123.206.70.44:8080/JavaWebTest/Upload_html?";
    private  String qq,pwd;
    private String s;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                textView.setText(s);
            }
        }
    };
    private EditText et1;
    private EditText et2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.ed1);
        et2 = (EditText) findViewById(R.id.ed2);
        textView = (TextView) findViewById(R.id.tv);
        Button btn=(Button) findViewById(R.id.btn);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        qq = et1.getText().toString().trim();
        pwd = et2.getText().toString().trim();
        if (IsNotNull(qq) || IsNotNull(pwd)) {
            new Thread() {
                public void run() {
                    URL url = null;
                    try {
                        url = new URL(path.trim());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setConnectTimeout(5000);
                        con.setReadTimeout(5000);
                        con.setRequestMethod("POST");
                        String data = "user=" + qq + "&password=" + pwd;
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setRequestProperty("Content-Length", data.length() + "");
                        con.setDoInput(true);
                        con.getOutputStream().write(data.getBytes());


                        InputStream inputStream = con.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len;
                        byte[] b = new byte[1024];
                        while ((len = inputStream.read(b)) != -1) {
                            baos.write(b);
                        }
                        baos.close();
                        inputStream.close();
                        s = baos.toString();
                        System.out.println("1111111111111111111"+s);
                        Message obtain = Message.obtain();
                        obtain.obj = s;
                        obtain.what = 1;
                        handler.sendMessage(obtain);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        } else {
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }


});
    }
        public boolean IsNotNull(String s) {
            if (!s.equals("") && s != null) {
                return true;
            }
            return false;
}
}