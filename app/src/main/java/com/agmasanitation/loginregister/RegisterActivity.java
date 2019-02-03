package com.agmasanitation.loginregister;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText etName=(EditText)findViewById(R.id.etName);
        final EditText etUsername=(EditText)findViewById(R.id.etUsername);
        final EditText etPassword=(EditText)findViewById(R.id.etPassword);
        final EditText etAge=(EditText)findViewById(R.id.etAge);
        final Button bRegister=(Button)findViewById(R.id.buttonRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("RegisterActivity","onClick");
                final String name=etName.getText().toString();
                final String username=etUsername.getText().toString();
                final String password=etPassword.getText().toString();
                final int age=Integer.parseInt(etAge.getText().toString());
                Response.Listener<String> listener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.e("RegisterActivity","onResponse");
                            JSONObject jsonResponse=new JSONObject(response);
                            Log.e("RegisterActivity","onResponse"+jsonResponse);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                Log.e("RegisterActivity","Success");
                                Intent loginIntent=new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                            }else{
                                Log.e("RegisterActivity","Failed");
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Failed!")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            Log.e("RegisterActivity","exception",e);
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest=new RegisterRequest(name,username,age,password,listener);
                RequestQueue queue=Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
