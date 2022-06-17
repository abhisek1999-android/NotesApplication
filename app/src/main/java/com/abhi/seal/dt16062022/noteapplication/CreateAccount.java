package com.abhi.seal.dt16062022.noteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abhi.seal.dt16062022.noteapplication.SP.PrefConfig;
import com.abhi.seal.dt16062022.noteapplication.db.auth.Auth;
import com.abhi.seal.dt16062022.noteapplication.db.auth.AuthDataBase;
import com.abhi.seal.dt16062022.noteapplication.db.user.User;
import com.abhi.seal.dt16062022.noteapplication.db.user.UserDataBase;
import com.abhi.seal.dt16062022.noteapplication.utility.EncryptPassword;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {
    private static final int Read_Permission=101;
    EditText email,password,name,number;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+";
    String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      email=findViewById(R.id.email);
      password=findViewById(R.id.password);
      name=findViewById(R.id.password);
      number=findViewById(R.id.phoneNumber);
        if (ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(CreateAccount.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.MANAGE_DOCUMENTS},Read_Permission);
        }

        if ( ContextCompat.checkSelfPermission(CreateAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(CreateAccount.this, new String[]{Manifest.permission.MANAGE_DOCUMENTS},Read_Permission);
        }
        if (!TextUtils.isEmpty(PrefConfig.readIdInPref(getApplicationContext(),"id"))){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }



    }

  public boolean isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^([a-z])"+"(?=(.*\\d){2})"+"(?=.*[a-z])"+"(?=(.*[A-Z]){2})"+ "(?=.*[@#$%^&+=])" + "(?=\\S+$).{7,15}$";
        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public void addAccount(View view) throws Exception {


        if (!email.getText().toString().matches(emailPattern)){
            email.setError("Email Pattern Mismatch");
            return;
        }
        if (!isValidPassword(password.getText().toString())){
            password.setError("Password must start with lower case character .Must contain 2 uppercase, one special character, 2 digits.");
            return;
        }

        if (!password.getText().toString().contains(name.getText().toString())){
            password.setError("Password must not contain your name!");
            return;
        }


        String pass=new EncryptPassword().encrypt(password.getText().toString()) ;

        UserDataBase db=UserDataBase.getDbInstance(this.getApplicationContext());
        AuthDataBase auth=AuthDataBase.getDbInstance(this.getApplicationContext());

        User user=new User();
        user.name=name.getText().toString();
        user.email=email.getText().toString();
        user.phone=number.getText().toString();
        user.password= pass ;

        Auth auth_=new Auth();

        auth_.email=email.getText().toString();
        auth_.pass= pass ;

        try{

            db.userDao().insertUser(user);
            auth.authDao().insertUser(auth_);
            startActivity( new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void main(View view) {

        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}