package pe.edu.ulima.eventosulima.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import pe.edu.ulima.eventosulima.R;
import pe.edu.ulima.eventosulima.beans.Eventos;

/**
 * Created by kyosh on 8/07/2016.
 */
public class ListadoEventosDiaAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Eventos> mEventos;
    private Context mContext;

    public ListadoEventosDiaAdapter(List<Eventos> mEventos, Context mContext) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mEventos = mEventos;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mEventos.size();
    }

    @Override
    public Object getItem(int i) {
        return mEventos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_dia_evento, null);
            viewHolder = new ViewHolder();
            viewHolder.tviHora = (TextView) view.findViewById(R.id.tviHora);
            viewHolder.tviLugarEvento = (TextView) view.findViewById(R.id.tviLugarEvento);
            viewHolder.tviNombreEvento = (TextView) view.findViewById(R.id.tviNombreEvento);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Eventos evento = mEventos.get(i);

        viewHolder.tviNombreEvento.setText(evento.getNombreEvento());
        viewHolder.tviLugarEvento.setText(evento.getLugar());
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(evento.getFecha());
        viewHolder.tviHora.setText(Calendar.HOUR_OF_DAY+":"+Calendar.MINUTE);

        return view;
    }

    class ViewHolder {
        TextView tviHora;
        TextView tviNombreEvento;
        TextView tviLugarEvento;
    }
}
