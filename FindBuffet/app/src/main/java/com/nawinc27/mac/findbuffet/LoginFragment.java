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

public class LoginFragment extends Fragment {

    private EditText email_input ;
    private EditText password_input;
    private TextView login_btn;
    private TextView register_btn;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        email_input = (EditText) getActivity().findViewById(R.id.email_input_register);
        password_input = (EditText) getActivity().findViewById(R.id.password_input_register);
        login_btn = (TextView)getActivity().findViewById(R.id.login_btn_login);
        register_btn = (TextView) getActivity().findViewById(R.id.register_btn_login);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container , false);
    }
}
