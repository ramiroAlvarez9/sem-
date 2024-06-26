package estacionamientos;

import notificaciones.NotificacionFinEstacionamiento;
import notificaciones.INotificacion;
import notificaciones.NotificacionInicioEstacionamiento;
import notificaciones.Notificador;
import sistema.SEM;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static sistema.SEM.getFinHorario;
import static sistema.SEM.getPrecioPorHora;


public abstract class Estacionamiento {
    protected final String patente;
    private final LocalTime inicio;
    protected LocalTime fin;
    protected EstadoDeEstacionamiento estado;

    public Estacionamiento(Notificador notificador, String patente) {
        this.patente = patente;
        this.inicio = LocalTime.now();
        this.estado = EstadoDeEstacionamiento.Vigente;

        INotificacion INotificacion = new NotificacionInicioEstacionamiento(patente, inicio);
        notificador.notificar(patente, INotificacion);
    }

    public LocalTime calcularTiempoDentroDe(int horas) {
        return LocalTime.now().plusHours(horas);
    }

    public LocalTime calcularHorarioFin(LocalTime tiempo) {
        LocalTime finHorarioSEM = getFinHorario();
        return tiempo.isAfter(finHorarioSEM) ? finHorarioSEM : tiempo;
    }

    public Double costo() {
        return getPrecioPorHora() * inicio.until(fin, ChronoUnit.HOURS);
    }

    public String getPatente() {
        return patente;
    }

    public LocalTime getInicio() {
        return inicio;
    }
    
    public LocalTime getFin() {
    	return fin;
    }

    public void setFin(LocalTime tiempo) {
        this.fin = calcularHorarioFin(tiempo);
    }

    public void finalizar(SEM sem, Notificador notificador) {
        INotificacion INotificacion = new NotificacionFinEstacionamiento(patente, fin, costo());
        notificador.notificar(patente, INotificacion);
        this.estado = EstadoDeEstacionamiento.NoVigente;
    }
    
    public boolean esVigente() {
    	return this.estado == EstadoDeEstacionamiento.Vigente;
    }
  

}

