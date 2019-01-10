package jazainc.jazato_dolist;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import jazainc.jazato_dolist.db.DatabaseManager;

public class TaskAdd extends AppCompatActivity implements OnClickListener {

    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialog;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.list_add);

        subjectEditText = (EditText) findViewById(R.id.subject_edittext);
        dateEditText = (EditText) findViewById(R.id.date_edittext);
        timeEditText = (EditText) findViewById(R.id.time_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_record);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        addTodoBtn.setOnClickListener(this);

        myCalendar = Calendar.getInstance();
//        date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.DATE, 0);
//                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                datePickerDialog.show();
//            }
//        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(TaskAdd.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        dateEditText.setText(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 0);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TaskAdd.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = String.format("%02d:%02d", selectedHour,selectedMinute);
                        timeEditText.setText(jam);
                        //timeEditText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                final String name = subjectEditText.getText().toString();
                final String date = dateEditText.getText().toString();
                final String time = timeEditText.getText().toString();

                if(subjectEditText.getText().toString().length()==0){
                    //jika form Email belum di isi / masih kosong
                    subjectEditText.setError(" masukan data!");}
                else if (dateEditText.getText().toString().length()==0){
                    dateEditText.setError("masukan tanggal!");
                }else if (timeEditText.getText().toString().length()==0){
                    timeEditText.setError("masukan waktu!");
                }else {
                    String status = "Aktif";
                    databaseManager.insert(name, date, time, status);

                    Intent main = new Intent(TaskAdd.this, ListTask.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(main);
                    break;
                }


        }
    }

}