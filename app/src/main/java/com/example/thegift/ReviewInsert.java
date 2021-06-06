package com.example.thegift;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Rating;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewInsert extends AppCompatActivity implements View.OnClickListener {

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    private ImageView ri_insertpic;
    //private Button on_img_load_btn;
    //private EditText on_num_editText;
    private EditText ri_title_editText;
    private EditText ri_writer_editText;
    private EditText ri_pw_editText;
    private RatingBar ratingBar;
    private EditText ri_multiline;
    private ImageView chpic;
    private ImageButton ri_save;

    //private int PICK_IMAGE_REQUEST = 1;
    //storage permission code
    private final int GALLERY_CODE = 1112;
    //Bitmap to get image from gallery
    private Bitmap bitmap1;
    //Uri to store the image uri
    //private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_insert);

        //이미지 회전(접근허가)
        tedPermission();

        //DB에 저장
        ri_insertpic = (ImageView) findViewById(R.id.ri_insertpic);
        ri_title_editText = (EditText) findViewById(R.id.ri_title_editText);
        ri_writer_editText = (EditText) findViewById(R.id.ri_writer_editText);
        ri_pw_editText = (EditText) findViewById(R.id.ri_pw_editText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ri_multiline = (EditText) findViewById(R.id.ri_multiline);
        chpic = (ImageView) findViewById(R.id.chpic);
        ri_save = (ImageButton) findViewById(R.id.ri_save);

        chpic.setOnClickListener(this);
        ri_save.setOnClickListener(this);

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_review_insert);
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

    @Override
    public void onClick(View v){

        switch (v.getId())
        {

            case R.id.chpic:
                selectImage();
                break;

            case R.id.ri_save:
                uploadImage();
                break;
        }
    }

    //날짜 표시
    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String today=dateFormat.format(date);
        return today;
    }

    private void uploadImage()
    {
        String image = imageToString();
        String title = ri_title_editText.getText().toString();
        String writer = ri_writer_editText.getText().toString();
        String pw = ri_pw_editText.getText().toString();
        Float star = (Float)ratingBar.getRating(); //ratingbar저장 방법
        String review = ri_multiline.getText().toString();
        String today = getDateToday(); //날짜 표시

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageClassBoard> call = apiInterface.uploadImageBoard( title, image, writer, pw, star, review, today);

        call.enqueue(new Callback<ImageClassBoard>() {
            @Override
            public void onResponse(Call<ImageClassBoard> call, Response<ImageClassBoard> response) {

                //ImageClass imageClass = response.body();
                //Toast.makeText(OnRegister.this,"Server Response: " +imageClass.getResponse(),Toast.LENGTH_LONG).show();
                Toast.makeText(ReviewInsert.this, "등록되었습니다." ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewInsert.this, ReviewMain.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ImageClassBoard> call, Throwable t) {
                Toast.makeText(ReviewInsert.this, "실패하였습니다." ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //이미지 회전(접근허가)
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


    }

    //앨범열기
    private void selectImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    sendPicture(data.getData());
                    break;
                default:
                    break;
            }
        }
    }

    //이미지 회전(시작)
    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        ri_insertpic.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
        bitmap1=rotate(bitmap, exifDegree);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }
    //이미지 회전 (끝)

    private String imageToString()
    {
        if (bitmap1 !=null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgByte, Base64.DEFAULT);
        }
        else
        {
            return("");
        }
    }




    //Top 버튼
    public void onButtonBackClicked(View v) {
        finish();
    }

    //[취소]버튼
    public void onButtonReviewMainClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), ReviewMain.class);
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
