package com.nawinc27.mac.findbuffet.Buffet_List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;
import com.nawinc27.mac.findbuffet.Model.Buffet;
import com.nawinc27.mac.findbuffet.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BuffetList_fragment extends Fragment {

    private GridView buffetGrid;
    private List<Buffet> buffets = new ArrayList<Buffet>();
    private String header;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            header = bundle.getString("name_group");
            Log.d("Buffet List" , "header : "+ header);
            editHeader(header);
        }


        buffetGrid = getActivity().findViewById(R.id.grid_buffet_list);
        final BuffetGridAdapter grid_adapter = new BuffetGridAdapter(getActivity(), android.R.layout.list_content , buffets);
        buffetGrid.setAdapter(grid_adapter);
        grid_adapter.clear();

        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่นเปิด 24 ชั่วโมงมาแดกได้" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("นบุเฟต์ทาเนื้อย่าง สาขาราชดำเนิน" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านบุเฟต์ทะเลเผาเนื้อย่าง สาขาราชดำเนิน" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านหวังหมาฮั่น" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));
        buffets.add(new Buffet("ร้านบุเฟต์ทะเลเผาเนื้อย่าง สาขาราชดำเนิน" , "Ma Hun" , "ร้านอาหารอายุ 100 ปี  "
                , " 097 - 0699888" , "เปิดทุกวันจันทร์ - ศุกร์ | 09 : 00 - 24 : 00"
                , "13.818340","100.588078",1));



        Log.d("Buffet List" , buffets.get(0).getName_th());

        grid_adapter.notifyDataSetChanged();
        initBackBtn();
    }

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
