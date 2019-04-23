package com.ram.landoffreelancers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle args=getArguments();
        int i=args.getInt("index");
        if (i==0){
            Button button=(Button)getActivity().findViewById(R.id.from);
            String time=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            button.setText(time);

        }
        if (i==1){
            Button button=(Button)getActivity().findViewById(R.id.to);
            String time=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            button.setText(time);

        }
    }
}
