package pe.edu.ulima.eventosulima.ListadoEventosDia;

import java.util.List;

import pe.edu.ulima.eventosulima.beans.Eventos;

/**
 * Created by kyosh on 8/07/2016.
 */
public interface ListadoEventosDiaView {
    public void setPresenter(ListadoEventosDiaPresenter presenter);
    public void mostrarListadoEventosDia(List<Eventos> eventos);
}
