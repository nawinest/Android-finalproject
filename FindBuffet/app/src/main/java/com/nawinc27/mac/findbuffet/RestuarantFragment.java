package com.nawinc27.mac.findbuffet;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.nawinc27.mac.findbuffet.Buffet_List.BuffetList_fragment;
import com.nawinc27.mac.findbuffet.Model.Buffet;
import com.nawinc27.mac.findbuffet.Plan.PlanFromFragment;
import com.nawinc27.mac.findbuffet.Profile.ProfileFragment;

public class RestuarantFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FirebaseUser mUid;
    private Buffet buffet_info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restuarant, container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUid = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();



        ImageView banner = (ImageView) getActivity().findViewById(R.id.image_restuarant);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            banner.setClipToOutline(true);
        }

        if(mAuth.getCurrentUser() != null){
            Bundle getForm_buffetList = getArguments();
            String name_en = getForm_buffetList.getString("name_en");
            String group_type = getForm_buffetList.getString("group_type");

            Log.d("RESTUARANT FRAGMENT : ", name_en + "   " +group_type);
            mDB.collection("Restuarant_Buffet").document(group_type)
                    .collection(group_type).document(name_en).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        Log.d("Restuarant Fragment : ", "Find Snapshot success");
                        buffet_info = documentSnapshot.toObject(Buffet.class);
                        TextView name_en = getActivity().findViewById(R.id.name_en_restuarant);
                        TextView address = getActivity().findViewById(R.id.location_restuarant);
                        TextView contact = getActivity().findViewById(R.id.tel_restuarant);
                        TextView time = getActivity().findViewById(R.id.time_restuarant);
                        TextView type = getActivity().findViewById(R.id.type_restuarant);
                        ImageView image_restuarant = getActivity().findViewById(R.id.image_restuarant);
                        Glide.with(getActivity()).load(buffet_info.getImage_url().get(1))
                                .apply(new RequestOptions()
                                        .placeholder(R.mipmap.ic_launcher)
                                        .centerCrop())
                                .into(image_restuarant);
                        type.setText(buffet_info.getName_th());
                        name_en.setText(buffet_info.getName_en());
                        address.setText(buffet_info.getAddress());
                        contact.setText(buffet_info.getTelephone());
                        time.setText(buffet_info.getTime());
                        Toast.makeText(getActivity(), "ขณะนี้กำลังอยู่ในร้าน : "+buffet_info.getName_th(),Toast.LENGTH_LONG);
                        initBackBtn();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Sorry , if have some problem with firebase" , Toast.LENGTH_LONG);
                    Log.d("Restuarant Fragment", "Something went wrong" + mAuth.getCurrentUser().getUid().toString());
                }
            });
            initAddPlanBtn();

        }else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
            Toast.makeText(getActivity(), "Sorry , you must logint first" , Toast.LENGTH_LONG);
        }
    }

    public void initBackBtn(){
        Button backBtn = getActivity().findViewById(R.id.back_btn_restuarant);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
    }

    public void initAddPlanBtn(){
        Button addPlan_btn = getActivity().findViewById(R.id.addplan_btn_rest);
        addPlan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putString("name_restuarant_th",buffet_info.getName_th());
                PlanFromFragment planFromFragment = new PlanFromFragment();
                planFromFragment.setArguments(bd);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, planFromFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


}
