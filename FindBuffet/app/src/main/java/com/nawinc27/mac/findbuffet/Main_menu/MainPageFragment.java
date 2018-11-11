package com.nawinc27.mac.findbuffet.Main_menu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nawinc27.mac.findbuffet.Buffet_List.BuffetList_fragment;
import com.nawinc27.mac.findbuffet.Profile.ProfileFragment;
import com.nawinc27.mac.findbuffet.R;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {
    private ArrayList<Menu> menus= new ArrayList<Menu>();
    private String uid;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView banner = (ImageView) getActivity().findViewById(R.id.banner1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            banner.setClipToOutline(true);
        }
        ScrollView sv = (ScrollView) getActivity().findViewById(R.id.scroll_menu);
        sv.scrollTo(0, 0);

        FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();

        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE,null);
        if(_user == null){
            Log.d("MainActivities Log : ", "didn't logged in");
        }else{
            uid = _user.getUid();
            Log.d("MainActivities Log : ", "USER logged in");
            //open database or create for use app at first time
            myDB.execSQL("CREATE TABLE IF NOT EXISTS "+uid+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name VARCHAR(255), " +
                    "date VARCHAR(255)," +
                    "time VARCHAR(255))");
            Log.d("MainActivities Log : ",uid);
        }



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
        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name_foodGroup = menus.get(position).getName();
                Bundle bd = new Bundle();
                bd.putString("name_group", name_foodGroup);
                BuffetList_fragment bf = new BuffetList_fragment();
                bf.setArguments(bd);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, bf)
                        .addToBackStack(null)
                        .commit();
                Log.d("Mainpage to Buffet_List", "success sent");

            }
        });

        initProfile();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_mainpage, container , false);

    }

    public void initProfile(){
        ImageView _profile = getView().findViewById(R.id.imageView2);
        _profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
