package pe.edu.ulima.eventosulima.ListadoEventosDia;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.edu.ulima.eventosulima.beans.Evento;

/**
 * Created by kyosh on 13/07/2016.
 */
public class ListadoEventosDiaPresenterImpl implements ListadoEventosDiaPresenter {

    private ListadoEventosDiaView mView;

    public ListadoEventosDiaPresenterImpl(ListadoEventosDiaView mView) {
        this.mView = mView;
    }

    @Override
    public void getEventosDia(String fecha) {
        final List<Evento> eventos = new ArrayList<>();

        Firebase ref = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");
        Query query = ref.orderByChild("fecha").equalTo(fecha);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Evento e = dataSnapshot.getValue(Evento.class);
                e.setKey(dataSnapshot.getKey());
                eventos.add(e);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mView.mostrarListadoEventosDia(eventos);
    }
}
