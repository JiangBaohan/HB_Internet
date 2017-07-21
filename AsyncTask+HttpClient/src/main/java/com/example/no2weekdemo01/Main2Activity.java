package com.example.no2weekdemo01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private ImageView imageView2;
    private TextView title2, fromname2, showtime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView2 = (ImageView) findViewById(R.id.image2);
        title2 = (TextView) findViewById(R.id.title2);
        fromname2 = (TextView) findViewById(R.id.fromname2);
        showtime2 = (TextView) findViewById(R.id.showtime2);
        Bean.DataBean dataBean = (Bean.DataBean) getIntent().getSerializableExtra("bean");
        title2.setText(dataBean.getTITLE());
        fromname2.setText(dataBean.getFROMNAME());
        showtime2.setText(dataBean.getSHOWTIME());
        ImageloderUtils.setImageView(dataBean.getIMAGEURL(),this,imageView2);
    }
}
