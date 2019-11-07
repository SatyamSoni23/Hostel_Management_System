package com.example.hostelmanagementsystem;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class adminHome extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        final NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.allotRoom){
                    Toast.makeText(adminHome.this, "About E-Receipt", Toast.LENGTH_SHORT).show();
                    //startAboutEreceiptActivity();
                }
                else if(id == R.id.unallotRoom){
                    Toast.makeText(adminHome.this, "Update Password", Toast.LENGTH_SHORT).show();
                    //startUpdatePasswordActivity();
                }
                else if (id == R.id.mess){
                    Toast.makeText(adminHome.this, "Update Shop Details", Toast.LENGTH_SHORT).show();
                    //startUpdateShopDetailsActivity();
                }
                else if (id == R.id.gym){
                    Toast.makeText(adminHome.this, "Update Shop Logo", Toast.LENGTH_SHORT).show();
                    //startUpdateShopLogoActivity();
                }
                else if (id == R.id.facilities){
                    Toast.makeText(adminHome.this, "Update Owner Details", Toast.LENGTH_SHORT).show();
                    //startUpdateOwnerDetailsActivity();
                }
                else if(id == R.id.complains){
                    Toast.makeText(adminHome.this, "Contact Us", Toast.LENGTH_SHORT).show();
                    //startContactUsActivity();
                }
                else if(id == R.id.Logout){
                    Toast.makeText(adminHome.this, "Logout", Toast.LENGTH_SHORT).show();
                    //startLogoutActivity();
                }
                return false;
            }
        });
    }
}
