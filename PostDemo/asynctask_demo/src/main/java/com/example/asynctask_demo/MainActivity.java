package com.example.asynctask_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private ProgressBar bar;
    private String path = "http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar= (ProgressBar) findViewById(R.id.progress);
        imageView= (ImageView) findViewById(R.id.image);
findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       AsyncTask<Void,Void,Bitmap> asyncTask=new AsyncTask<Void, Void, Bitmap>() {
           @Override
           protected void onPreExecute() {//VISIBLE占位隐藏，GONE是不占位的
               bar.setVisibility(View.VISIBLE);
           }

           @Override
           protected void onProgressUpdate(Void... values) {
               super.onProgressUpdate(values);//更新直线进度条
           }

           @Override
           protected Bitmap doInBackground(Void...voids) {
               try {
                   URL url=new URL(path.trim());
                   HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setConnectTimeout(8000);
                   connection.setReadTimeout(8000);
                   int responseCode = connection.getResponseCode();
                   if (responseCode==200){
                       InputStream inputStream = connection.getInputStream();
                       Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                       return  bitmap;
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               return null;
           }

           @Override//此方法在主线程执行
           protected void onPostExecute(Bitmap bitmap) {
               imageView.setImageBitmap(bitmap);
               bar.setVisibility(View.GONE);
               super.onPostExecute(bitmap);
           }
       };
        asyncTask.execute();
    }
});
    }


}
