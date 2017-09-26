package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

public class MainActivity extends AppCompatActivity {
    String fname;
    String lname;
    String answer;
    String pin;
    String state,city ;
    String password, mobile, address1 , address2 , email;
    String country;
    String id;
    String qid;
    String question;
    ArrayList<String> first = new ArrayList<String>();
    ArrayList<String> second = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank);
        ConnectToServer2();
        submit2();

    }
   public void initView()
    {
      fname= ((EditText)findViewById(R.id.fname)).getText().toString();
        lname= ((EditText)findViewById(R.id.lname)).getText().toString();
        email= ((EditText)findViewById(R.id.email)).getText().toString();
        pin= ((EditText)findViewById(R.id.pin)).getText().toString();
        city= ((EditText)findViewById(R.id.city)).getText().toString();
        state= ((EditText)findViewById(R.id.state)).getText().toString();
        address1= ((EditText)findViewById(R.id.add1)).getText().toString();
        address2= ((EditText)findViewById(R.id.add2)).getText().toString();
        password= ((EditText)findViewById(R.id.password)).getText().toString();
        mobile= ((EditText)findViewById(R.id.mobile)).getText().toString();
        answer=((EditText)findViewById(R.id.answer)).getText().toString();


    }
    public void submit(View v)
    {
        initView();

        if(!validate())
            return;

        send();
        send2();
        ConnectToServer();

    }
    public boolean validate()
    {
try{
        if(fname.equals(""))
            Toast.makeText(this, "First Name field empty", Toast.LENGTH_SHORT).show();
        else if(lname.equals(""))
            Toast.makeText(this, "Last Name field empty", Toast.LENGTH_SHORT).show();
        else if(email.equals(""))
            Toast.makeText(this, "Email id field empty", Toast.LENGTH_SHORT).show();
        else if(password.equals(""))
            Toast.makeText(this, "Password field empty", Toast.LENGTH_SHORT).show();
        else if(mobile.equals(""))
            Toast.makeText(this, "Mobile field empty", Toast.LENGTH_SHORT).show();
        else if(address1.equals(""))
            Toast.makeText(this, "Address 1 field empty", Toast.LENGTH_SHORT).show();
        else if(address2.equals(""))
            Toast.makeText(this, "Address 2 field empty", Toast.LENGTH_SHORT).show();
        else if(city.equals(""))
            Toast.makeText(this, "City field empty", Toast.LENGTH_SHORT).show();
        else if(state.equals(""))
            Toast.makeText(this, "State field empty", Toast.LENGTH_SHORT).show();
        else if(pin.equals(""))
            Toast.makeText(this, "Pin field empty", Toast.LENGTH_SHORT).show();
        else if(answer.equals(""))
            Toast.makeText(this, "Answer field empty", Toast.LENGTH_SHORT).show();
        else if(!email.endsWith("com"))
            Toast.makeText(this, "Email Id invalid", Toast.LENGTH_SHORT).show();
        else if(email.indexOf('@')==-1)
            Toast.makeText(this, "Email Id invalid", Toast.LENGTH_SHORT).show();
        else return true;
        }
catch(Exception e) {
    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
}
        return false;


    }
    private void ConnectToServer(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://u1701227.nettech.firm.in/api/signup.php".concat("?fname=" + fname + "&lname=" + lname + "&email="+email+"&password="+password+"&mobile="+mobile+"&address1="+address1+"&address2="+address2+"&city="+city+"&state="+state+"&pin="+pin+"&country="+id+"&security_ques_id="+qid+"&security_ans="+answer),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject x = new JSONObject(response);
                            String customer=x.getString("customer");
                            String address=x.getString("address");
                            String fin=x.getString("final");
                            if(fin.equals("Success")){
                                Toast.makeText(MainActivity.this, "Successful Signup", Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(MainActivity.this, "Already Registered....Try logging in", Toast.LENGTH_SHORT).show();



                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
    public void ConnectToServer2()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://u1701227.nettech.firm.in/api/country.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                first.add(jsonObject.getString("name"));
                            }
                            final String ar[]=first.toArray(new String[first.size()]);

                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,ar);
                            AutoCompleteTextView text=(AutoCompleteTextView)findViewById(R.id.country);
                            text.setThreshold(1);
                            text.setAdapter(adapter);

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
public void send()
{
    country=((AutoCompleteTextView)findViewById(R.id.country)).getText().toString();
    StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://u1701227.nettech.firm.in/api/country.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if(jsonObject.getString("name").equals(country))
                            {
                                id=jsonObject.getString("country_id");
                                break;
                            }

                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                }
            }){

    };
    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
    requestQueue.add(stringRequest);




}
public void submit2()
{
    StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://u1701227.nettech.firm.in/api/security_questions.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            second.add(jsonObject.getString("question"));

                        }
                        final String ar1[]=second.toArray(new String[second.size()]);


                        Spinner m=(Spinner)findViewById(R.id.q);
                        m.setAdapter(new ArrayAdapter(MainActivity.this,android.R.layout.simple_dropdown_item_1line,ar1));


                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                }
            }){

    };
    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
    requestQueue.add(stringRequest);



}

public void send2()
    {
        question=((Spinner)findViewById(R.id.q)).getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://u1701227.nettech.firm.in/api/security_questions.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(jsonObject.getString("question").equals(question))
                                {
                                    qid=jsonObject.getString("question_id");
                                    break;
                                }

                            }

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);




    }

}
