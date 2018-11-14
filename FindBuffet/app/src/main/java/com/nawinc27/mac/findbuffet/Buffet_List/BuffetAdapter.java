package com.nawinc27.mac.findbuffet.Buffet_List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View buffetItem = LayoutInflater.from(context).inflate(R.layout.buffetlist_item, parent, false);
        TextView name_th = buffetItem.findViewById(R.id.name_th_plan);
        TextView name_en = buffetItem.findViewById(R.id.name_en);
        ImageView image_buffet = buffetItem.findViewById(R.id.image_buffet);

        Glide.with(context).load(buffets.get(position).getImage_url().get(0))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop().circleCrop())
                .into(image_buffet);
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
                Log.d("Filtering", "Constraint is : " + constraint);
                for (int i = 0 ;i < filterList.size() ; i ++){
                    if(filterList.get(i).getName_en().toUpperCase().contains(constraint)){
                        Log.d("Filtering", "performFiltering: " + filterList.get(i).getName_en());
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
