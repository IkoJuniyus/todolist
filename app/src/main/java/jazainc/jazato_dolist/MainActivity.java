package jazainc.jazato_dolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView; //Baru Ditambahkan
    private DrawerLayout mDrawerLayout; //Baru Ditambahkan
    private Button btnote, bttodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        btnote = (Button) findViewById(R.id.btNote);
        bttodo = (Button) findViewById(R.id.btTodo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        if(toolbarText!=null && mToolbar!=null) {
            toolbarText.setText(getTitle());
            setSupportActionBar(mToolbar);
        }

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"You are in Home menu",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_profile:
                        Intent memo= new Intent(MainActivity.this, NewMainMemo.class);
                        startActivity(memo);
                        return true;
                    case R.id.nav_list:
                        Intent list= new Intent(MainActivity.this, ListTask.class);
                        startActivity(list);
                        return true;
                    case R.id.nav_history:
//                        Toast.makeText(getApplicationContext(),"Anda Memilih About",Toast.LENGTH_SHORT).show();
//                        return true;
                        Intent history= new Intent(MainActivity.this, ListTaskHistory.class);
                        startActivity(history);
                        return true;
                    case R.id.nav_about:
//                        Toast.makeText(getApplicationContext(),"Anda Memilih About",Toast.LENGTH_SHORT).show();
//                        return true;
                        Intent about= new Intent(MainActivity.this, About1.class);
                        startActivity(about);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Ada Kesalahan ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        btnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMainMemo.class);
                startActivity(intent);
            }
        });
        bttodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListTask.class);
                startActivity(intent);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.openDrawer, R.string.closeDrawer){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}