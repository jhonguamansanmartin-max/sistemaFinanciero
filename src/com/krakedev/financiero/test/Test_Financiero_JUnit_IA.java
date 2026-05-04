package com.krakedev.financiero.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

/**
 * Clase de pruebas unitarias para los métodos de la clase Banco.
 * Se validan los métodos: crearCuenta, depositar, retirar y transferir.
 */
class Test_Financiero_JUnit_IA {

    private Banco banco;
    private Cliente cliente;
    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        banco = new Banco();
        cliente = new Cliente("123456789", "Juan", "Perez");
        // Crear una cuenta con id cualquiera para pruebas que no requieren crearCuenta
        cuenta = new Cuenta("C001");
    }

    // ==================== PRUEBAS PARA crearCuenta ====================

    @Test
    void testCrearCuenta_DeberiaCrearCuentaConIdCorrectoYPropietarioAsignado() {
        // Se espera que el último código actual sea 1000
        int codigoEsperado = 1000;
        // Creamos la cuenta
        Cuenta nuevaCuenta = banco.crearCuenta(cliente);
        
        // Verificar id: debe ser el código convertido a String (1000)
        assertEquals(String.valueOf(codigoEsperado), nuevaCuenta.getId(), 
                "El id de la cuenta debe ser el último código convertido a String");
        
        // Verificar que el propietario sea el mismo objeto cliente pasado
        assertSame(cliente, nuevaCuenta.getPropietario(), 
                "El propietario de la cuenta debe ser el cliente proporcionado");
        
        // Verificar que el último código se haya incrementado a 1001
        assertEquals(1001, banco.getUltimoCodigo(), 
                "El último código debe incrementarse después de crear la cuenta");
    }

    @Test
    void testCrearCuenta_DeberiaIncrementarUltimoCodigoCadaVez() {
        Cuenta cuenta1 = banco.crearCuenta(cliente);
        assertEquals("1000", cuenta1.getId());
        assertEquals(1001, banco.getUltimoCodigo());
        
        Cuenta cuenta2 = banco.crearCuenta(cliente);
        assertEquals("1001", cuenta2.getId());
        assertEquals(1002, banco.getUltimoCodigo());
    }

    @Test
    void testCrearCuenta_DeberiaDejarSaldoInicialCeroYTipoPorDefecto() {
        Cuenta nuevaCuenta = banco.crearCuenta(cliente);
        // El constructor de Cuenta inicializa saldoActual = 0 y tipo = "A"
        assertEquals(0.0, nuevaCuenta.getSaldoActual(), 0.0001, 
                "El saldo inicial de una cuenta nueva debe ser 0");
        assertEquals("A", nuevaCuenta.getTipo(), 
                "El tipo de cuenta por defecto debe ser 'A'");
    }

    // ==================== PRUEBAS PARA depositar ====================

    @Test
    void testDepositar_MontoPositivo_DeberiaRetornarTrueYActualizarSaldo() {
        double saldoInicial = cuenta.getSaldoActual(); // 0
        double monto = 150.75;
        
        boolean resultado = banco.depositar(monto, cuenta);
        
        assertTrue(resultado, "Depósito con monto positivo debe retornar true");
        assertEquals(saldoInicial + monto, cuenta.getSaldoActual(), 0.0001,
                "El saldo debe incrementarse en el monto depositado");
    }

    @Test
    void testDepositar_MontoCero_DeberiaRetornarFalseYNoModificarSaldo() {
        double saldoInicial = cuenta.getSaldoActual(); // 0
        double monto = 0.0;
        
        boolean resultado = banco.depositar(monto, cuenta);
        
        assertFalse(resultado, "Depósito con monto cero debe retornar false");
        assertEquals(saldoInicial, cuenta.getSaldoActual(), 0.0001,
                "El saldo no debe modificarse cuando el monto es cero");
    }

    @Test
    void testDepositar_MontoNegativo_DeberiaRetornarFalseYNoModificarSaldo() {
        // Primero depositamos algo para tener saldo positivo
        banco.depositar(500, cuenta);
        double saldoInicial = cuenta.getSaldoActual();
        double monto = -100;
        
        boolean resultado = banco.depositar(monto, cuenta);
        
        assertFalse(resultado, "Depósito con monto negativo debe retornar false");
        assertEquals(saldoInicial, cuenta.getSaldoActual(), 0.0001,
                "El saldo no debe modificarse cuando el monto es negativo");
    }

    @Test
    void testDepositar_MontoPositivoMultiplesVeces_AcumulaCorrectamente() {
        banco.depositar(100, cuenta);
        banco.depositar(200.50, cuenta);
        assertEquals(300.50, cuenta.getSaldoActual(), 0.0001);
    }

    // ==================== PRUEBAS PARA retirar ====================

    @Test
    void testRetirar_MontoValidoYSaldoSuficiente_DeberiaRetornarTrueYReducirSaldo() {
        // Primero depositamos para tener saldo
        banco.depositar(1000, cuenta);
        double saldoInicial = cuenta.getSaldoActual();
        double monto = 350.25;
        
        boolean resultado = banco.retirar(monto, cuenta);
        
        assertTrue(resultado, "Retiro con monto válido y saldo suficiente debe retornar true");
        assertEquals(saldoInicial - monto, cuenta.getSaldoActual(), 0.0001,
                "El saldo debe disminuir en el monto retirado");
    }

    @Test
    void testRetirar_MontoMayorQueSaldo_DeberiaRetornarFalseYNoModificarSaldo() {
        banco.depositar(500, cuenta);
        double saldoInicial = cuenta.getSaldoActual();
        double monto = 600; // mayor que saldo
        
        boolean resultado = banco.retirar(monto, cuenta);
        
        assertFalse(resultado, "Retiro con monto mayor al saldo debe retornar false");
        assertEquals(saldoInicial, cuenta.getSaldoActual(), 0.0001,
                "El saldo no debe modificarse cuando el monto excede el saldo");
    }

    @Test
    void testRetirar_MontoCero_DeberiaRetornarFalseYNoModificarSaldo() {
        banco.depositar(300, cuenta);
        double saldoInicial = cuenta.getSaldoActual();
        
        boolean resultado = banco.retirar(0.0, cuenta);
        
        assertFalse(resultado, "Retiro con monto cero debe retornar false");
        assertEquals(saldoInicial, cuenta.getSaldoActual(), 0.0001);
    }

    @Test
    void testRetirar_MontoNegativo_DeberiaRetornarFalseYNoModificarSaldo() {
        banco.depositar(200, cuenta);
        double saldoInicial = cuenta.getSaldoActual();
        
        boolean resultado = banco.retirar(-50, cuenta);
        
        assertFalse(resultado, "Retiro con monto negativo debe retornar false");
        assertEquals(saldoInicial, cuenta.getSaldoActual(), 0.0001);
    }

    @Test
    void testRetirar_ExactamenteElSaldo_DeberiaDejarSaldoCero() {
        banco.depositar(100, cuenta);
        boolean resultado = banco.retirar(100, cuenta);
        
        assertTrue(resultado);
        assertEquals(0.0, cuenta.getSaldoActual(), 0.0001,
                "Retirar el saldo completo debe dejar la cuenta en cero");
    }

    // ==================== PRUEBAS PARA transferir ====================

    @Test
    void testTransferir_CuandoRetiroExitoso_DeberiaTransferirMontoYActualizarAmbasCuentas() {
        // Configurar cuentas
        Cuenta origen = new Cuenta("ORIG");
        Cuenta destino = new Cuenta("DEST");
        banco.depositar(500, origen);   // origen saldo 500
        banco.depositar(100, destino);  // destino saldo 100
        double monto = 200;
        double saldoOrigenInicial = origen.getSaldoActual();
        double saldoDestinoInicial = destino.getSaldoActual();
        
        boolean resultado = banco.transferir(origen, destino, monto);
        
        assertTrue(resultado, "Transferencia con saldo suficiente debe retornar true");
        assertEquals(saldoOrigenInicial - monto, origen.getSaldoActual(), 0.0001,
                "La cuenta origen debe disminuir en el monto transferido");
        assertEquals(saldoDestinoInicial + monto, destino.getSaldoActual(), 0.0001,
                "La cuenta destino debe aumentar en el monto transferido");
    }

    @Test
    void testTransferir_CuandoRetiroFallaPorSaldoInsuficiente_NoDebeModificarNingunaCuenta() {
        Cuenta origen = new Cuenta("ORIG");
        Cuenta destino = new Cuenta("DEST");
        banco.depositar(100, origen);   // origen saldo 100
        banco.depositar(50, destino);   // destino saldo 50
        double monto = 200; // insuficiente
        double saldoOrigenInicial = origen.getSaldoActual();
        double saldoDestinoInicial = destino.getSaldoActual();
        
        boolean resultado = banco.transferir(origen, destino, monto);
        
        assertFalse(resultado, "Transferencia con saldo insuficiente debe retornar false");
        assertEquals(saldoOrigenInicial, origen.getSaldoActual(), 0.0001,
                "La cuenta origen no debe modificarse");
        assertEquals(saldoDestinoInicial, destino.getSaldoActual(), 0.0001,
                "La cuenta destino no debe modificarse");
    }

    @Test
    void testTransferir_CuandoMontoEsCero_RetiroFallaYNoSeRealizaTransferencia() {
        Cuenta origen = new Cuenta("ORIG");
        Cuenta destino = new Cuenta("DEST");
        banco.depositar(300, origen);
        banco.depositar(100, destino);
        double saldoOrigenInicial = origen.getSaldoActual();
        double saldoDestinoInicial = destino.getSaldoActual();
        
        boolean resultado = banco.transferir(origen, destino, 0.0);
        
        assertFalse(resultado, "Transferir monto cero debe fallar (retirar retorna false)");
        assertEquals(saldoOrigenInicial, origen.getSaldoActual(), 0.0001);
        assertEquals(saldoDestinoInicial, destino.getSaldoActual(), 0.0001);
    }

    @Test
    void testTransferir_CuandoMontoNegativo_RetiroFallaYNoSeRealizaTransferencia() {
        Cuenta origen = new Cuenta("ORIG");
        Cuenta destino = new Cuenta("DEST");
        banco.depositar(400, origen);
        banco.depositar(200, destino);
        double saldoOrigenInicial = origen.getSaldoActual();
        
        boolean resultado = banco.transferir(origen, destino, -50);
        
        assertFalse(resultado, "Transferir monto negativo debe fallar");
        assertEquals(saldoOrigenInicial, origen.getSaldoActual(), 0.0001);
    }
}
