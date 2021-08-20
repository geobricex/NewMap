package com.example.newmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newmap.Models.Country;
import com.example.newmap.Models.University;
import com.example.newmap.utiles.Methods;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap googleMap;
    FloatingActionsMenu FloatMenu;
    FloatingActionButton predeterminado;
    FloatingActionButton satelital;
    FloatingActionButton hibrido;
    String jsonFileString = "";
    List<Country> countries;
    String ResultGeo;
    RequestQueue requestQueue;
    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle bundle = this.getIntent().getExtras();
        ResultGeo = (bundle.getString("Result"));
        Log.i("Logs", "Country= " + ResultGeo);

        initialize();
        OnClickListener();
//        loadData();
        ListCountry();


    }

    private void initialize() {
        country = new Country();
        FloatMenu = findViewById(R.id.grpMenu);
        predeterminado = findViewById(R.id.fgbtnNormal);
        satelital = findViewById(R.id.fgbtnSatellite);
        hibrido = findViewById(R.id.fgbtnHybrid);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void OnClickListener() {
        predeterminado.setOnClickListener(v -> {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        });
        satelital.setOnClickListener(v -> {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        });
        hibrido.setOnClickListener(v -> {
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        });
    }

    private void loadData() {
        jsonFileString = loadJSONFromAsset("all.json");
//        Log.i("Logs", jsonFileString);
        Gson gson = new Gson();

        Type type = new TypeToken<List<University>>() {
        }.getType();

        countries = gson.fromJson(jsonFileString, type);
    }

    private void supportMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frgMapGoogle);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
    }

    private void positiongoogleMaps() {
//        for (int u = 0; u < countries.size(); u++) {
//            Log.i("Logs", "Item " + u + "\n" + countries.get(u).toString());
//            pointmarkers(new LatLng(countries.get(u).getLatitude(), countries.get(u).getLongitude()), countries.get(u).toString());
//        }
        pointmarkers(new LatLng(Double.parseDouble(country.getLatitude()), Double.parseDouble(country.getLongitude())), country.toString());
        rectanglemarkers();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap paramgoogleMap) {
        googleMap = paramgoogleMap;

        googleMap.setInfoWindowAdapter(new PopUpActivity(MapActivity.this));
        googleMap.setOnMarkerClickListener(this);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(-0.13, -78.3)).zoom(3).bearing(0).tilt(0).build();//Middle of the world as a starting point
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.animateCamera(cameraUpdate);
        positiongoogleMaps();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    private void rectanglemarkers() {
        //https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial?hl=es-419
        Polyline polyline = googleMap.addPolyline(new PolylineOptions().clickable(false).add(
                new LatLng(Double.parseDouble(country.getNorth()), Double.parseDouble(country.getEast())),
                new LatLng(Double.parseDouble(country.getNorth()), Double.parseDouble(country.getWest())),
                new LatLng(Double.parseDouble(country.getSouth()), Double.parseDouble(country.getWest())),
                new LatLng(Double.parseDouble(country.getSouth()), Double.parseDouble(country.getEast())),
                new LatLng(Double.parseDouble(country.getNorth()), Double.parseDouble(country.getEast()))
        ));
    }

    private void pointmarkers(LatLng points, String data) {
        MarkerOptions opMark = new MarkerOptions().position(points).draggable(true).title(data);
        Marker marker = googleMap.addMarker(opMark);
        marker.setTag(country.toString());
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void setDataCountry(JsonObject jsonToSubJSON) {
        country.setName(Methods.JsonToString(jsonToSubJSON, "Name", ""));
        country.setTelPref(Methods.JsonToString(jsonToSubJSON, "TelPref", ""));
        country.setCountryInfo(Methods.JsonToString(jsonToSubJSON, "CountryInfo", ""));
        Double latitude = Methods.getJsonToDubleVector(jsonToSubJSON, "GeoPt")[0];
        Double longitud = Methods.getJsonToDubleVector(jsonToSubJSON, "GeoPt")[1];

        country.setLatitude(latitude.toString());
        country.setLongitude(longitud.toString());

        JsonObject jsonObjectCapital = Methods.JsonToSubJSON(jsonToSubJSON, "Capital");
        country.setCapital(Methods.JsonToString(jsonObjectCapital, "Name", ""));
        country.setTD(Methods.securGetJSON(jsonObjectCapital, "TD").toString());

        JsonObject jsonObjectGeoRectangle = Methods.JsonToSubJSON(jsonToSubJSON, "GeoRectangle");
        country.setWest(Methods.securGetJSON(jsonObjectGeoRectangle, "West").toString());
        country.setEast(Methods.securGetJSON(jsonObjectGeoRectangle, "East").toString());
        country.setNorth(Methods.securGetJSON(jsonObjectGeoRectangle, "North").toString());
        country.setSouth(Methods.securGetJSON(jsonObjectGeoRectangle, "South").toString());

        country.setSouth(Methods.securGetJSON(jsonObjectGeoRectangle, "South").toString());

        JsonObject jsonObjectCountryCodes = Methods.JsonToSubJSON(jsonToSubJSON, "CountryCodes");
        country.setIso2(Methods.JsonToString(jsonObjectCountryCodes, "iso2", ""));
        country.setIso3(Methods.JsonToString(jsonObjectCountryCodes, "iso3", ""));
        country.setFlag("http://www.geognos.com/api/en/countries/flag/" + country.getIso2() + ".png");

        Log.i("Logs", "JSON => " + country.toString());

        supportMapFragment();
    }

    private void ListCountry() {
        Log.i("Logs", "ListCountry");
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET,
                "http://www.geognos.com/api/en/countries/info/all.json", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Logs", response.toString());
                JsonObject jsonObject = Methods.stringToJSON(response.toString());
                String StatusMsg = Methods.JsonToString(jsonObject, "StatusMsg", "");
                jsonObject = Methods.JsonToSubJSON(jsonObject, "Results");
                if (StatusMsg.equals("OK")) {
                    if (jsonObject.size() > 0) {
                        Set<String> keys = jsonObject.keySet();
                        for (String key : keys) {
                            JsonObject jsonToSubJSON = Methods.JsonToSubJSON(jsonObject, key);
                            if (Methods.JsonToString(jsonToSubJSON, "Name", "").toUpperCase().trim().equals(ResultGeo.trim().toUpperCase())) {

                                Log.i("Logs", "KEY => " + key);
                                Log.i("Logs", "PAIS => " + ResultGeo);
                                Log.i("Logs", "JSON => " + jsonToSubJSON.toString());
                                setDataCountry(jsonToSubJSON);
                            }
                        }
                    }
                    Toast.makeText(MapActivity.this, "Okay", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MapActivity.this, "NULL", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                header.put("Accept", "application/json");
                return header;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue.add(json);
    }
}