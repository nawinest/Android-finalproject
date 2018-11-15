package com.nawinc27.mac.findbuffet.Profile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.nawinc27.mac.findbuffet.Buffet_List.BuffetList_fragment;
import com.nawinc27.mac.findbuffet.LoginFragment;
import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;
import com.nawinc27.mac.findbuffet.Plan.PlanFragment;
import com.nawinc27.mac.findbuffet.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FirebaseUser mUid;
    private TextView profileName ;
    private TextView profilePhone ;
    private TextView profileEmail ;
    private ImageView profile_img;
    String mName;
    String mPhone ;
    String mProfile_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUid = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        profile_img = getActivity().findViewById(R.id.img_profile);
        profileName = getActivity().findViewById(R.id.profile_name);
        profilePhone = getActivity().findViewById(R.id.profile_phone_number);
        profileEmail = getActivity().findViewById(R.id.profile_email);


        if(mAuth.getCurrentUser() != null){
            getProfile();
        }
        else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
        }
        initLogOut();
        initSeePlanBtn();
        initBackBtn();

    }

    public void initEditProfileBtn(){
        ImageView editBtn = getActivity().findViewById(R.id.edit_profile_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", mUid.getUid());
                bundle.putString("downloadUrl" , mProfile_image);
                EditProfileFragment editProfile = new EditProfileFragment();
                editProfile.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, editProfile)
                        .addToBackStack(null)
                        .commit();
                Log.d("ProfileFragment", "send bundle to edit");

            }
        });
    }


    public void initBackBtn(){
        Button back = getActivity().findViewById(R.id.back_btn_profile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MainPageFragment())
                        .addToBackStack(null).commit();
            }
        });
    }

    public void initSeePlanBtn(){
        Button wplan_btn = getActivity().findViewById(R.id.seeplan_btn);
        wplan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PlanFragment())
                        .addToBackStack(null).commit();
            }
        });
    }


    private void getProfile(){
        final String mEmail = mAuth.getCurrentUser().getEmail();
        final String mUid = mAuth.getCurrentUser().getUid();



        mDB.collection("customer")
                .document(" Member " + mUid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                            if(snapshot.exists()){
                                mName = snapshot.getString("name");
                                mPhone = snapshot.getString("phone");
                                mProfile_image = snapshot.getString("imgProfileUrl");
                                Log.d("ProfileFragment....", mName);
                                profileName.setText(mName);
                                profilePhone.setText(mPhone);
                                profileEmail.setText(mEmail);
                                Picasso.with(getActivity()).load(mProfile_image).fit().centerCrop().placeholder(R.mipmap.ic_launcher).into(profile_img);

                            }
                    }
                });
        initEditProfileBtn();
    }

    private void initLogOut(){
        Button _btnOut = getView().findViewById(R.id.profile_signout_button);
        _btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


}
