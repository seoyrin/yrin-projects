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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ReviewMain extends AppCompatActivity {

    //board_upload
    private static final String BASE_URL = "http://syr0527.cafe24.com";
    private static final String FULL_URL = BASE_URL+"/board_upload.php"; //연결할 php파일

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    class Board{
        @SerializedName("id") //db칼럼 명
        private int id;
        @SerializedName("imgurl")
        private String imgurl;
        @SerializedName("title")
        private String title;
        @SerializedName("writer")
        private String writer;
        @SerializedName("pw")
        private String pw;
        @SerializedName("star")
        private Float star;  //별점
        @SerializedName("review")
        private String review;
        @SerializedName("today")
        private String today;

        public Board(int id, String imgurl, String title, String writer, String pw, Float star, String review, String today){
            this.id = id;
            this.imgurl = imgurl;
            this.title = title;
            this.writer = writer;
            this.pw = pw;
            this.star = star;
            this.review = review;
            this.today = today;
        }

        /*GETTERS ANS SETTERS*/
        public int getId(){
            return id;
        }
        public void setId(int id){
            this.id = id;
        }
        public String getTitle(){
            return title;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getImgurl() { return imgurl; }
        public String getWriter() { return writer; }
        public String getPw(){ return pw; }
        public Float getStar() { return star; }  //별점
        public String getReview() { return review; }
        public String getToday() { return today; }

        @Override
        public String toString() {
            return title;
        }
    }

    interface MyAPIService {
        @GET("/board_upload.php")
        Call<List<ReviewMain.Board>> getBoards();
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

        private List<ReviewMain.Board> boards;
        private Context context;

        public ListViewAdapter(Context context, List<ReviewMain.Board> boards) {
            this.context = context;
            this.boards = boards;
        }

        @Override
        public int getCount() {
            return boards.size();
        }

        @Override
        public Object getItem(int pos) {
            return boards.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.activity_review_listmodel, viewGroup, false);
            } //수정한곳

            TextView TitleText = view.findViewById(R.id.TitleText);
            TextView DetailText = view.findViewById(R.id.DetailText);
            TextView WriterText = view.findViewById(R.id.WriterText);
            ImageView rm_detail2 = view.findViewById(R.id.rm_detail2);
            TextView DateText = view.findViewById(R.id.DateText); //날짜 표시

            final ReviewMain.Board thisBoard = boards.get(position);

            TitleText.setText(thisBoard.getTitle());
            DetailText.setText(thisBoard.getReview());
            WriterText.setText(thisBoard.getWriter());
            DateText.setText(thisBoard.getToday());

            if (thisBoard.getImgurl() != null && thisBoard.getImgurl().length() > 0) {
                Picasso.get().load(thisBoard.getImgurl()).into(rm_detail2);
            } else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_SHORT).show();
                Picasso.get().load(R.drawable.empty_image).into(rm_detail2);
            }

            view.setOnClickListener(new View.OnClickListener() { //수정하기 view대신 이미지 버튼 아이디 가능
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, thisOnline.getName(), Toast.LENGTH_SHORT).show();
                    String[] boards = {
                            thisBoard.getTitle(),
                            thisBoard.getWriter(),
                            thisBoard.getPw(),
                            thisBoard.getImgurl(),
                            thisBoard.getStar().toString(), //별점
                            thisBoard.getReview(),
                            thisBoard.getToday()
                    };
                    openDetailActivity(boards);
                }
            });

            return view;
        }

        private void openDetailActivity(String[] data) {
            Intent intent = new Intent(ReviewMain.this, ReviewDetail.class);
            intent.putExtra("TITLE_KEY", data[0]);
            intent.putExtra("WRITER_KEY", data[1]);
            intent.putExtra("PW_KEY", data[2]);
            intent.putExtra("IMAGE_KEY", data[3]);
            intent.putExtra("STAR_KEY", data[4]);
            intent.putExtra("REVIEW_KEY", data[5]);
            intent.putExtra("TODAY_KEY", data[6]);
            startActivity(intent);
        }
    }

    private ReviewMain.ListViewAdapter adapter;
    private ListView ListView1;

    private  void populateListView(List<ReviewMain.Board> boardList) {
        ListView1 = findViewById(R.id.ListView1);
        adapter = new ListViewAdapter(this,boardList);
        ListView1.setAdapter(adapter);
        ListView1.setDivider(null); //리스트 선 없애기
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main);

        //board_upload
        ReviewMain.MyAPIService myAPIService = ReviewMain.RetrofitClientInstance.getRetrofitInstance().create(ReviewMain.MyAPIService.class);

        Call<List<ReviewMain.Board>> call = myAPIService.getBoards();
        call.enqueue(new Callback<List<ReviewMain.Board>>() {

            @Override
            public void onResponse(Call<List<ReviewMain.Board>> call, Response<List<ReviewMain.Board>> response){
                populateListView(response.body());
            }
            @Override
            public  void onFailure(Call<List<ReviewMain.Board>> call, Throwable throwable){
                Toast.makeText(ReviewMain.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        } );

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_review_main);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton menu_review=(ImageButton)findViewById(R.id.menu_review);
        menu_review.setOnClickListener(new View.OnClickListener() {
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

    //[뒤로가기] 버튼
    public void onButtonBackClicked(View v) {
        finish();
    }

    //[글쓰기] 버튼
    public void onButtonReviewInsertClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), ReviewInsert.class);
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
