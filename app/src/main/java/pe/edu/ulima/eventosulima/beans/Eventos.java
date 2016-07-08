package pe.edu.ulima.eventosulima.beans;

import java.util.Date;

/**
 * Created by kyosh on 8/07/2016.
 */
public class Eventos {
    private String NombreEvento;
    private String Lugar;
    private Date Fecha;
    private int rating;
    private boolean asistir;

    public Eventos() {}

    public Eventos(String nombreEvento, String lugar, Date fecha, int rating, boolean asistir) {
        NombreEvento = nombreEvento;
        Lugar = lugar;
        Fecha = fecha;
        this.rating = rating;
        this.asistir = asistir;
    }

    public String getNombreEvento() {
        return NombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        NombreEvento = nombreEvento;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isAsistir() {
        return asistir;
    }

    public void setAsistir(boolean asistir) {
        this.asistir = asistir;
    }
}
