package com.example.thegift;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Review_Update extends AppCompatActivity {

    TextView ru_title_editText,ru_writer_editText,ru_multiline, review_num;
    ImageView ru_image;
    RatingBar ru_ratingBar;
    ImageButton ru_cancel, ru_delete, ru_save;

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_update);

        review_num = findViewById(R.id.review_num);
        ru_title_editText= findViewById(R.id.ru_title_editText);
        ru_writer_editText= findViewById(R.id.ru_writer_editText);
        ru_ratingBar= findViewById(R.id.ru_ratingBar);
        ru_multiline= findViewById(R.id.ru_multiline);
        ru_image= findViewById(R.id.ru_image);

        ru_cancel= findViewById(R.id.ru_cancel);
        ru_delete= findViewById(R.id.ru_delete);
        ru_save= findViewById(R.id.ru_save);

        // 비밀번호 확인후 넘어온 데이터
        Intent intent = getIntent();
        ArrayList<Review> reviews = intent.getParcelableArrayListExtra("reviews");
        if (reviews != null) {
            for (Review r : reviews) {
                Log.d("Review_Update", "title: " + r.getTitle());
                String id=r.getId().toString();
                String imageURL=r.getImgurl();
                String title=r.getTitle();
                String writer=r.getWriter();
                String review=r.getReview();
                float star = Float.valueOf(r.getStar());

                review_num.setText(id);
                ru_title_editText.setText(title);
                ru_writer_editText.setText(writer);
                ru_multiline.setText(review);
                ru_ratingBar.setRating(star);

                Picasso.get().load(imageURL).placeholder(R.drawable.transparent).into(ru_image);
            }
        }

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_review_update);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton ru_menu=(ImageButton)findViewById(R.id.ru_menu);
        ru_menu.setOnClickListener(new View.OnClickListener() {
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

    //삭제, 수정하기 버튼
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.ru_delete:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Review_Update.this);
                        alertDialog.setTitle("알림")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reviewDelete();
                                //finish();
                            }
                        })
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Review_Update.this, "취소되었습니다." ,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;

            case R.id.ru_save:
                reviewUpdate();
                break;
        }
    }

    //[취소]버튼
    public void onButtonCancleClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), ReviewMain.class);
        startActivity(intent);
    }

    //[삭제]버튼
    private void reviewDelete(){
        OkHttpClient okHttpClient = new OkHttpClient();

        String id = review_num.getText().toString();
        //int myid = Integer.parseInt(id);

        RequestBody requestBody = new FormBody.Builder()
                .add("id", id)
                .build();

        String url = "http://syr0527.cafe24.com/board_delete.php";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                //Log.d("Review_Update", "서버에서 응답한 데이터:" + response);
                //실행 완료 후 ReviewMain으로 이동
                //Toast.makeText(Review_Update.this, "삭제되었습니다." ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Review_Update.this, ReviewMain.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //Log.d("Review_Update", "콜백오류:" + e.getMessage());
                Toast.makeText(Review_Update.this, "삭제에 실패하였습니다." ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        }

    //[수정]버튼
    private void reviewUpdate(){
        OkHttpClient okHttpClient = new OkHttpClient();

        String id = review_num.getText().toString();
        //int myid = Integer.parseInt(id);
        String title = ru_title_editText.getText().toString();
        String writer = ru_writer_editText.getText().toString();
        String review = ru_multiline.getText().toString();
        //String star = ru_ratingBar.getRating().toString();
        Float star = (Float)ru_ratingBar.getRating();
        String mystar = star.toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("id", id)
                .add("title", title)
                .add("writer", writer)
                .add("review", review)
                //.add("star", star)
                .add("star", mystar)
                .build();

        String url = "http://syr0527.cafe24.com/board_update.php";
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                //Log.d("Review_Update", "서버에서 응답한 데이터:" + response);
                //Toast.makeText(Review_Update.this, "수정되었습니다." ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Review_Update.this, ReviewMain.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //Log.d("Review_Update", "콜백오류:" + e.getMessage());
                Toast.makeText(Review_Update.this, "수정에 실패하였습니다." ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //Top 버튼
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