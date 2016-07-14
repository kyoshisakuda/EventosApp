package pe.edu.ulima.eventosulima;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.edu.ulima.eventosulima.beans.Evento;

public class DetailActivity extends AppCompatActivity {
    /*void actualizar(Boolean b){
        Firebase.setAndroidContext(getApplicationContext());
        Firebase myFirebaseRefr;
        myFirebaseRefr = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/"+ eventosLista.get(0).getKey() + "/ir" , b);
        myFirebaseRefr.updateChildren(childUpdates);
    }*/

    public static final String EXTRA_POSITION = "position";

    public ArrayList<Evento> eventosLista  = new ArrayList<Evento>();
    public DetailActivity() {}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Firebase.setAndroidContext(getApplicationContext());

        Firebase myFirebaseRef;
        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            // Set Collapsing Toolbar layout to the screen
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            String key = getIntent().getStringExtra("firebaseposition");
            TextView placeDetail = (TextView) findViewById(R.id.place_detail);
            //Switch mSwitch = (Switch) findViewById(R.id.switch1);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getKey().equals(key)) {
                        Evento evento = data.getValue(Evento.class);
                        evento.setKey(data.getKey());
                        eventosLista.add(evento);

                    }

                }
                collapsingToolbar.setTitle(eventosLista.get(0).getEvento());
                placeDetail.setText(eventosLista.get(0).getDescripcion_detalle());
                Log.i("EventosLista", eventosLista.get(0).getDescripcion_detalle());
                TextView placeLocation =  (TextView) findViewById(R.id.place_location);
                placeLocation.setText(eventosLista.get(0).getUbicacion());
                ImageView placePicutre = (ImageView) findViewById(R.id.image);
                Picasso.with(getApplicationContext()).load(eventosLista.get(0).getImagen()).into(placePicutre);

                /*mSwitch.setChecked(eventosLista.get(0).isIr());



                mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        actualizar(b);
                        //Log.i("switch", ""+b);
                        //Log.i("keyswitch", eventosLista.get(0).getKey());


                    }
                });*/




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*final Firebase myFirebaseRef;
        Firebase.setAndroidContext(getApplicationContext());
        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");
        final Map<String, Object> childUpdates = new HashMap<>();
        Switch mSwitch = (Switch) findViewById(R.id.switch1);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

                Log.i("switch", ""+b);
                Log.i("keyswitch", eventosLista.get(0).getKey());




                childUpdates.put("/"+ eventosLista.get(0).getKey() + "/ir" , b);


            }

        });



        myFirebaseRef.updateChildren(childUpdates);
*/
    }


}
