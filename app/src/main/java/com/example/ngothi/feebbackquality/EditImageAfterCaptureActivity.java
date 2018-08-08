package com.example.ngothi.feebbackquality;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.io.File;
import java.io.FileOutputStream;

public class EditImageAfterCaptureActivity extends Activity {

    private static final String TAG = EditImageAfterCaptureActivity.class.getSimpleName();

    @BindView(R.id.my_view)
    MyView mMyViewImage;

    Bitmap originalBitmap;
    Bitmap image;
    String photoFileName;
    String selectedImagePath;
    Paint paint;

    private int PIC_EDIT = 9995;

    private Uri uri = null;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vaythoi);
        ButterKnife.bind(this);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);

        //        Intent Myintent = this.getIntent();
        //        Bundle packageFromCaller = Myintent.getBundleExtra("GoiTen1");
        //        photoFileName = packageFromCaller.getString("tenfile1");

        // intent này tu Camera.
        Intent Myintent = this.getIntent();
        Bundle packageFromCaller = Myintent.getBundleExtra(MainActivity.PHOTO_FILE_NAME);
        photoFileName = packageFromCaller.getString(MainActivity.PHOTO_FILE_NAME);

        Log.e(TAG, "onCreate: " + photoFileName);

        mMyViewImage.setBitmap(readBitmapAndScale(photoFileName));
    }

    public Bitmap readBitmapAndScale(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liwwuj
        BitmapFactory.decodeFile(path, options); //Đọc thông tin ảnh
        options.inSampleSize = 4; //Scale bitmap xuống 4 lần
        options.inJustDecodeBounds = false; //Cho phép đọc dữ liệu ảnh ảnh
        return BitmapFactory.decodeFile(path, options);
    }

    @OnClick(R.id.btn_save_image)
    void saveImage() {

        long startTime = System.currentTimeMillis();
        try {
            // saveImage(mMyViewImage.getBitmap(photoFileName));
            Log.e("SaveImage",
                "Successful: " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SaveImage", "Error: " + (System.currentTimeMillis() - startTime) + "ms");
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("photoFileName", selectedImagePath);
        intent.putExtra("GoiTen", bundle);
        setResult(MainActivity.RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.btnEditPhoto)
    void editImage(){
        startActionEdit();
        Toast.makeText(EditImageAfterCaptureActivity.this, "Test", Toast.LENGTH_SHORT).show();
    }

    private void startActionEdit() {
        Intent editIntent = new Intent(
                EditImageAfterCaptureActivity.this,
                DsPhotoEditorActivity.class);

        // get uri from file with name is photoFileName.
        editIntent.setData(Uri.fromFile(new File(photoFileName)));

        editIntent.putExtra(
                DsPhotoEditorConstants.DS_PHOTO_EDITOR_API_KEY,
                "ac4c3545a20670b3459f4f8574aa9cd5565d8b46");

        // creat directory with name is UploadImage.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "UploadImage");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        editIntent.putExtra(
                DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,
                "UploadImage");

        // Disable some TOOL.
        int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CONTRAST,
                                DsPhotoEditorActivity.TOOL_FRAME, DsPhotoEditorActivity.TOOL_FILTER,
                                    DsPhotoEditorActivity.TOOL_WARMTH};

        editIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);

        startActivityForResult(editIntent,PIC_EDIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == PIC_EDIT) {
                Uri uri = data.getData();
                String path_uri = uri.getPath().toString();
                //mMyViewImage.setOriginalBitmap(path_uri);
                mMyViewImage.setBitmap(readBitmapAndScale(path_uri));

                // Lấy lại đường dẫn file ảnh.
                selectedImagePath = path_uri;
                Log.e("Test", path_uri);
            }
        }
    }

    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap =
            Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
        //has background drawable, then draw it on the canvas
        {
            bgDrawable.draw(canvas);
        } else
        //does not have background drawable, then draw white background on the canvas
        {
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    // không cần hàm này cũng được vì đường dẫn lưu file đã tạo.
    void saveImage(Bitmap bitmap) {
        String nameFile = Utils.nameFileFromCurrentTime() + ".jpg";
        selectedImagePath = "/storage/emulated/0/UploadImage/" + nameFile;
        Log.e(TAG, "saveImage: " + selectedImagePath);
        File file = new File(selectedImagePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, selectedImagePath, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}