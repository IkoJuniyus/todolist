package jazainc.jazato_dolist;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jazainc.jazato_dolist.db.DatabaseManager;

public class NewAddMemo extends Activity implements OnClickListener {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private Button addTodoBtn;
    private EditText titleEtMemo, subjectEtMemo, dateEtMemo, timeEtMemo;

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.new_add_memo);

        String tanggal = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy",new java.util.Date()));
        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
        titleEtMemo = (EditText) findViewById(R.id.title_add_memo);
        subjectEtMemo = (EditText) findViewById(R.id.subject_add_memo);
        dateEtMemo = (EditText) findViewById(R.id.date_add_memo);
        timeEtMemo = (EditText) findViewById(R.id.time_add_memo);
        dateEtMemo.setText(tanggal);
        timeEtMemo.setText(currentDateTimeString);
        addTodoBtn = (Button) findViewById(R.id.add_record_memo);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record_memo:

                final String title = titleEtMemo.getText().toString();
                final String subject = subjectEtMemo.getText().toString();
                final String date = dateEtMemo.getText().toString();
                final String time = timeEtMemo.getText().toString();

                if(titleEtMemo.getText().toString().length()==0){
                    //jika form Email belum di isi / masih kosong
                    titleEtMemo.setError(" masukan data!");}
                    else if (subjectEtMemo.getText().toString().length()==0){
                    subjectEtMemo.setError("masukan data!");
                    }else if (dateEtMemo.getText().toString().length()==0){
                    dateEtMemo.setError("masukan tanggal!");
                    }else if (timeEtMemo.getText().toString().length()==0){
                    timeEtMemo.setError("masukan waktu!");
                    }else {
                    databaseManager.insert2(title, subject, date, time);

                    Intent main = new Intent(NewAddMemo.this, NewMainMemo.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);
                    break;}


        }
    }

}