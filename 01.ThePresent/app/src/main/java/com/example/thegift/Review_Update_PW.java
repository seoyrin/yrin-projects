package com.example.thegift;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class Review_Update_PW extends AppCompatActivity implements View.OnClickListener {

    private EditText ri_insert_pw;
    private ImageButton ri_ok;

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_update_pw);

        ri_insert_pw = (EditText) findViewById(R.id.ri_insert_pw);
        ri_ok = findViewById(R.id.ri_ok);

        ri_ok.setOnClickListener(this);

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_review_update_pw);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton ri_menu=(ImageButton)findViewById(R.id.ri_menu);
        ri_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        ImageButton menu_exit = (ImageButton)findViewById(R.id.menu_exit);
        menu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View view, float v) {

        }

        @Override
        public void onDrawerOpened(@NonNull View view) {

        }

        @Override
        public void onDrawerClosed(@NonNull View view) {

        }

        @Override
        public void onDrawerStateChanged(int i) {

        }
    };  //menu (drawer) 끝

    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.ri_ok:
                insertPassword();
                break;
        }
    }

    private void insertPassword() {
        OkHttpClient okHttpClient = new OkHttpClient();

        String pw = ri_insert_pw.getText().toString();
        RequestBody requestBody = new FormBody.Builder()
                .add("pw", pw)
                .build();

        String url = "http://syr0527.cafe24.com/board_pw_upload.php";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String json = response.body().string();

                if (json.equals("null")) {
                    // 비밀번호 틀림

                    // AlertDialog 는 메인 쓰레드에서 작동되어야함
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            // 배열값을 받아오지 않았을 때 창 띄우기
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Review_Update_PW.this);
                            alertDialog.setTitle("알림");
                            alertDialog.setMessage("비밀번호가 일치하지 않습니다.");
                            alertDialog.setNegativeButton("확인", null);
                            alertDialog.show();
                        }
                    });
                } else {
                    Log.d("Review_Update_PW", "서버에서 응답한 데이터:" + json);

                    Gson gson = new Gson();
                    // 베열 형식을 json 데이터를 객체화 시키는 방법
                    Type type = new TypeToken<ArrayList<Review>>() {}.getType();
                    ArrayList<Review> reviews = gson.fromJson(json, type);

                    // Review_Update 에 받은 데이터를 넘김
                    Intent intent = new Intent(Review_Update_PW.this, Review_Update.class);
                    intent.putExtra("reviews", reviews);
                    startActivity(intent);

                    // 종료
                    finish();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("Review_Update_PW", "콜백오류:" + e.getMessage());
            }
        });
    }

    //뒤로가기
    public void onButtonBackClicked(View v) {
        finish();
    }

    //Bottom 버튼
    public void onButtonHomeClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
    }
    public void onButtonLikeClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), AllOn.class);
        startActivity(intent);
    }
    public void onButtonReviewClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), ReviewMain.class);
        startActivity(intent);
    }

    //menu버튼 클릭을 위한 메소드 생성
    // 기념일
    public void onButtonAnniClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OnAnni.class);
        startActivity(intent);
    }

    // days
    public void onButtonDaysClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OnDays.class);
        startActivity(intent);
    }

    // 가성비
    public void onButtonMoneyClicked(View v){
        Intent intent = new Intent(getApplicationContext(), Won5000.class);
        startActivity(intent);
    }

    // 꽃
    public void onButtonFlowerClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OnFlower.class);
        startActivity(intent);
    }

    // 케이크
    public void onButtonCakeClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OnCake.class);
        startActivity(intent);
    }

    // 커플용품
    public void onButtonCoupleClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OnCouple.class);
        startActivity(intent);
    }

    //등록화면으로 이동
    public void onButtonOnregisterClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OnRegister.class);
        startActivity(intent);
    }

    public void onButtonOffregisterClicked(View v){
        Intent intent = new Intent(getApplicationContext(), OffRegister.class);
        startActivity(intent);
    }

    public void onButtonWonregisterClicked(View v){
        Intent intent = new Intent(getApplicationContext(), WonRegister.class);
        startActivity(intent);
    }

    // 전체보기-상단에 정의되어 있음

    // 후기게시판
    public void onButtonReviewmainClicked(View v){
        Intent intent = new Intent(getApplicationContext(), ReviewMain.class);
        startActivity(intent);
    }
}
