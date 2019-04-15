package com.shyam.landoffreelancers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Maps extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap map;
    private DatabaseReference databaseReference;
    private FusedLocationProviderClient fusedLocationClient;
    String resp;
    Location myloc;
    boolean state;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        text=navigationView.getHeaderView(0).findViewById(R.id.displayname);
        if (text!=null){
            text.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        navigationView.setNavigationItemSelectedListener(this);
        mapFragment.getMapAsync(this);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    myloc=location;
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    MyLoc loc=new MyLoc(location.getLatitude(),location.getLongitude());
                    databaseReference.child("locs").child(user.getUid()).setValue(loc);
                }
            }
        });
        try {
            text.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }catch (Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent=new Intent(Maps.this,Search.class);
            startActivityForResult(intent,1001);
        } else if (id == R.id.nav_gallery) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signOut();
        Intent intent=new Intent(Maps.this,MainActivity.class);
        startActivity(intent);
        }else if (id==R.id.nav_profile){
            Intent intent=new Intent(Maps.this,Update.class);
            String uid=FirebaseAuth.getInstance().getUid();
            intent.putExtra("uid",uid);

            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setBuildingsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Loc loc=new Loc(location.getLatitude(),location.getLongitude());
        databaseReference.child("locs").child(user.getUid().toString()).setValue(loc);
        Toast.makeText(getApplicationContext(),String.valueOf(loc.getLang()),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1001&&resultCode==RESULT_OK){
            final String response=data.getStringExtra("response");
            resp=response;
            map.clear();
            state=data.getBooleanExtra("state",false);
            Toast.makeText(this, String.valueOf(state), Toast.LENGTH_SHORT).show();

                databaseReference.child("profs").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot shot:dataSnapshot.getChildren()){
                            Toast.makeText(Maps.this, shot.getKey(), Toast.LENGTH_SHORT).show();
                            if (shot.getValue().toString().contentEquals(response)){
                                Toast.makeText(getApplicationContext(),shot.getKey(),Toast.LENGTH_SHORT).show();
                                databaseReference.child("locs").child(shot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        MyLoc location=dataSnapshot.getValue(MyLoc.class);
                                        float[] distance=new float[2];
                                        Toast.makeText(getApplicationContext(),String.valueOf(location.getLat()),Toast.LENGTH_SHORT).show();
                                        Location.distanceBetween(myloc.getLatitude(),myloc.getLongitude(),location.getLat(),location.getLang(),distance);
                                        map.addMarker(new MarkerOptions().position(new LatLng(location.getLat(),location.getLang())).title(String.valueOf(distance[0])+"m"));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        databaseReference.child("locs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot shot:dataSnapshot.getChildren()){
                    MyLoc loc=shot.getValue(MyLoc.class);
                    float[] lcs=new float[2];
                    Location.distanceBetween(loc.getLat(),loc.getLang(),marker.getPosition().latitude,marker.getPosition().longitude,lcs);
                    if (lcs[0]<2){
                        Intent intent=new Intent(Maps.this,Book.class);
                        intent.putExtra("key",shot.getKey());
                        intent.putExtra("lat",loc.getLat());
                        intent.putExtra("lang",loc.getLang());
                        intent.putExtra("dlat",myloc.getLatitude());
                        intent.putExtra("dlang",myloc.getLongitude());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return false;
    }
}
