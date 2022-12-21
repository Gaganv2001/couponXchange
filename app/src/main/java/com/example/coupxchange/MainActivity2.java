package com.example.coupxchange;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView ccc;
    Button cpy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ccc=findViewById(R.id.textView3);
        cpy=findViewById(R.id.button2);
        cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                }
                ClipData clip = ClipData.newPlainText("Copied Text", ccc.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });

    }
}