package com.nawinc27.mac.findbuffet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nawinc27.mac.findbuffet.Model.Customer;

public class RegisterFragment extends Fragment {

    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        Button regis = getActivity().findViewById(R.id.register_btn_register);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("register", "regis click");
                registerInit();
            }
        });

        logoutInit();
    }


    public void logoutInit(){

        Button back = getActivity().findViewById(R.id.back_btn_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .addToBackStack(null).commit();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    private void registerInit(){


        EditText password = getActivity().findViewById(R.id.password_input_register);
        EditText confPassword = getActivity().findViewById(R.id.password2_input_register2);
        EditText name = getActivity().findViewById(R.id.name_input_register);
        EditText email = getActivity().findViewById(R.id.email_input_register);
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        String _confPassword = confPassword.getText().toString();
        String _name = name.getText().toString();

        if(_email.isEmpty() || _password.isEmpty() || _confPassword.isEmpty() || _name.isEmpty()){
            Toast.makeText(getActivity(), "plase fill all data", Toast.LENGTH_SHORT).show();
        }else if(!_password.equals(_confPassword)){
            Toast.makeText(getActivity(), "password" , Toast.LENGTH_SHORT).show();
        }else  if(_password.length() < 6){
            Toast.makeText(getActivity(), "password must be more than 6 character", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(_email, _password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error =  "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    private void sendVerifiedEmail(final FirebaseUser user){
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                EditText name = getActivity().findViewById(R.id.name_input_register);
                String _name = name.getText().toString();
                EditText email = getActivity().findViewById(R.id.email_input_register);
                String _email = email.getText().toString();
                Customer cus = new Customer(_name, "กรุณาตั้งค่าเบอร์โทรของท่าน" , "https://d2x5ku95bkycr3.cloudfront.net/App_Themes/Common/images/profile/0_200.png");
                mStore.collection("customer")
                        .document((" Member " + user.getUid()))
                        .set(cus).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAuth.signOut();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new LoginFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error =  "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getActivity(), "Register complete", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error =  "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
