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

public class WonRegister extends AppCompatActivity implements View.OnClickListener {

    private ImageView won_reg_insertpic;
    private Button won_img_load_btn;
    private EditText won_name_editText;
    private EditText won_price_editText;
    private EditText won_link_editText;
    private ImageButton won_reg_save;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;

    //spinner 목록 연결
    private ArrayAdapter adapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_register);

        //DB에 저장
        //spinner 목록연결
        spinner = (Spinner) findViewById(R.id.won_category_spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.won_category_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        won_reg_insertpic = (ImageView) findViewById(R.id.won_reg_insertpic);
        won_img_load_btn = (Button) findViewById(R.id.won_img_load_btn);
        won_name_editText = (EditText) findViewById(R.id.won_name_editText);
        won_price_editText = (EditText) findViewById(R.id.won_price_editText);
        won_link_editText = (EditText) findViewById(R.id.won_link_editText);
        won_reg_save = (ImageButton) findViewById(R.id.won_reg_save);

        won_img_load_btn.setOnClickListener(this);
        won_reg_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId())
        {

            case R.id.won_img_load_btn:
                selectImage();
                break;

            case R.id.won_reg_save:
                uploadImage();
                break;
        }
    }

    private void uploadImage()
    {
        //저장하기
        String image = imageToString();
        String name = won_name_editText.getText().toString();
        String category = spinner.getSelectedItem().toString(); //spinner 가져오기 ??
        String price = won_price_editText.getText().toString();
        String link  = won_link_editText.getText().toString();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClassWon> call = apiInterface.uploadImageWon(name, image, category, price, link);

        call.enqueue(new Callback<ImageClassWon>() {
            @Override
            public void onResponse(Call<ImageClassWon> call, Response<ImageClassWon> response) {
                //실행 완료 후 Main으로 이동
                Toast.makeText(WonRegister.this, "등록되었습니다." ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WonRegister.this, Main.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ImageClassWon> call, Throwable t) {
                Toast.makeText(WonRegister.this, "실패하였습니다." ,Toast.LENGTH_SHORT).show();
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
                won_reg_insertpic.setImageBitmap(bitmap);

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
