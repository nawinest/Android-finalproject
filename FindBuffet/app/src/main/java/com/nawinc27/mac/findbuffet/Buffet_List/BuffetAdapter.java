package com.nawinc27.mac.findbuffet.Buffet_List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nawinc27.mac.findbuffet.Model.Buffet;
import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;
import java.util.List;

public class BuffetAdapter extends BaseAdapter implements Filterable {

    private List<Buffet> buffets;
    private List<Buffet> filterList;
    private Context context;
    CustomFilter filter;

    public BuffetAdapter(Context c, ArrayList<Buffet> b){
        this.context = c;
        this.buffets = b;
        this.filterList = b;
    }


    @Override
    public int getCount() {
        return buffets.size();
    }

    @Override
    public Object getItem(int position) {
        return buffets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return buffets.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View buffetItem = LayoutInflater.from(context).inflate(R.layout.buffetlist_item, parent, false);
        TextView name_th = buffetItem.findViewById(R.id.name_th);
        TextView name_en = buffetItem.findViewById(R.id.name_en);

        name_th.setText(buffets.get(position).getName_th());
        name_en.setText(buffets.get(position).getName_en());
        return buffetItem;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Buffet> filters = new ArrayList<Buffet>();

                for (int i = 0 ;i < filterList.size() ; i ++){
                    if(filterList.get(i).getName_en().contains(constraint)){
                        Buffet b = new Buffet(filterList.get(i).getName_th(),
                                filterList.get(i).getName_en(),
                                filterList.get(i).getAddress(),
                                filterList.get(i).getTelephone(),
                                filterList.get(i).getTime(),
                                filterList.get(i).getLat(),
                                filterList.get(i).getLng(),
                                filterList.get(i).getImage_url());
                        filters.add(b);
                    }
                }
                results.count = filters.size();
                results.values = filters;

            }else{
                results.count = filterList.size();
                results.values = filterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            buffets = (List<Buffet>) results.values;
            notifyDataSetChanged();
        }
    }

}
