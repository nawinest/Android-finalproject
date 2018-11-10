package com.nawinc27.mac.findbuffet.Buffet_List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nawinc27.mac.findbuffet.Model.Buffet;
import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class BuffetGridAdapter extends ArrayAdapter<Buffet> {

    private List<Buffet>  buffets = new ArrayList<>();
    private Context context;


    public BuffetGridAdapter(@NonNull Context context, int resource, @NonNull List<Buffet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.buffets = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View buffetItem = LayoutInflater.from(context).inflate(R.layout.buffetlist_item, parent, false);
        TextView name_th = buffetItem.findViewById(R.id.name_th);
        TextView name_en = buffetItem.findViewById(R.id.name_en);

        name_th.setText(buffets.get(position).getName_th());
        Log.d("Buffet" , buffets.get(position).getName_th() + " from adapter");
        name_en.setText(buffets.get(position).getName_en());
        return buffetItem;
    }
}
