package com.example.ngothi.feebbackquality;

import android.net.Uri;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageListViewCustom {
    private String Image_d;
    private String maLoi_d;
    private String maProgess_d;
    private String time_d;

    public ImageListViewCustom(String image_d, String maLoi_d, String maProgess_d, String time_d) {
        this.Image_d = image_d;
        this.maLoi_d = maLoi_d;
        this.maProgess_d = maProgess_d;
        this.time_d = time_d;
    }

    public String getImage_d() {
        return Image_d;
    }

    public void setImage_d(String image_d) {
        Image_d = image_d;
    }

    public String getMaLoi_d() {
        return maLoi_d;
    }

    public void setMaLoi_d(String maLoi_d) {
        this.maLoi_d = maLoi_d;
    }

    public String getMaProgess_d() {
        return maProgess_d;
    }

    public void setMaProgess_d(String maProgess_d) {
        this.maProgess_d = maProgess_d;
    }

    public String getTime_d() {
        return time_d;
    }

    public void setTime_d(String time_d) {
        this.time_d = time_d;
    }
}
