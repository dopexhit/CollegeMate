package com.example.collegemate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

public class DialogMakeAssignmentReminder extends DialogFragment {

    Spinner subject;
    TextView dateshow,timeshow;
    EditText detials;
    ImageButton submit,time,date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.dialog_add_assignment,container,false);

        //Setting up the toolbar
        Toolbar toolbar = rootview.findViewById(R.id.dialog_add_assignment_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Refrencing
        subject = rootview.findViewById(R.id.dialog_add_assignment_subject);
        detials = rootview.findViewById(R.id.dialog_add_assignment_details);
        dateshow = rootview.findViewById(R.id.dialog_add_assignment_date_show);
        timeshow = rootview.findViewById(R.id.dialog_add_assignment_time_show);
        subject = rootview.findViewById(R.id.dialog_add_assignment_subject);



        return rootview;
    }
}
