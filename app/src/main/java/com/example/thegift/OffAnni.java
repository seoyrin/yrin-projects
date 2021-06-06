package com.example.thegift;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class OffAnni extends AppCompatActivity {

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    //off_anni_upload
    private static final String BASE_URL = "http://syr0527.cafe24.com";//"http://syr0527.cafe24.com";
    private static final String FULL_URL = BASE_URL+"/off_anni_upload.php";

    class Offline{
        @SerializedName("id") //db칼럼 명
        private int id;
        @SerializedName("imgurl")
        private String imgurl;
        @SerializedName("name")
        private String name;
        @SerializedName("tag")
        private String tag;
        @SerializedName("introduce")
        private String introduce;
        @SerializedName("telnumber")
        private String telnumber;
        @SerializedName("address")
        private String address;
        @SerializedName("opentime")
        private String opentime;
        @SerializedName("snspage")
        private String snspage;
        @SerializedName("link")
        private String link;

        public Offline(int id, String imgurl, String name, String tag, String introduce,String telnumber, String address, String opentime,String snspage, String link){
            this.id = id;
            this.imgurl = imgurl;
            this.name = name;
            this.tag = tag;
            this.introduce = introduce;
            this.telnumber = telnumber;
            this.address = address;
            this.opentime = opentime;
            this.snspage = snspage;
            this.link = link;
        }

        /*GETTERS ANS SETTERS*/
        public int getId(){
            return id;
        }
        public void setId(int id){
            this.id = id;
        }
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getImgurl() { return imgurl; }
        public String getTag() { return tag; }
        public String getIntroduce(){ return introduce; }
        public String getTelnumber() { return telnumber; }
        public String getAddress() { return address; }
        public String getOpentime() { return opentime; }
        public String getSnspage() { return snspage; }
        public String getLink() { return link; }

        @Override
        public String toString() {
            return name;
        }
    }

    interface MyAPIService {
        @GET("/off_anni_upload.php")
        Call<List<OffAnni.Offline>> getOfflines();
    }

    static class RetrofitClientInstance {

        private static Retrofit retrofit;

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit= new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    class ListViewAdapter extends BaseAdapter {

        private List<OffAnni.Offline> offlines;
        private Context context;

        public ListViewAdapter(Context context, List<OffAnni.Offline> offlines) {
            this.context = context;
            this.offlines = offlines;
        }

        @Override
        public int getCount() {
            return offlines.size();
        }

        @Override
        public Object getItem(int pos) {
            return offlines.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.activity_on_1anni_listmodel, viewGroup, false);
            } //수정한곳 activity_on_1anni_listmodel

            TextView TitleText = view.findViewById(R.id.TitleText);
            TextView DetailText = view.findViewById(R.id.DetailText);
            TextView TagText = view.findViewById(R.id.TagText);
            ImageView on_anni_imageButton2 = view.findViewById(R.id.on_anni_imageButton2);

            final OffAnni.Offline thisOffline = offlines.get(position);

            TitleText.setText(thisOffline.getName());
            DetailText.setText(thisOffline.getIntroduce());
            TagText.setText(thisOffline.getTag());

            if (thisOffline.getImgurl() != null && thisOffline.getImgurl().length() > 0) {
                Picasso.get().load(thisOffline.getImgurl()).into(on_anni_imageButton2);
            } else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_SHORT).show();
                Picasso.get().load(R.drawable.empty_image).into(on_anni_imageButton2);
            }

            view.setOnClickListener(new View.OnClickListener() { //수정하기 view대신 이미지 버튼 아이디 가능
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, thisOffline.getName(), Toast.LENGTH_SHORT).show();
                    String[] offlines = {
                            thisOffline.getName(),
                            thisOffline.getIntroduce(),
                            thisOffline.getTag(),
                            thisOffline.getImgurl(),
                            thisOffline.getTelnumber(),
                            thisOffline.getAddress(),
                            thisOffline.getOpentime(),
                            thisOffline.getSnspage(),
                            thisOffline.getLink()
                    };
                    openDetailActivity(offlines);
                }
            });

            return view;
        }

        private void openDetailActivity(String[] data) {
            Intent intent = new Intent(OffAnni.this, OffAnniDetail.class);
            intent.putExtra("NAME_KEY", data[0]);
            intent.putExtra("INTRODUCE_KEY", data[1]);
            intent.putExtra("TAG_KEY", data[2]);
            intent.putExtra("IMAGE_KEY", data[3]);
            intent.putExtra("TELNUMBER_KEY", data[4]);
            intent.putExtra("ADDRESS_KEY", data[5]);
            intent.putExtra("OPENTIME_KEY", data[6]);
            intent.putExtra("SNSPAGE_KEY", data[7]);
            intent.putExtra("LINK_KEY", data[8]);
            startActivity(intent);
        }
    }

    private OffAnni.ListViewAdapter adapter;
    private ListView ListView1;


    private  void populateListView(List<OffAnni.Offline> offlineList) {
        ListView1 = findViewById(R.id.ListView1);
        adapter = new OffAnni.ListViewAdapter(this,offlineList);
        ListView1.setAdapter(adapter);
        ListView1.setDivider(null); //리스트 선 없애기
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_1anni);

        OffAnni.MyAPIService myAPIService = OffAnni.RetrofitClientInstance.getRetrofitInstance().create(OffAnni.MyAPIService.class);

        Call<List<OffAnni.Offline>> call = myAPIService.getOfflines();
        call.enqueue(new Callback<List<OffAnni.Offline>>() {

            @Override
            public void onResponse(Call<List<OffAnni.Offline>> call, Response<List<OffAnni.Offline>> response){
                populateListView(response.body());
            }
            @Override
            public  void onFailure(Call<List<OffAnni.Offline>> call, Throwable throwable){
                Toast.makeText(OffAnni.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        } );

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_off_1anni);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton menu_off_anni=(ImageButton)findViewById(R.id.menu_off_anni);
        menu_off_anni.setOnClickListener(new View.OnClickListener() {
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

    public void onButtonOnlineClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnAnni.class);
        startActivity(intent);
    }
    public void onButtonOfflineClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OffAnni.class);
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
