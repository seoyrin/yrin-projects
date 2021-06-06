package com.example.thegift;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OnDaysDetail extends AppCompatActivity {

    //리스트에 해당하는 정보 불러오기
    TextView on_detail_TitleText,on_detail_Hastag,on_detail_intro,on_detail_price,on_detail_site,on_detail_sns;
    ImageView pic;

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    private void initializewidgets(){
        on_detail_TitleText= findViewById(R.id.on_detail_TitleText);
        on_detail_Hastag= findViewById(R.id.on_detail_Hastag);
        on_detail_intro= findViewById(R.id.on_detail_intro);
        on_detail_price= findViewById(R.id.on_detail_price);
        on_detail_site= findViewById(R.id.on_detail_site);
        on_detail_sns= findViewById(R.id.on_detail_sns);
        pic= findViewById(R.id.pic);
    }

    private  void receiveAndShowData() {
        //RECEIVE DATA FROM ITEMS ACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String name=i.getExtras().getString("NAME_KEY");
        String introduce=i.getExtras().getString("INTRODUCE_KEY");
        String tag=i.getExtras().getString("TAG_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");
        String price=i.getExtras().getString("PRICE_KEY");
        final String site=i.getExtras().getString("SITE_KEY");
        final String sns=i.getExtras().getString("SNS_KEY");

        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        on_detail_TitleText.setText(name);
        on_detail_Hastag.setText(tag);
        on_detail_intro.setText(introduce);
        on_detail_price.setText(price);
        on_detail_site.setText(site);

        Picasso.get().load(imageURL).placeholder(R.drawable.empty_image).into(pic);

        //사이트 링크 이동
        on_detail_site.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                startActivity(myIntent);
            }
        } );

        //sns가 없는 경우 -로 표시
        if (sns.equals(""))
        {
            on_detail_sns.setText("-");
        }
        else
        {
            on_detail_sns.setText(sns);

            //sns페이지 이동
            on_detail_sns.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sns));
                    startActivity(myIntent);
                }
            } );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_2days_detail);

        initializewidgets();
        receiveAndShowData();

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_on_2days_detail);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton main_menu=(ImageButton)findViewById(R.id.menu_off_anni);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        ImageButton menu_off_anni = (ImageButton)findViewById(R.id.menu_exit);
        menu_off_anni.setOnClickListener(new View.OnClickListener() {
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
