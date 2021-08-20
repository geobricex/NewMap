package com.example.newmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newmap.utiles.Methods;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.JsonObject;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PopUpActivity implements GoogleMap.InfoWindowAdapter, Callback {
    Context context;
    ImageView imgBandera;
    TextView name;
    TextView capital;
    TextView td;
    TextView iso2;
    TextView TelPref;
    Marker globalMarker;

    public PopUpActivity(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        String markerTitle = marker.getTitle();
        Log.i("Logs", "getInfoWindow " + markerTitle);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_pop_up, null);

        initialize(view);
        loadData(markerTitle);

        return view;
    }

    private void initialize(View view) {
        imgBandera = view.findViewById(R.id.imgBandera);
        name = view.findViewById(R.id.lblPais);
        capital = view.findViewById(R.id.lblCapital);
        td = view.findViewById(R.id.lblTime);
        iso2 = view.findViewById(R.id.lbliso2);
        TelPref = view.findViewById(R.id.lblTelefono);

    }

    private void loadData(String markerTitle) {
        Log.i("Logs", "loadData" + markerTitle);
       JsonObject jsonObject =  Methods.stringToJSON(markerTitle);
        Log.i("Logs", "json= " + jsonObject.toString());

            name.setText(Methods.JsonToString(jsonObject, "Name", ""));
            capital.setText(Methods.JsonToString(jsonObject, "Capital", ""));
            td.setText(Methods.securGetJSON(jsonObject, "TD").toString());
            TelPref.setText(Methods.JsonToString(jsonObject, "TelPref", ""));
            iso2.setText(Methods.JsonToString(jsonObject, "iso2", ""));

            Picasso.get().load(Methods.JsonToString(jsonObject, "Flag", "")).resize(200, 200).centerCrop().into(imgBandera, this);

    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Override
    public void onSuccess() {
        if (globalMarker != null && globalMarker.isInfoWindowShown()) {
            globalMarker.hideInfoWindow();
            globalMarker.showInfoWindow();
        }
    }

    @Override
    public void onError(Exception e) {

    }
}