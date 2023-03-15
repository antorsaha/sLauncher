package com.sikderIthub.slauncher;

import static com.sikderIthub.slauncher.Utils.getInstalledAppList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sikderIthub.slauncher.adapters.AppAdapter;
import com.sikderIthub.slauncher.adapters.ViewPagerAdapter;
import com.sikderIthub.slauncher.models.Apps;
import com.sikderIthub.slauncher.models.Pager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private final String PREFS_NAME = "NovaPrefs";
    int DRAWER_PEEK_HEIGHT = 100;
    int numRow = 0;
    int numColumn = 0;
    int cellHeight;
    Apps mAppDrag;
    List<Apps> installedAppList = new ArrayList<>();


    //views
    LinearLayout mTopDrawerLayout;
    ImageButton mSettings;
    ViewPager2 mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    GridView mDrawerGridView;
    BottomSheetBehavior mBottomSheetBehavior;
    View mBottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        getData();

        initViews();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onBackPressed() {

    }

    private void initViews(){
        mTopDrawerLayout = findViewById(R.id.topDrawerLayout);
        mSettings = findViewById(R.id.settings);
        mViewPager = findViewById(R.id.vp_home_viewPager);





        mTopDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                DRAWER_PEEK_HEIGHT = mTopDrawerLayout.getHeight();
                initHome();
                initDrawer();
            }
        });

        mSettings.setOnClickListener(this);
    }

    private void initHome(){
        mViewPager = findViewById(R.id.vp_home_viewPager);

        ArrayList<Apps> appList1 = new ArrayList<>();
        ArrayList<Apps> appList2 = new ArrayList<>();
        ArrayList<Apps> appList3 = new ArrayList<>();

        for (int i = 0; i < numColumn*numRow ;i++)
            appList1.add(new Apps("", "",
                    ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground),
                    false));
        for (int i = 0; i < numColumn*numRow ;i++)
            appList2.add(new Apps("", "",
                    ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground),
                    false));
        for (int i = 0; i < numColumn*numRow ;i++)
            appList3.add(new Apps("", "",
                    ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground),
                    false));

        ArrayList<Pager> pagerList = new ArrayList<>();
        pagerList.add(new Pager(appList1));
        pagerList.add(new Pager(appList2));
        pagerList.add(new Pager(appList3));

        cellHeight = (getDisplayContentHeight() - DRAWER_PEEK_HEIGHT) / numRow ;

        mViewPagerAdapter = new ViewPagerAdapter(this, pagerList, cellHeight, numColumn);
        mViewPager.setAdapter(mViewPagerAdapter);

    }

    private void initDrawer(){
        View mBottomSheet =findViewById(R.id.bottomSheet);
        mDrawerGridView = findViewById(R.id.gv_drawer_grid);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setPeekHeight(DRAWER_PEEK_HEIGHT);

        installedAppList = getInstalledAppList(this);

        Log.d(TAG, "initDrawer: mDrawerGridView " + mDrawerGridView);
        mDrawerGridView.setAdapter(new AppAdapter(this, installedAppList, cellHeight));

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(mAppDrag != null)
                    return;

                if(newState == BottomSheetBehavior.STATE_COLLAPSED && mDrawerGridView.getChildAt(0).getY() != 0)
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(newState == BottomSheetBehavior.STATE_DRAGGING && mDrawerGridView.getChildAt(0).getY() != 0)
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

    }

    private void getData(){
        ImageView mHomeScreenImage = findViewById(R.id.iv_home_screen_image);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String imageUri = sharedPreferences.getString("imageUri", null);
        int numRow = sharedPreferences.getInt("numRow", 7);
        int numColumn = sharedPreferences.getInt("numColumn", 5);

        if(this.numRow != numRow || this.numColumn != numColumn){
            this.numRow = numRow;
            this.numColumn = numColumn;
            initHome();
        }

        if(imageUri != null)
            mHomeScreenImage.setImageURI(Uri.parse(imageUri));

    }


    private int getDisplayContentHeight() {
        final WindowManager windowManager = getWindowManager();
        final Point size = new Point();
        int screenHeight = 0, actionBarHeight = 0, statusBarHeight = 0;
        if(getActionBar()!=null){
            actionBarHeight = getActionBar().getHeight();
        }

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0){
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int contentTop = (findViewById(android.R.id.content)).getTop();
        windowManager.getDefaultDisplay().getSize(size);
        screenHeight = size.y;
        return screenHeight - contentTop - actionBarHeight - statusBarHeight;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.settings:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void itemPress(Apps app){
        Log.d(TAG, "itemPress: appName " + app.getName());
        if(mAppDrag != null && !app.getName().equals("")){
            Toast.makeText(this,"Cell Already Occupied", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mAppDrag != null && !app.isAppInDrawer()){

            app.setPackageName(mAppDrag.getPackageName());
            app.setName(mAppDrag.getName());
            app.setImage(mAppDrag.getImage());
            app.setAppInDrawer(false);

            if(!mAppDrag.isAppInDrawer()){
                mAppDrag.setPackageName("");
                mAppDrag.setName("");
                mAppDrag.setImage(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground));
                mAppDrag.setAppInDrawer(false);
            }
            mAppDrag = null;
            mViewPagerAdapter.notifyGridChanged();
            return;
        }else{
            Intent launchAppIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            if (launchAppIntent != null)
                getApplicationContext().startActivity(launchAppIntent);
        }
    }

    public void itemLongPress(Apps app){
        collapseDrawer();
        mAppDrag = app;
    }
    private void collapseDrawer() {
        /*mDrawerGridView.setY(DRAWER_PEEK_HEIGHT);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);*/

        //TODO COLLAPSE DRAWER
    }
}