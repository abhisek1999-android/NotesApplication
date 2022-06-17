package com.abhi.seal.dt16062022.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abhi.seal.dt16062022.noteapplication.SP.PrefConfig;
import com.abhi.seal.dt16062022.noteapplication.db.auth.Auth;
import com.abhi.seal.dt16062022.noteapplication.db.auth.AuthDataBase;
import com.abhi.seal.dt16062022.noteapplication.utility.EncryptPassword;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    EditText email,password;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email= findViewById(R.id.email);
        password=findViewById(R.id.password);
    }

    public void login(View view) throws Exception {


        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){

        AuthDataBase db=AuthDataBase.getDbInstance(this.getApplicationContext());
        List<Auth> userList=db.authDao().getUsers();

        for(int i=0;i<userList.size();i++){

          if ( userList.get(i).email.equals(email.getText().toString()) &&
                new EncryptPassword().decrypt(userList.get(i).pass.toString()).equals(password.getText().toString())){
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
               PrefConfig.writeIdInPref(getApplicationContext(),email.getText().toString(),"id");
              finish();
               flag=true;
               break;
          }
        }

        if (!flag)
            Toast.makeText(this, "Id password mismatch", Toast.LENGTH_SHORT).show();



        }
    }


}