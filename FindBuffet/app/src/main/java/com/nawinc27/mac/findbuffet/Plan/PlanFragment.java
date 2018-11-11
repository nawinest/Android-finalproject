package com.nawinc27.mac.findbuffet.Plan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;
import com.nawinc27.mac.findbuffet.Model.Plan;
import com.nawinc27.mac.findbuffet.Profile.ProfileFragment;
import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlanFragment extends Fragment {

    private List<Plan> plans = new ArrayList<>();
    private FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ListView planList = getActivity().findViewById(R.id.plan_list);
        final PlanAdapter planAdapter = new PlanAdapter(getActivity(), android.R.layout.list_content , plans);
        planList.setAdapter(planAdapter);
        planAdapter.clear();
        initBackBtn();

        //get database
        String uid = _user.getUid();
        Log.d("Plan Fragment : " , "user id : " + uid);
        final SQLiteDatabase db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        //query from database by table sleep_table [cursor]
        Cursor pointer_query = db.rawQuery("select * from "+uid, null);
        while (pointer_query.moveToNext()){
            int id = pointer_query.getInt(0);
            String name = pointer_query.getString(1);
            String date = pointer_query.getString(2);
            String time = pointer_query.getString(3);
            plans.add(new Plan(id, name, date , time));
        }

        planAdapter.sort(new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                return o2.get_date_plan().compareTo(o1.get_date_plan());
            }
        });
        Log.d("Plan Fragment : " , "sort");


        planAdapter.notifyDataSetChanged();

        Button clr_history = getView().findViewById(R.id.delete_plan_list);
        clr_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("delete from sleep_table");
                plans.clear();
                planAdapter.notifyDataSetChanged();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    public void initBackBtn(){
        Button back = getActivity().findViewById(R.id.back_btn_planlist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_view, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
