package jazainc.jazato_dolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import jazainc.jazato_dolist.db.DatabaseManager;
import jazainc.jazato_dolist.db.DatabaseHelper;

public class ListTask extends Activity implements SearchView.OnQueryTextListener {
    private DatabaseHelper helper;
    private DatabaseManager databaseManager;
    private ListView listView;
    private ListAdapter listAdapter;
    private SimpleCursorAdapter adapter;
    private SearchView search;

    final String[] from = new String[]{DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DATE, DatabaseHelper.TIME};

    final int[] to = new int[]{R.id.id, R.id.subject, R.id.date, R.id.time};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_task);

        search = (SearchView) findViewById(R.id.searchList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Show klik",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ListTask.this, TaskAdd.class);
                startActivity(intent);
            }
        });

//        CheckBox chk = (CheckBox) findViewById(R.id.cboxStatus);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        Cursor cursor = databaseManager.fetchFilterAktif();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.list_detail2, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {


                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView subjectTextView = (TextView) view.findViewById(R.id.subject);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);
                TextView timeTextView = (TextView) view.findViewById(R.id.time);

                String id = idTextView.getText().toString();
                String subject = subjectTextView.getText().toString();
                String date = dateTextView.getText().toString();
                String time = timeTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), TaskModify.class);
                modify_intent.putExtra("subject", subject);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("time", time);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });

        search.setOnQueryTextListener(this);

    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            Toast.makeText(ListTask.this,
                    "Checked", Toast.LENGTH_LONG).show();
//            TextView idTextView = (TextView) findViewById(R.id.id);
//            TextView subjectTextView = (TextView) findViewById(R.id.subject);
//            TextView dateTextView = (TextView) findViewById(R.id.date);
//            TextView timeTextView = (TextView) findViewById(R.id.time);
//
//            String id = idTextView.getText().toString();
//            String subject = subjectTextView.getText().toString();
//            String date = dateTextView.getText().toString();
//            String time = timeTextView.getText().toString();
//
//            String status = "Nonaktif";
//
//            Long _id = Long.parseLong(id);
//            databaseManager.update(_id, subject, date, time, status);

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText){
        SearchView sv = (SearchView) findViewById(R.id.searchList);
        String search = sv.getQuery().toString();
        Cursor cursor1 = databaseManager.fetchFilter(search);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.list_detail, cursor1, from, to,0);
        listView.setAdapter(adapter);
        return false;
    }

//    public void back(){
//        setContentView(R.layout.list_task);
//        Intent intent = new Intent(ListTask.this, MainActivity.class);
//        startActivity(intent);
//    }


}