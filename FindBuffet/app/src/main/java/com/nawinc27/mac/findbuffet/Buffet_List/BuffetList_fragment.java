package com.nawinc27.mac.findbuffet.Buffet_List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;
import com.nawinc27.mac.findbuffet.Model.Buffet;
import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;
import java.util.List;

public class BuffetList_fragment extends Fragment {

    private GridView buffetGrid;
    private List<Buffet> buffets = new ArrayList<Buffet>();
    private String header;
    private BuffetAdapter grid_adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            header = bundle.getString("name_group");
            Log.d("Buffet List" , "header : "+ header);
            editHeader(header);
        }


        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "ZAZAZA" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านบุเฟต์ทะเลเผาเนื้อย่าง สาขาราชดำเนิน" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านบุเฟต์ทะเลเผาเนื้อย่าง สาขาราชดำเนิน" , "Zap Kua" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));


        EditText filterText = getActivity().findViewById(R.id.search_bar);
        filterText.addTextChangedListener(filterTextWatcher);

        buffetGrid = getActivity().findViewById(R.id.grid_buffet_list);
        buffetGrid.setTextFilterEnabled(true);
        grid_adapter = new BuffetAdapter(getActivity() , (ArrayList<Buffet>) buffets);
        buffetGrid.setAdapter(grid_adapter);


        grid_adapter.notifyDataSetChanged();
        initBackBtn();
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (grid_adapter != null) {
                grid_adapter.getFilter().filter(s);
            } else {
                Log.d("filter", "no filter availible");
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buffet_list, container, false);
    }

    public void initBackBtn(){
        Button back_btn = getActivity().findViewById(R.id.back_buffet_list);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MainPageFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void editHeader(String ed){
        TextView header_text = getActivity().findViewById(R.id.header_buffet_list);
        header_text.setText(ed);
        Log.d("Buffet List" , ed + "is changed");
    }
}
