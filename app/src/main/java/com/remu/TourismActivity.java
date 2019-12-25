package com.remu;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.remu.POJO.TourPlace;

import java.util.ArrayList;

public class TourismActivity extends AppCompatActivity {

//    private DatabaseReference databaseReference;
//    private FirebaseRecyclerAdapter<Restoran, TourismActivity.TourismViewHolder> firebaseRecyclerAdapter;
    private RecyclerView rvTour;
    private TourismAdapter tourismAdapter;
    private ArrayList<TourPlace> tourPlaces;
//    private CardView cvTour;
    private LatLng currentPosition;
    private String myLat, myLong;

    NestedScrollView tourScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);

        Intent intent = getIntent();
        currentPosition = new LatLng(Double.parseDouble(intent.getStringExtra("latitude")), Double.parseDouble(intent.getStringExtra("longitude")));

        initializeUI();

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    myLat = Double.toString(location.getLatitude());
                    myLong = Double.toString(location.getLongitude());
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                    // Display in Toast
//                    Toast.makeText(HalalFastFoodRestaurantActivity.this,
//                            "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
//                            Toast.LENGTH_LONG).show();
                }
            }
        });

        rvTour.setLayoutManager(new GridLayoutManager(TourismActivity.this, 2));

//        Query query = databaseReference.orderByKey();
//
//        FirebaseRecyclerOptions<Restoran> options = new FirebaseRecyclerOptions.Builder<Restoran>()
//                .setQuery(query, Restoran.class).build();
//
//
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Restoran, TourismActivity.TourismViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull TourismActivity.TourismViewHolder tourismViewHolder, int i, @NonNull Restoran tourism) {
//                String LatLong = tourism.getAlamatRestoran();
//                String getLatLong[] = LatLong.split(",");
//                String getLat= getLatLong[0], getLong=getLatLong[1];
//                DecimalFormat df = new DecimalFormat("#.##");
//                double jarak = getJarak(Double.parseDouble(myLat), Double.parseDouble(getLat),Double.parseDouble(myLong), Double.parseDouble(getLong));
//
//
//                tourismViewHolder.setGambar(tourism.getFoto());
//                tourismViewHolder.setNama(tourism.getNamaRestoran());
//                tourismViewHolder.setTempat(df.format(tourism.getAkumulasiRating()));
//                tourismViewHolder.setRating(df.format(jarak)+" KM");
//
//                String ID = tourism.getID();
//
//                tourismViewHolder.itemView.setOnClickListener(view -> {
//                    Intent intent = new Intent(TourismActivity.this, TourismDetailActivity.class);
//                    intent.putExtra(TourismDetailActivity.gambar, tourism.getFoto());
//                    intent.putExtra(TourismDetailActivity.id, ID);
//                    intent.putExtra(TourismDetailActivity.nama, tourism.getNamaRestoran());
//                    intent.putExtra(TourismDetailActivity.rating, tourism.getAkumulasiRating());
//                    startActivity(intent);
//                });
//            }
//
//            @NonNull
//            @Override
//            public TourismActivity.TourismViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kategori, parent, false);
//
//                return new TourismActivity.TourismViewHolder(view);
//            }
//        };

        tourismAdapter = new TourismAdapter(this, tourPlaces, currentPosition);
        tourismAdapter.setClickListener((view, position) -> {
            //set what happend when clicked
            Log.i("TAG", "You clicked number " + tourismAdapter.getItem(position) + ", which is at cell position " + position);

        });
        rvTour.setAdapter(tourismAdapter);
//        rvTour.setAdapter(firebaseRecyclerAdapter);
//        cvTour.setOnClickListener(View->addTour());

        tourScrollView = findViewById(R.id.tour_scroll);
        tourScrollView.post(() -> {
            tourScrollView.scrollTo(0, 0);
        });
    }

    private void initializeUI() {
        rvTour = findViewById(R.id.TourismCategories);
//        cvTour = findViewById(R.id.addTour);
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Food").child("Restoran").child("Wisata").child("Wisata");
        tourPlaces = new ArrayList<TourPlace>() {{
            add(new TourPlace(getDrawable(R.drawable.universal_studios_japan), "Universal Studio Japan", 4.96, new LatLng(34.669529, 135.497009)));
            add(new TourPlace(getDrawable(R.drawable.disneyland_japan), "Disneyland Japan", 4.91, new LatLng(35.652832, 139.839478)));
            add(new TourPlace(getDrawable(R.drawable.disneysea_japan), "Disneysea Japan", 4.91, new LatLng(35.652832, 139.839478)));
            add(new TourPlace(getDrawable(R.drawable.fuji_q_highland), "Fuji Q Highland", 4.85, new LatLng(35.16667, 138.68333)));
            add(new TourPlace(getDrawable(R.drawable.nagashima_resort), "Nagashima Resort", 4.80, new LatLng(35.0302, 136.7300)));
            add(new TourPlace(getDrawable(R.drawable.meiji_mura), "Meiji Mura", 4.69, new LatLng(35.3786, 136.9445)));
        }};
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        try {
//            firebaseRecyclerAdapter.startListening();
//        }catch (Exception e){
//
//        }
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        try {
//            firebaseRecyclerAdapter.stopListening();
//        }catch (Exception e){
//
//        }
//    }
//
//    private void addTour(){
//        Intent in = new Intent(TourismActivity.this, AddTourismActivity.class);
//        in.putExtra(AddTourismActivity.Jenis, "Wisata");
//        in.putExtra(AddTourismActivity.kategori, "Wisata");
//        startActivity(in);
//    }
//
//    private double getJarak(double lat1, double lat2, double long1, double long2){
//        Distance distance = new Distance();
//        return distance.distance(lat1, lat2, long1, long2);
//    }
//
//    public class TourismViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView foto;
//        TextView nama;
//        TextView tempat;
//        TextView rating;
//
//        public TourismViewHolder(@NonNull View itemView) {
//            super(itemView);
//            foto = itemView.findViewById(R.id.Gambarkategoi);
//            nama = itemView.findViewById(R.id.NamaKategori);
//            tempat = itemView.findViewById(R.id.JumlahRestoran);
//            rating = itemView.findViewById(R.id.Jarak);
//        }
//
//        public void setGambar(String foto) {
//            Glide.with(TourismActivity.this)
//                    .load(foto)
//                    .placeholder(R.drawable.bg_loading)
//                    .into(this.foto);
//        }
//
//        public void setNama(String text) {
//            nama.setText(text);
//        }
//
//        public void setTempat(String text) {
//            tempat.setText(text);
//        }
//
//        public void setRating(String text) {
//            rating.setText(text);
//        }
//    }

}
