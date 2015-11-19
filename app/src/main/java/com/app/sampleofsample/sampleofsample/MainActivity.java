package com.app.sampleofsample.sampleofsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

//Created by Deepak D S
public class MainActivity extends AppCompatActivity {
RelativeLayout SignUpWithGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignUpWithGoogle = (RelativeLayout) findViewById(R.id.sign_in_with_google);
        SignUpWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        
        //First commit from Kumar Swamy
    }

    private void signInWithGoogle() {
    }
}
