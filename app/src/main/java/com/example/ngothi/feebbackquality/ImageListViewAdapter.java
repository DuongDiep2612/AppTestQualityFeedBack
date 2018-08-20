package com.example.ngothi.feebbackquality;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class ImageListViewAdapter extends ArrayAdapter<ImageListViewCustom> {

    //String textImage;

    private Context context;
    private int layout;
    private List<ImageListViewCustom> imageListViewCustoms;
    private String host = "duchuynm.000webhostapp.com/img";

    public ImageListViewAdapter(@NonNull Context context, int resource, @NonNull List<ImageListViewCustom> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.imageListViewCustoms = objects;
    }


    //Huy chỉnh sửa
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.db_img_d = convertView.findViewById(R.id.db_img_d);
            viewHolder.db_ml_d = convertView.findViewById(R.id.db_ml_d);
            viewHolder.db_mp_d = convertView.findViewById(R.id.db_mp_d);
            viewHolder.db_time_d = convertView.findViewById(R.id.db_time_d);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageListViewCustom image = imageListViewCustoms.get(position);

        if(image != null) {
            viewHolder.db_ml_d.setText(image.getMaLoi_d());
            viewHolder.db_mp_d.setText(image.getMaProgess_d());
            viewHolder.db_time_d.setText(image.getTime_d());
            Picasso.with(getContext())
                    .load(host + image.getImage_d())
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.error)
                    .into(viewHolder.db_img_d);
            viewHolder.imgBtn_showPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context,viewHolder.imgBtn_showPopup);
                    popup.getMenuInflater().inflate(R.menu.listview_image_popupmenu,popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.item_download:
                                {
                                    ImageListViewCustom image = getItem(position);
                                    download(image.getImage_d());
                                }
                            }
                            return true;
                        }
                    });
                }
            });
        }
        return convertView;
    }

    //Huy thêm
    private void download(String imageName) {
        class ftpRetrieveFile extends AsyncTask<Void, Void, Void> {
            private final String hostname = "files.000webhost.com";
            private final String userName = "duchuynm";
            private final String password = "choivuive";
            boolean retriveFile = false;

            public ftpRetrieveFile(String imageName) {
                this.imageName = imageName;
            }

            private String imageName;
            FTPClient ftpClient = null;

            public void connect() {
                if(ftpClient == null) {
                    try {
                        ftpClient = new FTPClient();
                        ftpClient.connect(hostname, 21);
                        Log.e("connect",ftpClient.getReplyString());
                        ftpClient.login(userName, password);
                        Log.e("login",ftpClient.getReplyString());
                        ftpClient.enterLocalPassiveMode();
                        ftpClient.changeWorkingDirectory("/public_html/img");
                        Log.e("change Work Dir",ftpClient.getReplyString());
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            public boolean retrieveFile() {
                retriveFile = false;
                FileOutputStream fileOutputStream = null;
                try {
                    File image  = File.createTempFile(
                            imageName.substring(0,imageName.length()-4),
                            imageName.substring(imageName.length()-4,imageName.length()),
                            new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/")
                    );
                    fileOutputStream = new FileOutputStream(image);
                    retriveFile = ftpClient.retrieveFile("/"+imageName,fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return retriveFile;
            }
            @Override
            protected Void doInBackground(Void... aVoid) {
                connect();
                retrieveFile();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(retriveFile == true) {
                    Toast.makeText(context,"Đã tải!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,"tải thất bại, vui lòng thử lại",Toast.LENGTH_LONG).show();
                }
            }
        }
        new ftpRetrieveFile(imageName).execute();
    }


    //Huy thêm
    static class ViewHolder {
        TextView db_ml_d, db_mp_d, db_time_d;
        ImageView db_img_d;
        ImageButton imgBtn_showPopup;
    }
}
