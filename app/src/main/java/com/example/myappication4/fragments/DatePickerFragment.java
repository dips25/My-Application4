package com.example.myappication4.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myappication4.PickDate;
import com.example.myappication4.R;

public class DatePickerFragment extends DialogFragment {

    Button setDate;
    DatePicker datePicker;
    PickDate pickDate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_datepicker , null);
        setDate = view.findViewById(R.id.set_date);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                returnDate();
                dialog.dismiss();
            }
        });

        return dialog;





    }

    public void returnDate() {

        pickDate.pickDate(String.format("%d/%d/%d" , datePicker.getDayOfMonth() , datePicker.getMonth()+1 , datePicker.getYear()));



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickDate =(PickDate) context;
    }
}
