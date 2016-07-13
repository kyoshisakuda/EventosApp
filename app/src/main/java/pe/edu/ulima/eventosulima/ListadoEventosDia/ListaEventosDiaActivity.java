package pe.edu.ulima.eventosulima.ListadoEventosDia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import pe.edu.ulima.eventosulima.R;
import pe.edu.ulima.eventosulima.adapters.ListadoEventosDiaAdapter;
import pe.edu.ulima.eventosulima.beans.Eventos;

public class ListaEventosDiaActivity extends AppCompatActivity implements ListadoEventosDiaView {

    ListadoEventosDiaPresenter mPresenter;
    ListView lviEventosDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_dia);

        lviEventosDia = (ListView) findViewById(R.id.lviEventosDia);

        setPresenter(new ListadoEventosDiaPresenterImpl(this));

        mPresenter.getEventosDia(Calendar.getInstance().getTime());
    }

    @Override
    public void setPresenter(ListadoEventosDiaPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void mostrarListadoEventosDia(List<Eventos> eventos) {
        ListadoEventosDiaAdapter adapter = new ListadoEventosDiaAdapter(eventos, this);

        lviEventosDia.setAdapter(adapter);
    }
}
