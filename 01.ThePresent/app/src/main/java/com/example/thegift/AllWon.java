package com.example.thegift;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class AllWon extends AppCompatActivity {

    //won_all_upload
    private static final String BASE_URL = "http://syr0527.cafe24.com";
    private static final String FULL_URL = BASE_URL+"/won_all_upload.php"; //연결할 php파일

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    class Won{
        @SerializedName("id") //db칼럼 명
        private int id;
        @SerializedName("imgurl")
        private String imgurl;
        @SerializedName("name")
        private String name;
        @SerializedName("price")
        private String price;
        @SerializedName("link")
        private String link;

        public Won(int id, String imgurl, String name, String price, String link){
            this.id = id;
            this.imgurl = imgurl;
            this.name = name;
            this.price = price;
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
        public String getPrice() { return price; }
        public String getLink() { return link; }

        @Override
        public String toString() {
            return name;
        }
    }

    interface MyAPIService {
        @GET("/won_all_upload.php")
        Call<ArrayList<AllWon.Won>> getWons(); //수정한곳
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

    class GridViewAdapter extends BaseAdapter {

        private ArrayList<AllWon.Won> wons;
        private Context context;

        public GridViewAdapter(Context context, ArrayList<AllWon.Won> wons) {
            this.context = context;
            this.wons = wons;
        }

        @Override
        public int getCount() {
            return wons.size();
        }

        @Override
        public Object getItem(int pos) {
            return wons.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.activity_won_gridview_model, viewGroup, false);
            }

            TextView won_Product = view.findViewById(R.id.won_Product);
            TextView won_Price = view.findViewById(R.id.won_Price);
            ImageView detail_pic = view.findViewById(R.id.detail_pic);

            //final Won thisWon = wons.get(position);
            final AllWon.Won thisWon = (AllWon.Won) this.getItem(position);

            Log.d("WON", thisWon.getName());

            won_Product.setText(thisWon.getName());
            won_Price.setText(thisWon.getPrice());
            final String link = thisWon.getLink(); //이동할 페이지 링크

            if (thisWon.getImgurl() != null && thisWon.getImgurl().length() > 0) {
                Picasso.get().load(thisWon.getImgurl()).into(detail_pic);
            } else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_SHORT).show();
                Picasso.get().load(R.drawable.empty_image).into(detail_pic);
            }

            view.setOnClickListener(new View.OnClickListener() { //수정하기 view대신 이미지 버튼 아이디 가능
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(myIntent);
                }
            });
            return view;
        }
    }


    private AllWon.GridViewAdapter adapter;
    private GridView mygridview;

    private  void populateListView(ArrayList<AllWon.Won> wonList) {

        mygridview = findViewById(R.id.mygridview);
        adapter = new AllWon.GridViewAdapter(this,wonList);
        mygridview.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_won);

        //won_all_upload
        AllWon.MyAPIService myAPIService = AllWon.RetrofitClientInstance.getRetrofitInstance().create(AllWon.MyAPIService.class);

        Call<ArrayList<AllWon.Won>> call = myAPIService.getWons(); //수정한곳
        call.enqueue(new Callback<ArrayList<AllWon.Won>>() {

            @Override
            public void onResponse(Call<ArrayList<AllWon.Won>> call, Response<ArrayList<AllWon.Won>> response){
                populateListView(response.body());
            }
            @Override
            public  void onFailure(Call<ArrayList<AllWon.Won>> call, Throwable throwable){
                Toast.makeText(AllWon.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        } );

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_all_won);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton like_menu=(ImageButton)findViewById(R.id.like_menu);
        like_menu.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(getApplicationContext(), AllOn.class);
        startActivity(intent);
    }
    public void onButtonOfflineClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), AllOff.class);
        startActivity(intent);
    }
    public void onButtonWonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), AllWon.class);
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
