package com.example.coupxchange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private ImageView gi;
    private EditText signupemail,signuppassword,confirmsignuppass;
    private Button signupbutton;
    private TextView loginredirect;
    private GoogleSignInClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        signupemail = findViewById(R.id.signup_email);
        signuppassword = findViewById(R.id.signup_password);
        confirmsignuppass = findViewById(R.id.signup_confirmpassword);
        signupbutton = findViewById(R.id.signup_button);
        loginredirect = findViewById(R.id.loginRedirectText);
        gi=findViewById(R.id.goauth);
        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client= GoogleSignIn.getClient(this,options);

        gi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupemail.getText().toString().trim();
                String pass = signuppassword.getText().toString().trim();
                String cpass= confirmsignuppass.getText().toString().trim();

                if (!validpwd(pass))
                {
                    signuppassword.setError("Password must contain minimum 8 character,atleast one special charater and alphanumeric");
                }

                if (user.isEmpty()){
                    signupemail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()){

                }
                if(!(pass.equals(cpass))){
                    confirmsignuppass.setError("Password must be same");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
                    signupemail.setError("Enter a valid email");
                }


                else{
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(signup.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signup.this, login.class));
                            } else {
                                Toast.makeText(signup.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this,login.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account=task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                                    String usrname=auth.getCurrentUser().getEmail();
                                    intent.putExtra("usrname",usrname);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(signup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            catch(ApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String usrname=auth.getCurrentUser().getEmail();
            Intent intent=new Intent(this,HomeActivity.class);
            intent.putExtra("usrname",usrname);

            startActivity(intent);
        }
    }


    Pattern lowercase=Pattern.compile("^.*[a-z].*$");
    Pattern uppercase=Pattern.compile("^.*[A-Z].*$");
    Pattern number=Pattern.compile("^.*[0-9].*$");
    Pattern splcharacter=Pattern.compile("^.*[^a-zA-Z0-9].*$");

    private boolean validpwd(String pass)
    {
        if(pass.length()<8) return false;
        if(!lowercase.matcher(pass).matches()) return false;
        if(!uppercase.matcher(pass).matches()) return false;
        if(!number.matcher(pass).matches()) return false;
        if(!splcharacter.matcher(pass).matches()) return false;
        return true;
    }

}