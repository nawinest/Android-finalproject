package com.nawinc27.mac.findbuffet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RegisterFragment extends Fragment {
    private FirebaseAuth
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    private void registerInit(){
        EditText email, password, confPassword, name;
        email = getActivity().findViewById(R.id.email_input_register);
        password = getActivity().findViewById(R.id.password_input_register);
        confPassword = getActivity().findViewById(R.id.password2_input_register2);
        name = getActivity().findViewById(R.id.name_input_register);



    }
}
