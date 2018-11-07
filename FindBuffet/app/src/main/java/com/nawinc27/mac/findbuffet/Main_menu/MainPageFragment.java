package com.nawinc27.mac.findbuffet.Main_menu;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {
    private ArrayList<Menu> menus= new ArrayList<Menu>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView banner = (ImageView) getActivity().findViewById(R.id.banner1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            banner.setClipToOutline(true);
        }
        ScrollView sv = (ScrollView) getActivity().findViewById(R.id.scroll_menu);
        sv.scrollTo(0, 0);

        ListView menu_list = getActivity().findViewById(R.id.menu_listview);
        final MenuAdapter adapter = new MenuAdapter(getActivity(), android.R.layout.list_content , menus);
        menu_list.setAdapter(adapter);
        adapter.clear();


        menus.add((new Menu("บุฟเฟต์ซีฟู้่ด", "Seafood Buffet", "#")));
        menus.add((new Menu("บุฟเฟ่ต์ของหวาน", "Dessert Buffet", "#")));
        menus.add((new Menu("บุฟเฟ่ต์อาหารญี่ปุ่น", "Japanese Food Buffet", "#")));
        menus.add((new Menu("บุฟเฟ่ต์เนื้อ", "Beef Buffet", "#")));
        menus.add((new Menu("บุฟเฟ่ต์อาหารไทย", "Thai food Buffet", "#")));
        menus.add((new Menu("บุฟเฟ่ต์ทั่วไป", "Other Buffet", "#")));


        adapter.notifyDataSetChanged();




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_mainpage, container , false);

    }
}
