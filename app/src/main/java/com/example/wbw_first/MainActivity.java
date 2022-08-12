package com.example.wbw_first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wbw_first.Adapters.VPAdapter;
import com.example.wbw_first.Adapters.VPMain;
import com.example.wbw_first.Adapters.VPUser;
import com.example.wbw_first.Controllers.PagerController;
import com.example.wbw_first.Fragments.fragmentArea;
import com.example.wbw_first.Fragments.fragmentCarouselSlider;
import com.example.wbw_first.Fragments.fragmentHistory;
import com.example.wbw_first.Fragments.fragmentHome;
import com.example.wbw_first.Fragments.fragmentListUser;
import com.example.wbw_first.Fragments.fragmentRegisterUser;
import com.example.wbw_first.Fragments.fragmentUser;
import com.example.wbw_first.Interfaces.IDrawerLayout;
import com.example.wbw_first.Utilities.Drawer;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IDrawerLayout, View.OnClickListener {

    private LinearLayout lyHome, lyUser, lyArea, lyWork, lyHistory, lyLogout, lyMainTolbar;
    private ImageView ivMenu, ivMore, ivBackMain;
    private TextView tvTitleTolbar;
    private DrawerLayout drawerLayout;
    private Context context = this;

    private VPMain vpMain;
    private TabLayout tabLayoutMain;
    private ViewPager viewPagerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialize ui
        initUI();

        // Config tab layout
        listenerTabsLayoutMain();

        // Botones en escucha
        ivMenu.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        ivBackMain.setOnClickListener(this);

        lyHome.setOnClickListener(this);
        lyUser.setOnClickListener(this);
        lyArea.setOnClickListener(this);
        lyWork.setOnClickListener(this);
        lyHistory.setOnClickListener(this);
        lyLogout.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Close drawer
        Drawer.closeDrawer(drawerLayout);
    }

    /**
     * Inicialize interface
     *
     */
    private void initUI(){
        drawerLayout = findViewById(R.id.drawerlayout);

        tabLayoutMain = findViewById(R.id.tabLayoutMain);
        viewPagerMain = findViewById(R.id.viewPagerMain);

        tvTitleTolbar = findViewById(R.id.tvTitleTolbarmain);

        ivMenu = findViewById(R.id.ivMenu);
        ivMore = findViewById(R.id.ivMoreMain);
        ivBackMain = findViewById(R.id.ivMainBack);

        lyMainTolbar = findViewById(R.id.lyMainTolbar);
        lyHome = findViewById(R.id.lyHome);
        lyUser = findViewById(R.id.lyUser);
        lyArea = findViewById(R.id.lyArea);
        lyWork = findViewById(R.id.lyWork);
        lyHistory = findViewById(R.id.lyHistory);
        lyLogout = findViewById(R.id.lyLogout);
    }

    private void listenerTabsLayoutMain(){
        vpMain = new VPMain(getSupportFragmentManager(), tabLayoutMain.getTabCount());
        viewPagerMain.setAdapter(vpMain);

        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayoutMain.selectTab(tabLayoutMain.getTabAt(position));
                vpMain.notifyDataSetChanged();

                // Ocultar tolbar TOP
                if(position != 0){
                    lyMainTolbar.setVisibility(View.GONE);
                } else {
                    lyMainTolbar.setVisibility(View.VISIBLE);
                }

                // Actualizar titulo del tolbar
                switch (position){
                    case 0: tvTitleTolbar.setText("Daily Work"); break;
                    case 1: tvTitleTolbar.setText("Gestión de usuario"); break;
                    case 2: tvTitleTolbar.setText("Gestión de area"); break;
                    case 3: tvTitleTolbar.setText("Historial"); break;
                    default: tvTitleTolbar.setText(R.string.app_name);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayoutMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMain.setCurrentItem(tab.getPosition());
                vpMain.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void ClickMenu(View view) {
        Drawer.openDrawer(drawerLayout);
    }

    @Override
    public void ClickBackMain(View view) {
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void ClickHome(View view) {
        //recreate();
        //replaceFragment(new fragmentHome());
        tabLayoutMain.selectTab(tabLayoutMain.getTabAt(0));
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void ClickUser(View view) {
        tabLayoutMain.selectTab(tabLayoutMain.getTabAt(1));
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void ClickArea(View view) {
        tabLayoutMain.selectTab(tabLayoutMain.getTabAt(2));
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void ClickProgress(View view) {
        tabLayoutMain.selectTab(tabLayoutMain.getTabAt(3));
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void ClickHistory(View view) {
        tabLayoutMain.selectTab(tabLayoutMain.getTabAt(4));
        Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void ClickLogout(View view) {
        Drawer.logout(this);
    }

    // Listener onclick, elementos del menu de navegación
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivMenu: ClickMenu(view); break;
            case R.id.ivMoreMain:
                Drawer.redirectActivity(this, ActivityMore.class);
                overridePendingTransition(R.anim.down_in, R.anim.down_out);
                break;
            case R.id.ivMainBack: ClickBackMain(view); break;
            case R.id.lyHome: ClickHome(view); break;
            case R.id.lyUser: ClickUser(view); break;
            case R.id.lyArea: ClickArea(view); break;
            case R.id.lyWork: ClickProgress(view); break;
            case R.id.lyHistory: ClickHistory(view); break;
            case R.id.lyLogout: ClickLogout(view); break;
        }
    }

    /**
     * Este método remplaza el contenido del fragment "fragmentContainer"
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

}