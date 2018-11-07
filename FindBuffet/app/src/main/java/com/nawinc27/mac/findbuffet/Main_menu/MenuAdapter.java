package com.nawinc27.mac.findbuffet.Main_menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ArrayAdapter<Menu>{

    private Context con;
    List<Menu> menus = new ArrayList<Menu>();


    public MenuAdapter(@NonNull Context context, int resource, @NonNull List<Menu> objects) {
        super(context, resource, objects);
        this.con = context;
        this.menus = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View menuItem = LayoutInflater.from(con).inflate(R.layout.menulist_item, parent, false);
        TextView name = (TextView)menuItem.findViewById(R.id.name_th_menu);
        name.setText(menus.get(position).getName());
        TextView nameEng = (TextView) menuItem.findViewById(R.id.name_en_menu);
        nameEng.setText(menus.get(position).getNameEng());

        return menuItem;
    }
}
