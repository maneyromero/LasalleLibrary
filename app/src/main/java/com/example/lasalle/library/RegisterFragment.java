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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final EditText email = (EditText) getView().findViewById(R.id.email);
        final EditText password = (EditText) getView().findViewById(R.id.password);
        final EditText repeatPassword = (EditText) getView().findViewById(R.id.repeat_password);
        Button registerButton = (Button) getView().findViewById(R.id.register_button);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean emailIsCorrect=false,PasswordIsCorrect=false,repeatPasswordIsCorrect=false;

                if(!emailREGEXVerification(email.getText().toString())){
                    email.setError(getString(R.string.wrong_email));
                }else{emailIsCorrect=true;}
                if(password.getText().toString().length()<=5){
                    password.setError(getString(R.string.greater_than_5));
                }else{PasswordIsCorrect=true;}
                if(!(repeatPassword.getText().toString().equals(password.getText().toString()))){
                    repeatPassword.setError(getString(R.string.password_not_match));
                }else{
                    repeatPasswordIsCorrect = true;
                }
                if(emailIsCorrect && PasswordIsCorrect && repeatPasswordIsCorrect){
                    HashMap accountData = new HashMap<String,String>();
                    accountData.put(email.getText().toString(),password.getText().toString());

                    if(!accountAlreadyRegistered(email.getText().toString())){
                        registerUser(accountData);
                        Toast.makeText(getActivity(), R.string.register_ok, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), R.string.user_already_exists, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    public boolean emailREGEXVerification(String email){
        //REGEX DE STACKOVERFLOW, no entiendo cómo funcionan las REGEX ->>>>>>>>>>>>>>>>>>>>>> INVESTIGAR en base a https://stackoverflow.com/questions/8204680/java-regex-email
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern  pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean accountAlreadyRegistered(String email){
        SharedPreferences pref= getActivity().getApplicationContext().getSharedPreferences("MyFilename", Context.MODE_PRIVATE);
        String mail = pref.getString(email,null);
        return mail != null;

    }

    public void registerUser(HashMap<String,String> accountData){
        SharedPreferences pref= getActivity().getApplicationContext().getSharedPreferences("MyFilename", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        for(Map.Entry<String, String> entry : accountData.entrySet()) {
            editor.putString(entry.getKey(),entry.getValue());
            editor.apply();
            pref.getString("a@a.com","DEFAULT");
        }



    }

}
