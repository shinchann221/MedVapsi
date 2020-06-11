package com.medvapsi.medvapsi;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class SellingFragment extends Fragment {
    private EditText drug,company,datem;
    private RadioGroup form, type;
    private RadioButton type1, form1;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private SQLiteDatabase db;

    private RadioButton t1,t2,t3,rg1,rg2,rg3;

    private String expdate;


    public SellingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_selling, container, false);

        drug = view.findViewById (R.id.drug);
        company = view.findViewById (R.id.company);
        datem = view.findViewById (R.id.date);
        type = view.findViewById (R.id.typegroup);
        form =  view.findViewById (R.id.formgroup);
        Button submit = view.findViewById (R.id.button_submit);
        t1 = view.findViewById (R.id.type1);
        t2 = view.findViewById (R.id.type2);
        t3= view.findViewById (R.id.type3);
        rg1 = view.findViewById (R.id.rg1);
        rg2 = view.findViewById (R.id.rg2);
        rg3 = view.findViewById (R.id.rg3);


        final int id = type.getCheckedRadioButtonId ();
        final int id2 = form.getCheckedRadioButtonId ();
        Calendar calendar = Calendar.getInstance ();
         final int year = calendar.get (Calendar.YEAR);
        final int month = calendar.get (Calendar.MONTH);
         final  int day = calendar.get (Calendar.DAY_OF_MONTH);
        String d = month + "/" + day + "/" + year;
        datem.setText (d);

        datem.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog (view.getContext (), onDateSetListener, year, month, day);

                Objects.requireNonNull (datePickerDialog.getWindow ()).setBackgroundDrawable (new ColorDrawable (Color.WHITE));
                datePickerDialog.show ();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                expdate = month + "/" + day + "/" + year;
                datem.setText (expdate);

            }
        };

        submit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String drugname = drug.getText ().toString ();
                String companyname= company.getText ().toString ();

                String typename = "";
                if(id==t1.getId ()){
                    typename = t1.getText ().toString ();
                }else if (id==t2.getId ()){
                    typename = t2.getText ().toString ();
                }else if (id == t3.getId ()){
                    typename = t3.getText ().toString ();
                }

                String formname = "";
                if(id2 == rg1.getId ()){
                    formname = rg1.getText ().toString ();
                }else if (id2 == rg2.getId ()){
                    formname = rg2.getText ().toString ();
                }else if ( id2 == rg3.getId ()){
                    formname = rg3.getText ().toString ();
                }

                nav_activity activity = (nav_activity) getActivity ();
                try {

                    if (activity != null) {
                        activity.dbupload (drugname, companyname, expdate, typename, formname);
                    }

                }catch ( NullPointerException e){
                    Log.w ("TAG", "onClick: ",e );
                }

                Toast.makeText (activity, "Thank You! We'll get back to you soon", Toast.LENGTH_SHORT).show ();
            }
        });
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach (context);
    }
}
