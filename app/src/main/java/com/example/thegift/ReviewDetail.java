package com.example.thegift;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ReviewDetail extends AppCompatActivity{

    //board_upload
    //리스트에 해당하는 정보 불러오기
    TextView ReviewTitleText,ReviewWriterText,ReviewDateText,ReviewText;
    ImageView rd_pic;
    RatingBar ratingBar;

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    private void initializewidgets(){
        ReviewTitleText= findViewById(R.id.ReviewTitleText);
        ReviewWriterText= findViewById(R.id.ReviewWriterText);
        ReviewDateText= findViewById(R.id.ReviewDateText);
        ReviewText= findViewById(R.id.ReviewText);
        ratingBar= findViewById(R.id.ratingBar);
        rd_pic= findViewById(R.id.rd_pic);
    }

    private  void receiveAndShowData() {
        //RECEIVE DATA FROM ITEMS ACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String title=i.getExtras().getString("TITLE_KEY");
        String writer=i.getExtras().getString("WRITER_KEY");
        String today=i.getExtras().getString("TODAY_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");
        String review=i.getExtras().getString("REVIEW_KEY");
        //final String star=i.getExtras().getString("STAR_KEY");
        float star = Float.valueOf(getIntent().getExtras().getString("STAR_KEY"));


        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        ReviewTitleText.setText(title);
        ReviewWriterText.setText(writer);
        ReviewDateText.setText(today);
        ReviewText.setText(review);
        ratingBar.setRating(star);

        Picasso.get().load(imageURL).placeholder(R.drawable.pic).into(rd_pic);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        //board_upload
        initializewidgets();
        receiveAndShowData();

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_review_detail);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton r_detail_menu=(ImageButton)findViewById(R.id.r_detail_menu);
        r_detail_menu.setOnClickListener(new View.OnClickListener() {
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

    //Top 버튼
    public void onButtonBackClicked(View v) {
        finish();
    }

    //[수정/삭제]버튼
    public void onButtonUpdateDeleteClicked(View v){
        Intent intent = new Intent(getApplicationContext(), Review_Update_PW.class);
        startActivity(intent);
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

