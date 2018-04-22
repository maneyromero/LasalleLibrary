package com.example.lasalle.library;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Amer on 11/03/2018.
 */

public class LoginFragment extends Fragment implements Button.OnClickListener {
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button login = (Button) getView().findViewById(R.id.login);
        Button register = (Button) getView().findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AL hacer click en login, obtenemos la info de los campos email y password, para verificarlos y comprobar si están en el fichero.
                EditText emailField = (EditText) getView().findViewById(R.id.email);
                String email = emailField.getText().toString();
                EditText passwordField = (EditText) getView().findViewById(R.id.password);
                String password = passwordField.getText().toString();

                if(email!=null){
                    if(password!=null) {
                        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyFilename", Context.MODE_PRIVATE);
                        String password_in_file = pref.getString(email, null);
                        if (password != null && password_in_file != null) {
                            if (password.equals(password_in_file)) {
                                Intent books = new Intent(getActivity(), MainActivity.class);
                                startActivity(books);
                            } else {
                                Toast.makeText(getActivity(), R.string.password_wrong, Toast.LENGTH_SHORT).show();
                                Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
                                passwordField.startAnimation(shake);
                            }
                        } else {
                            Toast.makeText(getActivity(), R.string.user_not_registered, Toast.LENGTH_SHORT).show();
                            Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
                            emailField.startAnimation(shake);
                        }
                    }else{
                        passwordField.setError(getString(R.string.password_empty));
                        Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
                        passwordField.startAnimation(shake);
                    }
                }else{
                    emailField.setError(getString(R.string.email_empty));
                    Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
                    emailField.startAnimation(shake);
                }


            }
        });
        //si pulsan el Register, se llama al metodo changeTransaction para invocar al RegisterFragment y, éste, a sus views
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login activity = (Login) getActivity();
                activity.changeTransaction();
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}
