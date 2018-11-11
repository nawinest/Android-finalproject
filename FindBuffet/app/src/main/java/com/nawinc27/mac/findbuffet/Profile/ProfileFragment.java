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

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FirebaseUser mUid;
    private TextView profileName ;
    private TextView profilePhone ;
    private TextView profileEmail ;
    private ImageView img_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView img_pro = (ImageView) getActivity().findViewById(R.id.img_profile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img_pro.setClipToOutline(true);
        }
        mUid = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        profileName = getActivity().findViewById(R.id.profile_name);
        profilePhone = getActivity().findViewById(R.id.profile_phone_number);
        profileEmail = getActivity().findViewById(R.id.profile_email);

        if(mAuth.getCurrentUser() != null){
            getProfile();
            initLogOut();
            initBackBtn();
            initSeePlanBtn();
            initEditProfileBtn();
        }
        else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
        }

    }

    public void initEditProfileBtn(){
        ImageView editBtn = getActivity().findViewById(R.id.edit_profile_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", mUid.getUid());
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
                                String mName = snapshot.getString("name");
                                String mPhone = snapshot.getString("phone");
                                Log.d("ProfileFragment....", mName);
                                profileName.setText(mName);
                                profilePhone.setText(mPhone);
                                profileEmail.setText(mEmail);
                            }
                    }
                });

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
