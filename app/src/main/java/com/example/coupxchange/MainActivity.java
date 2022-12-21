package com.example.coupxchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    Button logout;
    Button submit;
    EditText t1;
    TextView username;

    String tok;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout=findViewById(R.id.logoutbutton1);
        submit=findViewById(R.id.button);
        username=findViewById(R.id.textView2);
        t1=findViewById(R.id.editTextTextPersonName);



        Intent rintent = getIntent();
        String userEmail = rintent.getStringExtra("usrname");
        username.setText(userEmail);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                tok=t1.getText().toString();
                rootNode=FirebaseDatabase.getInstance();
                databaseReference=rootNode.getReference().child("Tokens");

                Token tokeninfo=new Token(userEmail,tok);

                databaseReference.push().setValue(tokeninfo);
                Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                 Intent intent= new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
    }

}