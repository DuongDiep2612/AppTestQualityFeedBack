package com.example.ngothi.feebbackquality;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageListViewAdapter extends ArrayAdapter<ImageListViewCustom> {

    //String textImage;

    public ImageListViewAdapter(@NonNull Context context, int resource, @NonNull List<ImageListViewCustom> objects) {
        super(context, resource, objects);
    }

//    private Context context;
//    private int layout;
//    private List<ImageListViewCustom> imageListViewCustoms;

//    public ImageListViewAdapter(Context context, int layout, List<ImageListViewCustom> imageListViewCustoms) {
//        this.context = context;
//        this.layout = layout;
//        this.imageListViewCustoms = imageListViewCustoms;
//    }

//    @Override
//    public int getCount() {
//        return imageListViewCustoms.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(layout, null);
//
//        // anh xa view.
//        TextView db_ml_d = (TextView) view.findViewById(R.id.db_ml_d);
//        TextView db_mp_d = (TextView) view.findViewById(R.id.db_mp_d);
//        TextView db_time_d = (TextView) view.findViewById(R.id.db_time_d);
//        ImageView db_img_d = (ImageView) view.findViewById(R.id.db_img_d);
//
//        // gan gia tri.
//        ImageListViewCustom imageInfo = imageListViewCustoms.get(i);
//
//        db_ml_d.setText(imageInfo.getMaLoi_d());
//        db_mp_d.setText(imageInfo.getMaProgess_d());
//        db_time_d.setText(imageInfo.getTime_d());
//        //db_img_d.setImageResource(imageInfo.getImage_d());
//        Picasso.with(context)
//
//        return view;
//
//    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.info_image_listview, null);
        }
        ImageListViewCustom p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView db_ml_d = (TextView) view.findViewById(R.id.db_ml_d);
            TextView db_mp_d = (TextView) view.findViewById(R.id.db_mp_d);
            TextView db_time_d = (TextView) view.findViewById(R.id.db_time_d);
            ImageView db_img_d = (ImageView) view.findViewById(R.id.db_img_d);

            db_ml_d.setText(p.getMaLoi_d());
            db_mp_d.setText(p.getMaProgess_d());
            db_time_d.setText(p.getTime_d());
            //db_img_d.setImageResource(p.getImage_d());
            //Picasso.get().load(p.getImage_d()).into(db_img_d);
            Picasso.with(getContext()).load(p.getImage_d()).placeholder(R.drawable.loader).error(R.drawable.error).into(db_img_d);
            //textImage = p.getImage_d();

        }
        return view;
    }
}
