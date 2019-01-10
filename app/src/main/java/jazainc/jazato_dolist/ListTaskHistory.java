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

import jazainc.jazato_dolist.db.DatabaseManager;
import jazainc.jazato_dolist.db.DatabaseHelper;

public class ListTaskHistory extends Activity implements SearchView.OnQueryTextListener {
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

        setContentView(R.layout.history_list);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        Cursor cursor = databaseManager.fetchFilterNonaktif();
        search = (SearchView) findViewById(R.id.searchList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.list_detail, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

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

                Intent modify_intent = new Intent(getApplicationContext(), UpdateHistory.class);
                modify_intent.putExtra("subject", subject);
                modify_intent.putExtra("date", date);
                modify_intent.putExtra("time", time);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });

        search.setOnQueryTextListener(this);

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


}