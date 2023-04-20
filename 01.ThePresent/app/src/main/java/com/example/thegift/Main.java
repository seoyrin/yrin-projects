
package com.example.thegift;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    //menu (drawer) 시작
    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //menu (drawer) 중간
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        drawerView = (View)findViewById(R.id.activity_menu);

        ImageButton main_menu=(ImageButton)findViewById(R.id.main_menu);
        main_menu.setOnClickListener(new View.OnClickListener() {
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

    //[뒤로가기] 버튼(누르면 앱 종료)
    public void onButtonFinishClicked(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main.this);
        alertDialog.setTitle("알림")
                .setMessage("종료하시겠습니까?")
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //모든 액티비티 종료
                        ActivityCompat.finishAffinity(Main.this);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        //int pid = android.os.Process.myPid();
                        //android.os.Process.killProcess(pid);
                        //앱 종료
                    }
                })
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Main.this, "취소되었습니다." ,Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    //Main 버튼 6개
    public void onButtonAnniClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnAnni.class);
        startActivity(intent);
    }
    public  void onButtonDaysClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnDays.class);
        startActivity(intent);
    }
    public  void onButtonMoneyClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), Won5000.class);
        startActivity(intent);
    }
    public  void onButtonFlowerClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnFlower.class);
        startActivity(intent);
    }
    public  void onButtonCakeClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnCake.class);
        startActivity(intent);
    }
    public  void onButtonCoupleClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnCouple.class);
        startActivity(intent);
    }


    //menu버튼 클릭을 위한 메소드 생성
    public  void onButtonLikeClicked(View v) {
        // 좋아요
        Intent intent = new Intent(getApplicationContext(), AllOn.class);
        startActivity(intent);
    }

    public  void onButtonReviewmainClicked(View v) {
        // 후기 게시판
        Intent intent = new Intent(getApplicationContext(), ReviewMain.class);
        startActivity(intent);
    }

    public  void onButtonOnregisterClicked(View v) {
        // on_register
        Intent intent = new Intent(getApplicationContext(), OnRegister.class);
        startActivity(intent);
    }

    public  void onButtonOffregisterClicked(View v) {
        // off_register
        Intent intent = new Intent(getApplicationContext(), OffRegister.class);
        startActivity(intent);
    }

    public  void onButtonWonregisterClicked(View v) {
        // won_register
        Intent intent = new Intent(getApplicationContext(), WonRegister.class);
        startActivity(intent);
    }
}
