package com.nawinc27.mac.findbuffet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nawinc27.mac.findbuffet.Main_menu.MainPageFragment;

public class LoginFragment extends Fragment {

    private 

    private EditText email_input ;
    private EditText password_input;
    private TextView login_btn;
    private TextView register_btn;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        email_input = (EditText) getActivity().findViewById(R.id.login_email_input_register);
        password_input = (EditText) getActivity().findViewById(R.id.login_password_input_register);

        login_btn = (TextView)getActivity().findViewById(R.id.login_btn_login);
        register_btn = (TextView) getActivity().findViewById(R.id.register_btn_login);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MainPageFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    public void login(){


        String emailSrr = email_input.getText().toString();
        String passwordStr = password_input.getText().toString();
        if(emailSrr.isEmpty() || passwordStr.isEmpty()){
            Toast.makeText(getActivity(),"Login failed", Toast.LENGTH_SHORT).show();
        }else{

        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container , false);
    }
}
