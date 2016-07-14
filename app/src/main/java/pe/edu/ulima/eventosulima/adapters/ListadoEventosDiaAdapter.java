package pe.edu.ulima.eventosulima.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pe.edu.ulima.eventosulima.DetailActivity;
import pe.edu.ulima.eventosulima.R;
import pe.edu.ulima.eventosulima.beans.Evento;

/**
 * Created by kyosh on 8/07/2016.
 */
public class ListadoEventosDiaAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Evento> mEventos;
    private Context mContext;

    public ListadoEventosDiaAdapter(List<Evento> mEventos, Context mContext) {
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

        final Evento evento = mEventos.get(i);

        viewHolder.tviNombreEvento.setText(evento.getEvento());
        viewHolder.tviLugarEvento.setText(evento.getUbicacion());

        Date fecha = new Date();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            fecha = format.parse(evento.getFecha()+" "+evento.getHora());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        String marcadorHora = calendario.get(Calendar.AM_PM) == Calendar.PM ? "pm" : "am";
        viewHolder.tviHora.setText(calendario.get(Calendar.HOUR_OF_DAY)+":"+evento.getHora().split(":")[1]+" "+marcadorHora);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, evento.getKey(), Toast.LENGTH_SHORT).show();

                Intent intentDetail = new Intent(mContext, DetailActivity.class);
                intentDetail.putExtra("firebaseposition", evento.getKey());
                mContext.startActivity(intentDetail);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView tviHora;
        TextView tviNombreEvento;
        TextView tviLugarEvento;
    }
}
