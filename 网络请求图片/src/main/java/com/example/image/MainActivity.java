package com.example.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private ProgressBar bar;
    int a = 0;
    private String path = "http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    a+=50;
                    bar.setProgress(a);
                    break;
                case 1:
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    bar.setVisibility(View.GONE);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                new Thread() {
                    public void run() {
                        try {
/*                            while (a < 100) {
                                sleep(1000);
                                handler.sendEmptyMessage(0);
                            }*/
                            URL url = new URL(path);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setConnectTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            int responseCode = httpURLConnection.getResponseCode();
                            if (responseCode == 200) {

                                Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());

                                Message message = new Message();
                                message.obj = bitmap;
                                message.what = 1;
                                handler.sendMessage(message);

                            }
                            if (a >= 100) {


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }
}
