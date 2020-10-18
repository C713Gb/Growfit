package com.example.myapplication;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetNearbyPlaceData extends AsyncTask<Object,String,String> {
    String googlePlaceaData;
    GoogleMap googleMap;
    String url;
    @Override
    protected String doInBackground(Object... objects) {
        googleMap=(GoogleMap) objects[0];
        url=(String) objects[1];
        DownloadUrl downloadUrl=new DownloadUrl();
        try{
            googlePlaceaData=downloadUrl.readUrl(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        return googlePlaceaData;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject parentObject = new JSONObject();
            JSONArray resultArray= parentObject.getJSONArray("results");
            for(int i=0;i<resultArray.length();i++){
                JSONObject jsonObject=resultArray.getJSONObject(i);
                JSONObject locationObj=jsonObject.getJSONObject("geometry").getJSONObject("location");
                String latitude=locationObj.getString("lat");
                String longitude=locationObj.getString("lng");

                JSONObject nameObject =resultArray.getJSONObject(i);
                String name=nameObject.getString("name");
                LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);

                googleMap.addMarker(markerOptions);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
