package com.krakedev.financiero.servicios;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;

public class Banco {
	private int ultimoCodigo = 1000;

	public int getUltimoCodigo() {
		return ultimoCodigo;
	}

	public void setUltimoCodigo(int ultimoCodigo) {
		this.ultimoCodigo = ultimoCodigo;
	}

	public Banco() {
	}

	public Cuenta crearCuenta(Cliente cliente) {
		String codigoStr = ultimoCodigo + "";
		Cuenta cuentaNueva = new Cuenta(codigoStr);
		cuentaNueva.setPropietario(cliente);
		ultimoCodigo++;

		return cuentaNueva;
	}

	public boolean depositar(double monto, Cuenta cuenta) {
		if (monto > 0) {
			double montoTotal = cuenta.getSaldoActual() + monto;
			cuenta.setSaldoActual(montoTotal);
			return true;
		} else {
			return false;
		}
	}

	public boolean retirar(double monto, Cuenta cuenta) {
		if (monto > 0 && monto <= cuenta.getSaldoActual()) {
			double montoTotal = cuenta.getSaldoActual() - monto;
			cuenta.setSaldoActual(montoTotal);
			return true;
		} else {
			return false;
		}
	}

	public boolean transferir(Cuenta origen, Cuenta destino, double monto) {
		if (retirar(monto, origen)) {
			depositar(monto, destino);
			return true;
		} else {
			return false;
		}
	}
}
