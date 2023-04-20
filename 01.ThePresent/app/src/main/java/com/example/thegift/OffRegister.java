package com.example.thegift;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffRegister extends AppCompatActivity implements View.OnClickListener{

    private ImageView insert_pic;
    private Button off_img_load_btn;
    //private EditText on_num_editText;
    //private Spinner on_category_spinner;
    private EditText off_name_editText;
    private EditText off_hastag_editText;
    private EditText off_explain_editText;
    private EditText off_tel_editText;
    private EditText off_addr_editText;
    private EditText off_time_editText;
    private EditText off_snspage_editText;
    private EditText off_reserve_editText;
    private ImageButton save;

    //private int PICK_IMAGE_REQUEST = 1;
    //storage permission code
    private static final int IMG_REQUEST = 777;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    //private Uri filePath;

    //spinner 목록 연결(dropdownlist)
    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_register);

        //DB에 저장
        //spinner 목록연결(dropdownlist)
        spinner = (Spinner) findViewById(R.id.off_category_spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.off_category_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        insert_pic = (ImageView) findViewById(R.id.insert_pic);
        off_img_load_btn = (Button) findViewById(R.id.off_img_load_btn);
        //on_num_editText = (EditText) findViewById(R.id.on_num_editText);
        //on_category_spinner = (Spinner) findViewById(R.id.on_category_spinner);
        off_name_editText = (EditText) findViewById(R.id.off_name_editText);
        off_hastag_editText = (EditText) findViewById(R.id.off_hastag_editText);
        off_explain_editText = (EditText) findViewById(R.id.off_explain_editText);
        off_tel_editText = (EditText) findViewById(R.id.off_tel_editText);
        off_addr_editText = (EditText) findViewById(R.id.off_addr_editText);
        off_time_editText = (EditText) findViewById(R.id.off_time_editText);
        off_snspage_editText = (EditText) findViewById(R.id.off_snspage_editText);
        off_reserve_editText = (EditText) findViewById(R.id.off_reserve_editText);
        save = (ImageButton) findViewById(R.id.save);

        off_img_load_btn.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId())
        {

            case R.id.off_img_load_btn:
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
        String name = off_name_editText.getText().toString();
        String category = spinner.getSelectedItem().toString(); //spinner 가져오기 ??
        String tag = off_hastag_editText.getText().toString();
        String introduce = off_explain_editText.getText().toString();
        String tel = off_tel_editText.getText().toString();
        String address = off_addr_editText.getText().toString();
        String time  = off_time_editText.getText().toString();
        String sns = off_snspage_editText.getText().toString();
        String link = off_reserve_editText.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClassOff> call = apiInterface.uploadImageOff(name, image, category, tag, introduce, tel, address, time, sns, link);

        call.enqueue(new Callback<ImageClassOff>() {
            @Override
            public void onResponse(Call<ImageClassOff> call, Response<ImageClassOff> response) {

                //ImageClass imageClass = response.body();
                //Toast.makeText(OnRegister.this,"Server Response: " +imageClass.getResponse(),Toast.LENGTH_LONG).show();
                //실행 완료 후 Main으로 이동
                Toast.makeText(OffRegister.this, "등록되었습니다." ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OffRegister.this, Main.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ImageClassOff> call, Throwable t) {
                Toast.makeText(OffRegister.this, "실패하였습니다." ,Toast.LENGTH_SHORT).show();
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
