package com.example.studin.ui.addNewEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.studin.database.AppActivity;
import com.example.studin.database.AppDatabase;
import com.example.studin.database.EventTable;
import com.example.studin.databinding.FragmentAddneweventBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewEventFragment extends Fragment{

    private EditText courseName;
    private EditText examName;
    private EditText examDesc;
    private EditText examDate0;
    private EditText examDate1;
    private EditText examTime0;
    private EditText examTime1;
    private EditText sources;

    Button buttonAddEvent;
    Button buttonCancelEvent;

    DatePickerDialog pickerDate;
    TimePickerDialog pickerTime;

    private FragmentAddneweventBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddNewEventViewModel addNewEventViewModel =
                new ViewModelProvider(this).get(AddNewEventViewModel.class);

        binding = FragmentAddneweventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //addNewEventViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        db= AppActivity.getDatabase();

        courseName = binding.editTextCourseName;
        examName = binding.editTextTextExamName;
        examDesc = binding.editTextExamDesc;

        examDate0 = binding.editTextDate0;
        examDate0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show date picker dialog
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pickerDate = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                examDate0.setText(setDateFormat(dayOfMonth, monthOfYear, year));

                                String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                if (examDate0.getText().toString().compareTo(curDate) < 0) {
                                    examDate0.setTextColor(Color.RED);
                                    Toast toast = Toast.makeText(getActivity(), "Exam date " +
                                                    "must be bigger than the current date!",
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    examDate0.setTextColor(Color.BLACK);
                                }
                            }
                        }, year, month, day);
                pickerDate.show();
            }
        });

        examDate1 = binding.editTextDate1;
        examDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                pickerDate = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                examDate1.setText(setDateFormat(dayOfMonth, monthOfYear, year));

                                if (examDate0.getText().toString()
                                        .compareTo(examDate1.getText().toString()) > 0) {
                                    examDate1.setTextColor(Color.RED);
                                    Toast toast = Toast.makeText(getActivity(), "Retake date " +
                                                    "must be after exam date!",
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    examDate1.setTextColor(Color.BLACK);
                                }
                            }
                        }, year, month, day);
                pickerDate.show();
            }
        });

        examTime0 = binding.editTextTime0;
        examTime0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show time picker dialog
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerTime = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                examTime0.setText(setTimeFormat(sMinute, sHour));

                                String curDate = new SimpleDateFormat("YYYY-MM-DD").format(new Date());
                                String curTime = new SimpleDateFormat("HH:mm").format(new Date());
                                if (examDate0.getText().toString().compareTo(curDate) == 0 &&
                                        examTime0.getText().toString().compareTo(curTime) <= 0) {
                                    examTime0.setTextColor(Color.RED);
                                    Toast toast = Toast.makeText(getActivity(), "Exam time " +
                                                    "must be bigger than the current time!",
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    examTime0.setTextColor(Color.BLACK);
                                }
                            }
                        }, hour, minutes, true);
                pickerTime.show();
            }
        });

        examTime1 = binding.editTextTime1;
        examTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                pickerTime = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                examTime1.setText(setTimeFormat(sMinute, sHour));

                                if (examDate0.getText().toString()
                                        .compareTo(examDate1.getText().toString()) == 0 &&
                                        examTime0.getText().toString()
                                        .compareTo(examTime1.getText().toString()) >= 0) {
                                    examTime1.setTextColor(Color.RED);
                                    Toast toast = Toast.makeText(getActivity(), "Retake time " +
                                                    "must be after exam time!",
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    examTime1.setTextColor(Color.BLACK);
                                }
                            }
                        }, hour, minutes, true);
                pickerTime.show();
            }
        });

        sources = binding.editTextSources;

        buttonAddEvent = binding.buttonAdd;
        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on click opens data input view for new event
                if (TextUtils.isEmpty(courseName.getText().toString()) ||
                        TextUtils.isEmpty(examName.getText().toString()) ||
                        TextUtils.isEmpty(examDate0.getText().toString()) ||
                        TextUtils.isEmpty(examTime0.getText().toString())) {
                    Toast toast = Toast.makeText(getActivity(), "Please fill fields: " +
                                    "course name, exam name, exam date and time!",
                            Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    EventTable event = new EventTable(courseName.getText().toString(),
                            examName.getText().toString(), examDesc.getText().toString(),
                            examDate0.getText().toString(), examDate1.getText().toString(),
                            examTime0.getText().toString(), examTime1.getText().toString(),
                            sources.getText().toString());
                    db.eventDAO().insert(event);

                    Navigation.findNavController(view).popBackStack();
                    Toast toast = Toast.makeText(getActivity(), "Event was successfully added!",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        buttonCancelEvent = binding.buttonCancel;
        buttonCancelEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on click returns to Home fragment
                Navigation.findNavController(view).popBackStack();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String setDateFormat(int day, int month, int year) {
        String date;
        if (day < 10 && month + 1 < 10) {
            date = year + "-0" + (month + 1) + "-0" + day;
        } else if (day < 10 && month + 1 >= 10) {
            date = year + "-" + (month + 1) + "-0" + day;
        } else if (day >= 10 && month + 1 < 10) {
            date = year + "-0" + (month + 1) + "-" + day;
        } else {
            date = year + "-" + (month + 1) + "-" + day;
        }
        return date;
    }

    private String setTimeFormat(int min, int hr) {
        String time;
        if (min < 10 && hr < 10) {
            time = "0" + hr + ":0" + min;
        } else if (min < 10 && hr >= 10) {
            time = hr + ":0" + min;
        } else if (min >= 10 && hr < 10) {
            time = "0" + hr + ":" + min;
        } else {
            time = hr + ":" + min;
        }
        return time;
    }

}
