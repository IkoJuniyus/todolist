package jazainc.jazato_dolist;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import jazainc.jazato_dolist.db.DatabaseManager;

public class UpdateHistory extends Activity implements OnClickListener {
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button btn_get_datetime;
    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText dateText, timeText;
    private CheckBox chkBox;
    private long _id;
    private String status = "Aktif";

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.update_history);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        dateText = (EditText) findViewById(R.id.date_edittext);
        timeText = (EditText) findViewById(R.id.time_edittext);

        deleteBtn = (Button) findViewById(R.id.btn_delete);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateHistory.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(UpdateHistory.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String subject = intent.getStringExtra("subject");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        _id = Long.parseLong(id);

        titleText.setText(subject);
        dateText.setText(date);
        timeText.setText(time);

        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                databaseManager.delete(_id);
                this.returnHome();
//                new AlertDialog.Builder(this)
//                        .setMessage("Are you sure to delete this List?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                Intent intent = new Intent(UpdateHistory.this, ListTaskHistory.class);
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ListTaskHistory.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(sdf.format(myCalendar.getTime()));
    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            Toast.makeText(UpdateHistory.this, "Completed", Toast.LENGTH_LONG).show();
            status = "Nonaktif";
        } else {
            status = "Aktif";
        }
    }

}
