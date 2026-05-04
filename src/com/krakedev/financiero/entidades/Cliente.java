package com.krakedev.financiero.entidades;

public class Cliente {
	private String cedula;
	private String nombre;
	private String apellido;

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Cliente(String cedula, String nombre, String apellido) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Cliente() {
	}

	public void imprimir() {
		System.out.println("-----Informacion Cliente-----");
		System.out.println("# Cedula: " + cedula);
		System.out.println("# Nombre: " + nombre);
		System.out.println("# Apellido: " + apellido);
	}
}
