package com.example.no2weekdemo01;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * data:2017/7/13
 * author:汉堡(Administrator)
 * function:
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Bean.DataBean> list;
    private ViewHolder holder;

    public MyAdapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.list_item, null);
            holder.tv1 = (TextView) view.findViewById(R.id.title);
            holder.tv2 = (TextView) view.findViewById(R.id.fromname);
            holder.tv3 = (TextView) view.findViewById(R.id.showtime);
            holder.imageView= (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv1.setText(list.get(i).getTITLE());
        holder.tv2.setText(list.get(i).getFROMNAME());
        holder.tv3.setText(list.get(i).getSHOWTIME());

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(configuration);
        ImageLoader.getInstance().loadImage(list.get(i).getIMAGEURL(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                holder.imageView.setImageBitmap(loadedImage);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv1, tv2, tv3;
        ImageView imageView;
    }


}

