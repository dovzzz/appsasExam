package com.example.studin.ui.exportData;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.studin.database.AppActivity;
import com.example.studin.database.AppDatabase;
import com.example.studin.database.EventTable;
import com.example.studin.databinding.FragmentExportdataBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class exportDataFragment extends Fragment {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String FILE_NAME = "events.txt";

    private FragmentExportdataBinding binding;
    private AppDatabase db;

    private LinearLayout linearLayout;
    private ScrollView scrollView;
    Button buttonExport;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExportdataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        verifyStoragePermissions(getActivity());
        db = AppActivity.getDatabase();
        textView = binding.textView;
        textView.setText("Select which events you want to export to a .txt file!");

        scrollView = binding.scrollView2;
        linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.removeAllViews();
        List<EventTable> eventList = db.eventDAO().getAllTasks();
        ViewList(eventList);
        scrollView.addView(linearLayout);

        buttonExport = binding.buttonExport;
        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path + "/" + FILE_NAME);
                OutputStream fos = null;

                try {
                    file.createNewFile();
                    fos = new FileOutputStream(file);

                    for (int i = 0; i < linearLayout.getChildCount(); i++) {
                        View childView = linearLayout.getChildAt(i);
                        CheckBox cb = (CheckBox) childView;

                        if (cb.isChecked()) {
                            int childViewId = childView.getId();

                            String exportingEvent = db.eventDAO().getTask(childViewId).getStringAll() + "\n\n";
                            fos.write(exportingEvent.getBytes(StandardCharsets.UTF_8));

                            cb.setChecked(false);
                        }
                    }

                    // Navigation.findNavController(view).popBackStack();
                    Toast.makeText(getContext(), "Saved to " + file.getPath(), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void ViewList(List<EventTable> eventList) {
        for (EventTable event : eventList) {
            CheckBox cb = new CheckBox(getActivity());

            cb.setId(event.getId());
            cb.setPadding(20, 20, 20, 20);

            cb.setText(event.getStringMain());
            linearLayout.addView(cb);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // check if write permission is true
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // ask user for the permission
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
