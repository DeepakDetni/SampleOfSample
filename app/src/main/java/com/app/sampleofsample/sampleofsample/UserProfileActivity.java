package com.app.sampleofsample.sampleofsample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.plus.Plus;

import java.io.InputStream;


public class UserProfileActivity extends ActionBarActivity {

    String email;
    String personName;
    String personPhotoUrl;
    String personGooglePlusProfile;
    MainActivity mainActivity;

    Button SignoutGromGoogle, RevokeAcessFromGoogle, confirmbutton;
    ImageView imgProfilePic;
    TextView username, useremail;
    LinearLayout llProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        SignoutGromGoogle = (Button) findViewById(R.id.btn_sign_out);
        RevokeAcessFromGoogle = (Button) findViewById(R.id.btn_revoke_access);
        confirmbutton = (Button) findViewById(R.id.confirmbutton);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        username = (TextView) findViewById(R.id.txtName);
        useremail = (TextView) findViewById(R.id.txtEmail);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        Intent i = getIntent();
        email = i.getStringExtra("email");
        personGooglePlusProfile = i.getStringExtra("personGooglePlusProfile");
        personPhotoUrl = i.getStringExtra("personPhotoUrl");
        personName = i.getStringExtra("personname");

          mainActivity = new MainActivity();
        new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
        username.setText(personName);
        useremail.setText(email);
        SignoutGromGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.signOutFromGplus();
            }
        });
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
