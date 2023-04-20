package com.example.thegift;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnRegister extends AppCompatActivity implements View.OnClickListener {

    private ImageView insert_pic;
    private Button on_img_load_btn;
    //private EditText on_num_editText;
    //private Spinner on_category_spinner;
    private EditText on_name_editText;
    private EditText on_hastag_editText;
    private EditText on_explain_editText;
    private EditText on_price_editText;
    private EditText on_link_editText;
    private EditText on_snspage_editText;
    private ImageButton save;

    //private int PICK_IMAGE_REQUEST = 1;
    //storage permission code
    private static final int IMG_REQUEST = 777;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    //private Uri filePath;

    //spinner 목록 연결
    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_register);

        //DB에 저장
        //requestStoragePermission();
        //spinner 목록연결
        spinner = (Spinner) findViewById(R.id.on_category_spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.on_category_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        insert_pic = (ImageView) findViewById(R.id.insert_pic);
        on_img_load_btn = (Button) findViewById(R.id.on_img_load_btn);
        //on_num_editText = (EditText) findViewById(R.id.on_num_editText);
        //on_category_spinner = (Spinner) findViewById(R.id.on_category_spinner);
        on_name_editText = (EditText) findViewById(R.id.on_name_editText);
        on_hastag_editText = (EditText) findViewById(R.id.on_hastag_editText);
        on_explain_editText = (EditText) findViewById(R.id.on_explain_editText);
        on_price_editText = (EditText) findViewById(R.id.on_price_editText);
        on_link_editText = (EditText) findViewById(R.id.on_link_editText);
        on_snspage_editText = (EditText) findViewById(R.id.on_snspage_editText);
        save = (ImageButton) findViewById(R.id.save);

        on_img_load_btn.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId())
        {

            case R.id.on_img_load_btn:
                selectImage();
                break;

            case R.id.save:
                uploadImage();
                break;
        }
    }

    private void uploadImage()
    {
        String image = imageToString();
        String name = on_name_editText.getText().toString();
        String category = spinner.getSelectedItem().toString(); //spinner 가져오기 ??
        String tag = on_hastag_editText.getText().toString();
        String introduce = on_explain_editText.getText().toString();
        String price = on_price_editText.getText().toString();
        String site  = on_link_editText.getText().toString();
        String sns = on_snspage_editText.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClass> call = apiInterface.uploadImage(name, image, category,tag, introduce, price, site, sns);

        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {

                //실행 완료 후 Main으로 이동
                Toast.makeText(OnRegister.this, "등록되었습니다." ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OnRegister.this, Main.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {
                Toast.makeText(OnRegister.this, "실패하였습니다." ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                insert_pic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    //Top  버튼
    public void onButtonExitClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
    }
}
