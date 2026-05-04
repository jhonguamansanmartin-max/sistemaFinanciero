package com.krakedev.financiero.entidades;

public class Cuenta {
	private String id;
	private double saldoActual;
	private String tipo;
	private Cliente propietario;

	public Cliente getPropietario() {
		return propietario;
	}

	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(double saldoActual) {
		this.saldoActual = saldoActual;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cuenta(String id) {
		this.id = id;
		saldoActual = 0;
		tipo = "A";
		propietario = new Cliente();
	}

	public void imprimir() {
		System.out.println("----Informacion de Cuenta----");
		System.out.println("# Id de cuenta: " + id);
		System.out.println("# Saldo actual: " + saldoActual);
		System.out.println("# Tipo: " + tipo);
		System.out.println();
		System.out.println("-----Informacion Cliente-----");
		System.out.println("# Cedula: " + propietario.getCedula());
		System.out.println("# Nombre: " + propietario.getNombre());
		System.out.println("# Apellido: " + propietario.getApellido());
	}

}
