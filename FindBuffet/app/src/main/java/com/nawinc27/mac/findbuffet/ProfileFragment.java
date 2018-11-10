package com.nawinc27.mac.findbuffet;

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

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private FirebaseUser mUid;
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


        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser() != null){
            getProfile();
            initLogOut();
            initBackBtn();
        }
        else{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new LoginFragment())
                    .addToBackStack(null).commit();
        }

    }


    public void initBackBtn(){
        Button back = getActivity().findViewById(R.id.back_btn_profile);
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
//                                String mImage = snapshot.getString("image");
                                TextView _profileName = getView().findViewById(R.id.profile_name);
                                TextView _profilePhone = getView().findViewById(R.id.profile_phone_number);
                                TextView _profileEmail = getView().findViewById(R.id.profile_email);

                                _profileName.setText(mName);
                                _profilePhone.setText(mPhone);
                                _profileEmail.setText(mEmail);
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
