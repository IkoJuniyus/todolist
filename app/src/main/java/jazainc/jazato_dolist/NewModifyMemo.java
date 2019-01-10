package jazainc.jazato_dolist;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.DialogInterface;
import android.view.Menu;

import jazainc.jazato_dolist.db.DatabaseManager;

public class NewModifyMemo extends AppCompatActivity implements OnClickListener {
    
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private EditText titleText, subjectText;
    private Button updateBtn, deleteBtn;
    private EditText dateText, timeText;

    private long _id;

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.new_edit_memo);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        titleText = (EditText) findViewById(R.id.title_edit_memo);
        subjectText = (EditText) findViewById(R.id.subject_edit_memo);
        dateText = (EditText) findViewById(R.id.date_edit_memo);
        timeText = (EditText) findViewById(R.id.time_edit_memo);

        updateBtn = (Button) findViewById(R.id.edit_record_memo);
        deleteBtn = (Button) findViewById(R.id.delete_record_memo);
        
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String subject = intent.getStringExtra("subject");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        _id = Long.parseLong(id);
        String tanggal = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy",new java.util.Date()));
        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
        titleText.setText(title);
        subjectText.setText(subject);
        dateText.setText(tanggal);
        timeText.setText(currentDateTimeString);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_record_memo:
                String title = titleText.getText().toString();
                String subject = subjectText.getText().toString();
//                String date = dateText.getText().toString();
                String time = timeText.getText().toString();
                String tanggal = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy",new java.util.Date()));

                if(titleText.getText().toString().length()==0){
                    //jika form Email belum di isi / masih kosong
                    titleText.setError(" masukan data!");}
                else if (subjectText.getText().toString().length()==0){
                    subjectText.setError("masukan data!");
                }else if (dateText.getText().toString().length()==0){
                    dateText.setError("masukan tanggal!");
                }else if (timeText.getText().toString().length()==0){
                    timeText.setError("masukan waktu!");
                }else {

                databaseManager.update2(_id, title, subject, tanggal, time);
                Toast.makeText(NewModifyMemo.this, "Update Success", Toast.LENGTH_SHORT);
                this.returnHome();
                break;
                }

            case R.id.delete_record_memo:
                databaseManager.delete(_id);
                this.returnHome();
//                    new AlertDialog.Builder(this)
//                            .setMessage("Are you sure to delete this Memo?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    Intent intent = new Intent(NewModifyMemo.this, NewMainMemo.class);
//                                    startActivity(intent);
//                                }
//                            })
//                            .setNegativeButton("No", null)
//                            .show();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), NewMainMemo.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

    public void onDelete() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure to delete this Memo?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewModifyMemo.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
