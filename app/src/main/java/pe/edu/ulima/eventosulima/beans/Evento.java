package pe.edu.ulima.eventosulima.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Miguel on 6/07/2016.
 */
public class Evento implements Serializable {
    private String key;
    private String carrera;
    private String descripcion;
    private String descripcion_detalle;
    private String evento;
    private String imagen;
    private String ubicacion;
    private String fecha;
    private String hora;
    private boolean ir;
    private String link;

    public Evento(){}

    public String getKey() {
        return key;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion_detalle() {
        return descripcion_detalle;
    }

    public void setDescripcion_detalle(String descripcion_detalle) {
        this.descripcion_detalle = descripcion_detalle;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isIr() {
        return ir;
    }

    public void setIr(boolean ir) {
        this.ir = ir;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
