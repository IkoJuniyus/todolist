package jazainc.jazato_dolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import jazainc.jazato_dolist.db.DatabaseManager;
import jazainc.jazato_dolist.db.DatabaseHelper;

public class NewMainMemo extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private SearchView search;
    private DatabaseHelper helper;
    private DatabaseManager databaseManager;
    private ListView listView;
    private ListAdapter listAdapter;
    private SimpleCursorAdapter adapter;




    final String[] from = new String[]{DatabaseHelper._ID2,DatabaseHelper.TITLE,
            DatabaseHelper.SUBJECT2, DatabaseHelper.DATE2, DatabaseHelper.TIME2};

    final int[] to = new int[] { R.id.txtIdMemo, R.id.txtTitleMemo, R.id.txtSubjectMemo, R.id.txtDateMemo, R.id.txtTimeMemo };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_memo_list2);
        search = (SearchView) findViewById(R.id.searchList);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab_memo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Show klik",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NewMainMemo.this, NewAddMemo.class);
                startActivity(intent);
            }
        });


        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        Cursor cursor = databaseManager.fetch2();

        listView = (ListView) findViewById(R.id.list_view_memo);
        listView.setEmptyView(findViewById(R.id.empty_memo));

        adapter = new SimpleCursorAdapter(this, R.layout.task_view, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.txtIdMemo);
                TextView titleTextView = (TextView) view.findViewById(R.id.txtTitleMemo);
                TextView subjectTextView = (TextView) view.findViewById(R.id.txtSubjectMemo);
                TextView dateTextView = (TextView) view.findViewById(R.id.txtDateMemo);
                TextView timeTextView = (TextView) view.findViewById(R.id.txtTimeMemo);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String subject = subjectTextView.getText().toString();
                String date = dateTextView.getText().toString();
                String time = timeTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), NewModifyMemo.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("subject", subject);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("time", time);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });
        search.setOnQueryTextListener(this);
    }
//    public void backHome(View v)
//    {
//        Intent i = new Intent(this, NewMainMemo.class);
//        startActivity(i);
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText){
        SearchView sv = (SearchView) findViewById(R.id.searchList);
        String search = sv.getQuery().toString();
        Cursor cursor1 = databaseManager.fetchFilter1(search);

        listView = (ListView) findViewById(R.id.list_view_memo);
        listView.setEmptyView(findViewById(R.id.empty_memo));

        adapter = new SimpleCursorAdapter(this, R.layout.task_view, cursor1, from, to,0);
        listView.setAdapter(adapter);
        return false;
    }

}