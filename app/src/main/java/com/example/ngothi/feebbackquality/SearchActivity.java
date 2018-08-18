package com.example.ngothi.feebbackquality;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ListView lvImage;
    ArrayList<ImageListViewCustom> imageListViewCustoms;
    ImageListViewAdapter ImageAdapter;
    EditText editTextSrc_d;
    String textTime = null;
    String textDate = null;
    String UrlImage = null;
    int countDownload = 0;
    int Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // anh xa view
        editTextSrc_d = (EditText) findViewById(R.id.editTextSrc);
        Button btnSrc_d = (Button) findViewById(R.id.buttonSrc);
        // anh xa listView.
        lvImage = (ListView) findViewById(R.id.lv_image_db_d);

        // khoi tao mang
        imageListViewCustoms = new ArrayList<>();

        // gan cac phan tu la cau thu vao mang
        // imageListViewCustoms.add(new ImageListViewCustom(R.drawable.blue_drak, "test", "test", "12000016082018"));

        // khoi tao Adapter.
        ImageAdapter = new ImageListViewAdapter(this, R.layout.info_image_listview, imageListViewCustoms);
        lvImage.setAdapter(ImageAdapter);

        // btn Search.
        btnSrc_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this, editTextSrc_d.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                GetData("http://duchuynm.000webhostapp.com/getdata.php");
            }
        });

        // Listview item click.
        lvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Object itemOject = lvImage.getItemAtPosition(i);
                //Toast.makeText(SearchActivity.this, itemOject., Toast.LENGTH_SHORT).show();
                TextView v = (TextView) view.findViewById(R.id.db_time_d);
                textDate = v.getText().toString().substring(11, 21);
                textTime = v.getText().toString().substring(22);
                GetImage("http://duchuynm.000webhostapp.com/getdata.php");       // Tam nhu vay.
//                if(Id != 0){
//
//                }
            }
        });
    }

    // get data database.
    private void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(SearchActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < response.length(); i++){   // mỗi phần tử là 1 JSON Object.
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getString("imeid").trim().equals(editTextSrc_d.getText().toString().trim())){
                                    //Toast.makeText(SearchActivity.this, "Dung!", Toast.LENGTH_SHORT).show();
                                    imageListViewCustoms.add(new ImageListViewCustom(
                                            "http://duchuynm.000webhostapp.com/" + object.getString("imagenamed"),
                                            "Mã lỗi: " + object.getString("ml1d"),
                                            "Mã Progess: " + object.getString("ml2d"),
                                            "Thời gian: " + object.getString("dated") + "-" + object.getString("timed")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ImageAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, "Download Error!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void GetImage(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(SearchActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < response.length(); i++){   // mỗi phần tử là 1 JSON Object.
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getString("imeid").trim().equals(editTextSrc_d.getText().toString().trim()) && textDate.equals(object.getString("dated").trim()) && textTime.equals(object.getString("timed").trim())){
                                    UrlImage = "http://duchuynm.000webhostapp.com/" + object.getString("imagenamed");
                                    countDownload = object.getInt("countd");
                                    Id = object.getInt("idd");
                                    UpdateCount("http://duchuynm.000webhostapp.com/update.php");
                                    Toast.makeText(SearchActivity.this, UrlImage + "-----" + countDownload + "-----" + Id, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ImageAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(SearchActivity.this, "Download Error!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void UpdateCount(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(SearchActivity.this, "Update Count Success", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(SearchActivity.this, "error!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, "Xay ra loi database", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                //params.put("Date_D", textDate);
                //params.put("Time_D", textTime);
                //params.put("IMEI_D", editTextSrc_d.getText().toString().trim());
                params.put("ID_D", String.valueOf(Id));
                params.put("Count_D", String.valueOf(countDownload+1));


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // get image.
    private class LoadImage extends AsyncTask<String, Void, Bitmap>{

        Bitmap bitmapImage = null;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                bitmapImage = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
