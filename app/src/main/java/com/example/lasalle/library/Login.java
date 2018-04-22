package com.example.lasalle.library;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Amer on 11/03/2018.
 */

public class Login extends AppCompatActivity implements OnFragmentListener {
    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment fragment = new LoginFragment();
        fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.login,fragment).commit();

    }



    @Override
    public void changeTransaction() {
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentManager.beginTransaction().replace(R.id.login,registerFragment).addToBackStack(null).commit();

    }
}
