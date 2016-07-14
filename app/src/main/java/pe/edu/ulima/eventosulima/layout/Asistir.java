package pe.edu.ulima.eventosulima.layout;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import pe.edu.ulima.eventosulima.DetailActivity;
import pe.edu.ulima.eventosulima.R;
import pe.edu.ulima.eventosulima.beans.Evento;
import pe.edu.ulima.eventosulima.services.AlarmService;

/**
 * A simple {@link Fragment} subclass.
 */
public class Asistir extends Fragment {
    Firebase myFirebaseRef;

    RecyclerView recyclerView;

    public ArrayList<Evento> eventosLista  = new ArrayList<Evento>();
    public Asistir() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Mis eventos");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);

        Firebase.setAndroidContext(getContext());

        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Evento evento = data.getValue(Evento.class);
                    evento.setKey(data.getKey());
                    if(evento.isIr() == true) {
                        eventosLista.add(evento);
                    }
                }
                try {
                    ContentAdapter adapter = new ContentAdapter(eventosLista, getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }catch (Exception e){
                    Log.i("Error", e.toString());
                }
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
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public TextView fecha;
        public ImageButton asistir;
        public ImageButton compartir;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent, final List<Evento> eventos) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            Log.i("viewholdersize", ""+eventos.size());
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            asistir = (ImageButton) itemView.findViewById(R.id.favorite_button);
            asistir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String imagen = "";
                    if(eventos.get(getAdapterPosition()).isIr() == false) {
                        asistir.setImageResource(R.drawable.avatar);
                        Firebase myFirebaseRef;
                        Firebase.setAndroidContext(view.getContext());

                        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");
                        String key = eventos.get(getAdapterPosition()).getKey();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/"+ key + "/ir" , true);
                        myFirebaseRef.updateChildren(childUpdates);
                        Toast.makeText(view.getContext(), "Evento para asistir", Toast.LENGTH_SHORT);

                        //Activar Notificacion y agregar a calendario
                        startAlarm(view.getContext(),
                                eventos.get(getAdapterPosition()).getFecha()+" "+eventos.get(getAdapterPosition()).getHora(),
                                eventos.get(getAdapterPosition()).getEvento(),
                                Integer.parseInt(eventos.get(getAdapterPosition()).getKey()));
                        addCalendar(eventos.get(getAdapterPosition()).getFecha()+" "+eventos.get(getAdapterPosition()).getHora(),
                                eventos.get(getAdapterPosition()).getEvento(),
                                eventos.get(getAdapterPosition()).getDescripcion(),
                                eventos.get(getAdapterPosition()).getUbicacion(),
                                view.getContext());
                    }else if (eventos.get(getAdapterPosition()).isIr() == true){
                        asistir.setImageResource(R.drawable.social);
                        Firebase myFirebaseRef;
                        Firebase.setAndroidContext(view.getContext());

                        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");
                        String key = eventos.get(getAdapterPosition()).getKey();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/"+ key + "/ir" , false);

                        myFirebaseRef.updateChildren(childUpdates);

                        Toast.makeText(view.getContext(), "Evento para asistir", Toast.LENGTH_SHORT);

                        //Cancelar Notificación
                        deleteAlarm(view.getContext(),
                                eventos.get(getAdapterPosition()).getFecha()+" "+eventos.get(getAdapterPosition()).getHora(),
                                eventos.get(getAdapterPosition()).getEvento(),
                                Integer.parseInt(eventos.get(getAdapterPosition()).getKey()));
                    }
                }
            });
            compartir = (ImageButton) itemView.findViewById(R.id.share_button);
            compartir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context lcontext = view.getContext();
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = eventos.get(getAdapterPosition()).getLink();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, eventos.get(getAdapterPosition()).getEvento());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    lcontext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("firebaseposition", eventos.get(getAdapterPosition()).getKey());
                    context.startActivity(intent);
                }
            });
        }

        private void startAlarm(Context context, String strFecha, String evento, int key) {
            //String strFecha = "14-07-2016 15:04:00";
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date fecha = new Date();
            try {
                fecha = df.parse(strFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(fecha);
            long when = calendar.getTimeInMillis();         // notification time
            Intent intent = new Intent(context, AlarmService.class);
            intent.putExtra("evento", evento);
            intent.putExtra("key", key);
            PendingIntent pendingIntent = PendingIntent.getService(context, key, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
            //Toast.makeText(context,"Alarma lanzada "+evento+" "+key, Toast.LENGTH_SHORT).show();
        }

        private void deleteAlarm(Context context, String strFecha, String evento, int key) {
            //String strFecha = "14-07-2016 15:04:00";
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date fecha = new Date();
            try {
                fecha = df.parse(strFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmService.class);
            intent.putExtra("evento", evento);
            intent.putExtra("key", key);
            PendingIntent pendingIntent = PendingIntent.getService(context, key, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
            //Toast.makeText(context,"Alarma lanzada "+evento+" "+key, Toast.LENGTH_SHORT).show();
        }

        private void addCalendar(String dateInString, String titulo, String descripcion, String lugar, Context context) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            //String dateInString = "20-07-2016 14:30:00";
            Date date = new Date();
            try {
                date = sdf.parse(dateInString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            final ContentValues event = new ContentValues();
            event.put(CalendarContract.Events.CALENDAR_ID, 1);

            event.put(CalendarContract.Events.TITLE, titulo);
            event.put(CalendarContract.Events.DESCRIPTION, descripcion);
            event.put(CalendarContract.Events.EVENT_LOCATION, lugar);

            event.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
            event.put(CalendarContract.Events.DTEND, cal.getTimeInMillis()+60*60*1000);
            event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
            event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true

            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");
            } else {
                baseUri = Uri.parse("content://calendar/events");
            }

            context.getContentResolver().insert(baseUri, event);
        }
    }


    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.

        private Context context;
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
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Log.i("MEVENTOS", ""+mEventos.size());
            Picasso.with(context).load(mEventos.get(position).getImagen()).into(holder.picture);
            if(mEventos.get(position).isIr() == true){
                holder.asistir.setImageResource(R.drawable.social);
            }else {
                holder.asistir.setImageResource(R.drawable.avatar);
            }

            holder.name.setText(mEventos.get(position).getEvento());
            holder.description.setText(mEventos.get(position).getDescripcion());
            holder.fecha.setText(mEventos.get(position).getFecha());
            Log.i("position", ""+position);

        }

        @Override
        public int getItemCount() {
            return mEventos.size();
        }
    }

}
