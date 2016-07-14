package pe.edu.ulima.eventosulima.layout;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.edu.ulima.eventosulima.DetailActivity;
import pe.edu.ulima.eventosulima.R;
import pe.edu.ulima.eventosulima.beans.Evento;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    Firebase myFirebaseRef;

    RecyclerView recyclerView;


    public ArrayList<Evento> eventosLista  = new ArrayList<Evento>();
    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);


        Firebase myFirebaseRef;
        Firebase.setAndroidContext(getContext());

        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    Evento evento = data.getValue(Evento.class);

                    eventosLista.add(evento);

                }
                ContentAdapter adapter = new ContentAdapter(eventosLista, getContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getContext(), "Falló la conexión a firebase", Toast.LENGTH_LONG);
            }});
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_card, container, false);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent, final List<Evento> eventos) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Log.i("position",""+getAdapterPosition());
                    Log.i("positionfirebase",""+ eventos.get(getAdapterPosition()).getKey());
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("firebaseposition", eventos.get(getAdapterPosition()).getKey());
                    Log.i("position", ""+getPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.

        private Context context;
        private static final int LENGTH = 2;
        private List<Evento> mEventos;

        public ContentAdapter(List<Evento> eventos, Context context) {
            this.context = context;
            mEventos = eventos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent, mEventos);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.with(context).load(mEventos.get(position).getImagen()).into(holder.picture);



            //holder.picture.setImageResource(R.drawable.ing_sistemas_hackaton);

            holder.name.setText(mEventos.get(position).getEvento());
            holder.description.setText(mEventos.get(position).getDescripcion());
        }

        @Override
        public int getItemCount() {
            return  mEventos.size();
        }
    }

}
