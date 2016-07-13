package pe.edu.ulima.eventosulima.ListadoEventosDia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.edu.ulima.eventosulima.beans.Eventos;

/**
 * Created by kyoshi on 8/07/2016.
 */
public class ListadoEventosDiaPresenterImpl implements ListadoEventosDiaPresenter{

    private ListadoEventosDiaView mView;

    public ListadoEventosDiaPresenterImpl(ListadoEventosDiaView mView) {
        this.mView = mView;
    }

    @Override
    public void getEventosDia(Date fecha) {
        List<Eventos> eventos = new ArrayList<>();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        eventos.add(new Eventos("Mi Cumpleaños", "Mi Casa", calendario.getTime(), 5, true));
        calendario.add(Calendar.HOUR_OF_DAY, 2);
        eventos.add(new Eventos("Hackaton", "S-270", calendario.getTime(), 5, true));
        calendario.add(Calendar.HOUR_OF_DAY, 2);
        eventos.add(new Eventos("Competitividad Textil", "Aula Magna A", calendario.getTime(), 5, true));
        calendario.add(Calendar.HOUR_OF_DAY, 2);
        eventos.add(new Eventos("Ceremonia de Graduación", "Zoom", calendario.getTime(), 5, true));

        mView.mostrarListadoEventosDia(eventos);
    }
}
