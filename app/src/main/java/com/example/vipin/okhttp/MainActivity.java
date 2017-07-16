package com.example.vipin.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    TextView txtString,fName,lName;
    ImageView avatar;

    public String url= "https://reqres.in/api/users/2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtString= (TextView)findViewById(R.id.txtString);
        fName= (TextView)findViewById(R.id.fname);
        lName = (TextView)findViewById(R.id.lname);
        avatar = (ImageView) findViewById(R.id.avatar);
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(url);
    }

    public class OkHttpHandler extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String...params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtString.setText(s);

            try {
                JSONObject rootObject = new JSONObject(s);
                JSONObject object = rootObject.getJSONObject("data");
                int id = object.optInt("id");
                String fname = object.optString("first_name");
                String lname = object.optString("last_name");
                String imageUrlString = object.optString("avatar");


               fName.setText(fname);
               lName.setText(lname);

                Glide.with(getApplicationContext()).load(imageUrlString).into(avatar);


                Log.d("TAG",id+" "+fname+" "+lname);
                Toast.makeText(MainActivity.this,""+id+" "+fname+" "+lname, Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}