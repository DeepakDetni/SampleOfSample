package com.app.sampleofsample.sampleofsample;

import android.app.Activity;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.io.InputStream;


public class UserProfileActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String email;
    String personName;
    String personPhotoUrl;
    String personGooglePlusProfile;
    MainActivity mainActivity;
    public GoogleApiClient mGoogleApiClient;

    Button SignoutGromGoogle, RevokeAcessFromGoogle, confirmbutton;
    ImageView imgProfilePic;
    TextView username, useremail;
    LinearLayout llProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        mGoogleApiClient.connect();


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
                signOutFromGplus();
            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "User is new connected!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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

    public void signOutFromGplus() {
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();
        Log.d("logout", "success");
        Intent i = new Intent(UserProfileActivity.this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "Log out success!", Toast.LENGTH_LONG).show();

    }
}
