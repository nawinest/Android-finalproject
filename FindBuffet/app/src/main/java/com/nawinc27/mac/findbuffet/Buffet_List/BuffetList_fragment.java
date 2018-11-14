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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nawinc27.mac.findbuffet.LoginFragment;
import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;
import com.nawinc27.mac.findbuffet.Model.Buffet;
import com.nawinc27.mac.findbuffet.R;
import com.nawinc27.mac.findbuffet.RestuarantFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BuffetList_fragment extends Fragment {

    private GridView buffetGrid;
    private List<Buffet> buffets = new ArrayList<Buffet>();
    private String header;
    private String menu_eng_forQuery;
    private String query_path;
    private BuffetAdapter grid_adapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FirebaseUser mUid;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUid = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser() != null){
            Bundle bundle = getArguments();
            if(bundle != null){
                header = bundle.getString("name_group");
                menu_eng_forQuery = bundle.getString("name_group_en");
                query_path = menu_eng_forQuery.split(" ")[0].toLowerCase();
                Log.d("Buffet List" , "Path to query is : " + query_path);
                Log.d("Buffet List" , "header : "+ header);
                editHeader(header);
            }
            else{
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null).commit();
                Toast.makeText(getActivity(), "Sorry , It have some problem with Program" , Toast.LENGTH_LONG);
            }
            buffets.clear();
            buffets.add(new Buffet("ร้านลาวา","LAVA","41/51"
                    ,"097-6998888","เปิดทุกวัน 12:00 - 24:00","13.722269","100.76162199999999"
                    ,new ArrayList<String>(Arrays.asList("https://firebasestorage.googleapis.com/v0/b/findbuffet-a597a.appspot.com/o/Aumkum%2FAumKum-22.jpg?alt=media&token=33be9ef2-e306-4c21-9973-adc11f4235d6"
                    ,"https://firebasestorage.googleapis.com/v0/b/findbuffet-a597a.appspot.com/o/Aumkum%2Ffoody-upload-api-foody-mobile-960x550-jpg-171031172448.jpg?alt=media&token=e2d3df00-aece-4928-a1d5-ec286d0f13a8"))));
            Log.d("Services : " , "Finished");
            mDB.collection("Restuarant_Buffet").document(query_path).collection(query_path)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot query : queryDocumentSnapshots){
                        Log.d("....." , query.getString("name_th"));
                        Buffet bf = query.toObject(Buffet.class);
                        buffets.add(bf);
                    }
                    grid_adapter.notifyDataSetChanged();
                    initBackBtn();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Services : " , "ERROR : " + e.toString().toUpperCase());
                }
            });


            EditText filterText = getActivity().findViewById(R.id.search_bar);
            filterText.addTextChangedListener(filterTextWatcher);

            buffetGrid = getActivity().findViewById(R.id.grid_buffet_list);
            buffetGrid.setTextFilterEnabled(true);
            grid_adapter = new BuffetAdapter(getActivity() , (ArrayList<Buffet>) buffets);
            buffetGrid.setAdapter(grid_adapter);
            grid_adapter.notifyDataSetChanged();


            buffetGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Buffet bf = (Buffet) grid_adapter.getItem(position);
                    String name_en = bf.getName_en();
                    Log.d("Buffet List Fragment : " , name_en);
                    Bundle bd_to_restuarant = new Bundle();
                    bd_to_restuarant.putString("name_en", name_en);
                    bd_to_restuarant.putString("group_type", query_path);
                    RestuarantFragment restuarant_fragment = new RestuarantFragment();
                    restuarant_fragment.setArguments(bd_to_restuarant);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, restuarant_fragment)
                            .addToBackStack(null)
                            .commit();
                    Log.d("Buffet List Fragment : " , "GO TO RESTUARANT FRAGMENT");
                }
            });

        }
        else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
        }




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
