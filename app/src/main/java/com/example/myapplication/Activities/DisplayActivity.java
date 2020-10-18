package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private static final String PRODUCT_URL="http://192.168.43.37/myApi/api.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter,adapter1;
    private List<ListItem> listItems;
    SearchView searchs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        recyclerView = findViewById(R.id.recyclerdisplay);
        searchs=findViewById(R.id.searchbar);
        searchs.setQueryHint("Search for videos");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        loadProducts();
        searchs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                search(newText);
                return true;
            }
        });
    }
    private void search(String string){

        ArrayList<ListItem> myList= new ArrayList<>();
        for (ListItem object:listItems){
            if(object.getV_name().toLowerCase().contains(string.toLowerCase())){
                myList.add(object);
            }
        }
        adapter1=new ReAdapter(DisplayActivity.this,myList);
        recyclerView.setAdapter(adapter1);
    }
    //v_id,v_name,v_image,v_video_url,v_user,v_desc
    private void loadProducts(){
        StringRequest stringRequest= new StringRequest(Request.Method.GET, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray video = new JSONArray(response);
                    for(int i=0; i<video.length();i++){
                        JSONObject videoObject = video.getJSONObject(i);
                        int id = videoObject.getInt("v_id");
                        String name=videoObject.getString("v_name");
                        String videourl=videoObject.getString("v_video_url");
                        String videoimage=videoObject.getString("v_image");
                        String videodescription=videoObject.getString("v_desc");
                        String videouser=videoObject.getString("v_user");
                        ListItem listItem= new ListItem(id,name,videoimage,videourl,videouser,videodescription);
                        listItems.add(listItem);
                    }
                    adapter=new ReAdapter(DisplayActivity.this,listItems);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DisplayActivity.this, "error is"+error, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}