package com.example.studin.ui.existingEvent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.studin.R;
import com.example.studin.database.AppActivity;
import com.example.studin.database.AppDatabase;
import com.example.studin.database.EventTable;
import com.example.studin.databinding.FragmentExistingeventBinding;

public class ExistingEventFragment extends Fragment {

    private FragmentExistingeventBinding binding;
    private AppDatabase db;

    private TextView textView;
    Button buttonEdit;
    Button buttonDelete;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExistingEventViewModel existingEventViewModel =
                new ViewModelProvider(this).get(ExistingEventViewModel.class);

        binding = FragmentExistingeventBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        db = AppActivity.getDatabase();
        textView = binding.textViewEvent;

        final int id = getArguments().getInt("id");
        EventTable event = db.eventDAO().getTask(id);
        textView.setText("Course name: " + event.getCourseName() + "\nExam name: " +
                event.getExamName() + "\n\nDescription: " + event.getExamDesc() +
                "\n\nExam date and time: " + event.getExamDate() + " " + event.getExamTime() +
                "\nFirst retake date and time: " + event.getFirstRetakeDate() + " " +
                event.getFirstRetakeTime() + "\n\nUseful sources: " + event.getSources());

        buttonEdit = binding.buttonEdit;
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open edit fragment - add new fragment with pre-filed values
                final Bundle bundle = getArguments();
                Navigation.findNavController(view).navigate(R.id.nav_editEvent, bundle);
            }
        });

        buttonDelete = binding.buttonRemove;
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // remove event from database
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Deleting event");
                builder.setMessage("Are you sure you want to delete this event?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // delete event
                        db.eventDAO().deleteOne(id);
                        dialog.dismiss();

                        Navigation.findNavController(view).popBackStack();
                        Toast toast = Toast.makeText(getActivity(), "Event was successfully deleted!",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
