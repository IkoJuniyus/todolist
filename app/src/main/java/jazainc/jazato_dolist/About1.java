package jazainc.jazato_dolist;

import android.app.Activity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class About1 extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView; //Baru Ditambahkan
    private DrawerLayout mDrawerLayout; //Baru Ditambahkan

    protected void onCreate(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        if (toolbarText != null && mToolbar != null) {
            toolbarText.setText(getTitle());
            setSupportActionBar(mToolbar);
        }

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent home = new Intent(About1.this, MainActivity.class);
                        startActivity(home);
                        return true;
                    case R.id.nav_profile:
                        Intent memo = new Intent(About1.this, NewMainMemo.class);
                        startActivity(memo);
                        return true;
                    case R.id.nav_list:
                        Intent list = new Intent(About1.this, ListTask.class);
                        startActivity(list);
                        return true;
                    case R.id.nav_history:
//                        Toast.makeText(getApplicationContext(),"Anda Memilih About",Toast.LENGTH_SHORT).show();
//                        return true;
                        Intent history= new Intent(About1.this, ListTaskHistory.class);
                        startActivity(history);
                        return true;
                    case R.id.nav_about:
                        Toast.makeText(getApplicationContext(),"You are in About menu",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Wrong Operation", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
