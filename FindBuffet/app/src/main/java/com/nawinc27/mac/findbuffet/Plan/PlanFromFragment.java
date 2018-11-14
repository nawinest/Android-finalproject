package com.nawinc27.mac.findbuffet.Plan;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;
import com.nawinc27.mac.findbuffet.Profile.ProfileFragment;
import com.nawinc27.mac.findbuffet.R;
import com.nawinc27.mac.findbuffet.RestuarantFragment;

import java.util.Calendar;

public class PlanFromFragment extends Fragment {
    private SQLiteDatabase db;
    private String name;
    private TextView name_plan;
    private TextView date;
    private EditText note;
    private FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
    private Calendar mCurrentDate;
    private int mYear, mMonth, mDay;
    private String dayStr, monthStr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_from, container ,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        initBackbtn();
        Bundle bd = getArguments();
        name_plan = ((TextView)getView().findViewById(R.id.name_list_plan));
        date = ((TextView)getView().findViewById(R.id.date_picker_form));
        note = ((EditText)getView().findViewById(R.id.note_form));

        //config calendar
        mCurrentDate = Calendar.getInstance();
        mYear = mCurrentDate.get(Calendar.YEAR);
        mMonth = mCurrentDate.get(Calendar.MONTH);
        mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        //display popup Calendar
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerPopup(date);
            }
        });

        if(bd != null){
            name = bd.getString("name_restuarant_th");
            Log.d("Plan Form : " , name);

        }
        initAdd_plan_btn();
    }

    public void initBackbtn(){
        Button back = getActivity().findViewById(R.id.back_btn_planform);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
    }

    public void initAdd_plan_btn(){
        Button bt = getActivity().findViewById(R.id.add_plan_btn);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues content = new ContentValues();
                content.put("name", name_plan.getText().toString());
                content.put("date" , date.getText().toString() );
                content.put("note" , note.getText().toString() );
                db.insert("user_plan_"+_user.getUid(),null,content);
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                Toast.makeText(getActivity(),"บันทึกแผนเรียบร้อยแล้วครับ",Toast.LENGTH_LONG);
            }
        });
    }

    private void datePickerPopup(final TextView field){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(month < 10){
                    monthStr = "0" + (month + 1);
                }else{
                    monthStr = (month + 1)+"";
                }
                if(dayOfMonth < 10){
                    dayStr  = "0" + dayOfMonth ;
                }else{
                    dayStr = dayOfMonth+"";
                }
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                field.setText(year+"-"+monthStr+"-"+dayStr);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
