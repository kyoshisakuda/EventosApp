package pe.edu.ulima.eventosulima;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import pe.edu.ulima.eventosulima.ListadoEventosDia.ListaEventosDiaActivity;

public class CalendarioActivity extends AppCompatActivity implements CalendarPickerView.CellClickInterceptor{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today);

        calendar.setCellClickInterceptor(this);
    }

    @Override
    public boolean onCellClicked(Date date) {
        Intent intent = new Intent(this, ListaEventosDiaActivity.class);
        startActivity(intent);
        return true;
    }
}
