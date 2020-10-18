package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FoodIntake extends AppCompatActivity {

    EditText search;
    TextView food, cal;
    Button search_btn;
    ImageButton back;
    String put,category,Url,calorie;
    String query=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intake);

        search = findViewById(R.id.search_food);
        search_btn = findViewById(R.id.search_btn_2);
        food = findViewById(R.id.food_name);
        cal = findViewById(R.id.cal_name);
        back = findViewById(R.id.back_5);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put = search.getText().toString().trim();
                category= put.replaceAll(" ","%40");
                //spinners.setEnabled(true);
                try {
                    query = URLEncoder.encode(category, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                Url = "https://api.edamam.com/api/food-database/parser?ingr=" + query + "&app_id=cb767032&app_key=c33a26a5b116689deb1b1063b9978780";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest objectRequest = new StringRequest(Request.Method.GET, Url, new ResponseListener(), new ErrorListener());
                requestQueue.add(objectRequest);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private class ResponseListener implements Response.Listener {

        @Override
        public void onResponse(Object response) {

            try {
                JSONObject jsonObject= new JSONObject((String) response);
                JSONArray parse = jsonObject.getJSONArray("hints");
                for(int i=0; i<1;i++){
                    JSONObject get=parse.getJSONObject(i);
                    // String food=get.getString("food");
                    JSONObject foo = get.getJSONObject("food");
                    String foods=foo.getString("label");
                    food.setText(foods);
                    JSONObject nut=foo.getJSONObject("nutrients");
                    calorie=nut.getString("ENERC_KCAL");
                    double dCal = Double.parseDouble(calorie);
                    calorie = String.format("%.0f",dCal);
                    cal.setText(String.format("%.0f",dCal) + " kcal");
//                    String proteins=nut.getString("PROCNT");
//                    prot = proteins;
//                    dProt= Double.parseDouble(proteins);
//                    texts1.setText(String.format("%.0f",dProt));
//                    String fats=nut.getString("FAT");
//                    fat = fats;
//                    dFat= Double.parseDouble(fats);
//                    texts2.setText(String.format("%.0f",dFat));
//                    String carbo=nut.getString("CHOCDF");
//                    carb = carbo;
//                    dCarb= Double.parseDouble(carbo);
//                    texts3.setText(String.format("%.0f",dCarb));
                   // res = calorie;
                }} catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonObjects = null;
            try {
                jsonObjects = new JSONObject((String) response);
                JSONArray hint=jsonObjects.getJSONArray("hints");
                for(int k=0;k<hint.length();k++) {
                    JSONObject gets = hint.getJSONObject(k);
                    // String list=gets.getString("food");
                    JSONObject hin = gets.getJSONObject("food");


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(FoodIntake.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();
        }

           }
}