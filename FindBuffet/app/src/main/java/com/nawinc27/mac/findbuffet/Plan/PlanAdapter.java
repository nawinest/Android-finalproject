package com.nawinc27.mac.findbuffet.Plan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nawinc27.mac.findbuffet.Model.Plan;
import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends ArrayAdapter<Plan> {

    private  Context con;
    private List<Plan> plans = new ArrayList<>();


    public PlanAdapter(@NonNull Context context, int resource, @NonNull List<Plan> objects) {
        super(context, resource, objects);
        this.con = context;
        this.plans = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View plan_item = LayoutInflater.from(con).inflate(R.layout.planlist_item, parent, false);
        TextView name = plan_item.findViewById(R.id.name_list_plan);
        TextView date = plan_item.findViewById(R.id.date_list_plan);
        TextView time = plan_item.findViewById(R.id.time_list_plan);


        name.setText(plans.get(position).get_name());
        date.setText(plans.get(position).get_date_plan());
        time.setText(plans.get(position).get_time_plan());
        return plan_item;
    }
}
