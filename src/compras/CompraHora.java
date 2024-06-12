package compras;

import java.time.LocalDate;
import java.time.LocalTime;

import sistema.PuntoDeVenta;

public class CompraHora extends Compra {
	private int cantHoras;

	public CompraHora(LocalDate fecha, LocalTime hora, int cantHoras, PuntoDeVenta puntoDeVenta) {
		super(fecha, hora, puntoDeVenta);
		this.cantHoras = cantHoras;
	}

	public int getCantHoras() {
		return this.cantHoras;
	}

}
