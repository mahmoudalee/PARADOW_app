package com.alee.paradow.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alee.paradow.R;
import com.alee.paradow.frags.DiscoverFragment;
import com.alee.paradow.frags.FavoritesFragment;
import com.alee.paradow.frags.HomeFragment;
import com.alee.paradow.frags.ProfileFragment;
import com.alee.paradow.utils.CurvedBottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    CurvedBottomNavigationView bottomNavigationView;

    public static String token;
    SharedPreferences sharedPreferences;
    Long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        sharedPreferences=getSharedPreferences("myPrefs",MODE_PRIVATE);
        token=sharedPreferences.getString("token",null);

        userID=sharedPreferences.getLong("userID",-1);

//        callerIntent= this.getIntent();
//        user =  callerIntent.getParcelableExtra("user");

        bottomNavigationView.inflateMenu(R.menu.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new HomeFragment(userID) ).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment(userID);
                    break;
                case R.id.nav_discover:
                    selectedFragment = new DiscoverFragment();
                    break;
                case R.id.nav_favorites:
                    selectedFragment = new FavoritesFragment(userID);
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.blank:
                    //TODO(7):Implement the POPUP Activity
                    return true;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectedFragment ).commit();
            return true;
        }
    };

    public void CameraOpen(View view) {
        //TODO: Cam Click
        Toast.makeText(this, "Cam Clicked", Toast.LENGTH_SHORT).show();
    }

    public void TextWrite(View view) {
        Intent intent = new Intent(this , TextToDrawActivity.class);
        startActivity(intent);
    }

    public void Connect(View view) {
        Intent i = new Intent(this,DeviceConnectActivity.class);
        startActivity(i);
    }
}
