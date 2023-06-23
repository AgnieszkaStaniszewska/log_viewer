package com.example.temat13;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button loadLogsButton;
    private TextView logTextView;
    private EditText durationEditText;
    private TextView startDateTextView;
    private ImageView calendarIcon;
    private TextView startTimeTextView;
    private ImageView clockIcon;
    private Calendar selectedDate;
    private int selectedHour;
    private int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_viewer);

        loadLogsButton = findViewById(R.id.loadLogsButton);
        logTextView = findViewById(R.id.logTextView);
        durationEditText = findViewById(R.id.durationEditText);
        startDateTextView = findViewById(R.id.startDateTextView);
        calendarIcon = findViewById(R.id.calendarIcon);
        startTimeTextView = findViewById(R.id.startTimeTextView);
        clockIcon = findViewById(R.id.clockIcon);
        selectedDate = Calendar.getInstance();
        selectedHour = selectedDate.get(Calendar.HOUR_OF_DAY);
        selectedMinute = selectedDate.get(Calendar.MINUTE);

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        clockIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        loadLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationText = durationEditText.getText().toString();
                String startDateText = startDateTextView.getText().toString();
                String startTimeText = startTimeTextView.getText().toString();

                if (TextUtils.isEmpty(durationText) || TextUtils.isEmpty(startDateText) || TextUtils.isEmpty(startTimeText)) {
                    Toast.makeText(MainActivity.this, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                    return;
                }

                int duration = Integer.parseInt(durationText);

                // Pobranie wybranej daty początkowej
                Calendar startDate = selectedDate;
                startDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                startDate.set(Calendar.MINUTE, selectedMinute);

                // Utworzenie daty końcowej na podstawie wybranej daty początkowej i czasu trwania
                Calendar endDate = Calendar.getInstance();
                endDate.setTime(startDate.getTime());
                endDate.add(Calendar.MINUTE, duration);

                // Przykładowy kod: Wyświetlenie logów w polu tekstowym logTextView
                StringBuilder logsBuilder = new StringBuilder();
                logsBuilder.append("Logi dla wybranego okresu:\n");
                logsBuilder.append("Data początkowa: ").append(startDate.getTime()).append("\n");
                logsBuilder.append("Data końcowa: ").append(endDate.getTime()).append("\n");
                logsBuilder.append("Log 1\n");
                logsBuilder.append("Log 2\n");
                logsBuilder.append("Log 3\n");

                logTextView.setText(logsBuilder.toString());
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        startDateTextView.setText(
                                String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year)
                        );
                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;

                        startTimeTextView.setText(
                                String.format("%02d:%02d", hourOfDay, minute)
                        );
                    }
                },
                selectedHour,
                selectedMinute,
                true
        );
        timePickerDialog.show();
    }
}
