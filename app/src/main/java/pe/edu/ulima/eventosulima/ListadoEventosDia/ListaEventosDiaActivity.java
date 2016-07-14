package pe.edu.ulima.eventosulima.ListadoEventosDia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.edu.ulima.eventosulima.R;
import pe.edu.ulima.eventosulima.adapters.ListadoEventosDiaAdapter;
import pe.edu.ulima.eventosulima.beans.Evento;

public class ListaEventosDiaActivity extends AppCompatActivity implements ListadoEventosDiaView {

    ListadoEventosDiaPresenter mPresenter;
    ListView lviEventosDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_dia);

        lviEventosDia = (ListView) findViewById(R.id.lviEventosDia);

        setPresenter(new ListadoEventosDiaPresenterImpl(this));

        Intent prevIntent = getIntent();
        String fecha = prevIntent.getStringExtra("fecha");
        mPresenter.getEventosDia(fecha);
    }

    @Override
    public void setPresenter(ListadoEventosDiaPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void mostrarListadoEventosDia(List<Evento> eventos) {
        ListadoEventosDiaAdapter adapter = new ListadoEventosDiaAdapter(eventos, this);

        lviEventosDia.setAdapter(adapter);
    }
}
