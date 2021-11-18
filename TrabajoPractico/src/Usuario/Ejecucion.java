package Usuario;

import implementacion.Farmacia;

import java.util.Map;
import java.util.Scanner;

import static java.util.Map.entry;

public class Ejecucion {

    private static Scanner scanner = new Scanner(System.in);

    // @formatter:off
    // Diccionario de tipos de turno
    private static Map<String, String> tiposTurnos = Map.ofEntries(
        entry("O",   "Obra social (demora estimada 30')"),
        entry("P",   "Particular (demora estimada 20')"),
        entry("P1",  "PAMI 1 receta (demora estimada 10')"),
        entry("P3",  "PAMI 2 o 3 recetas (demora estimada 30')"), 
        entry("P+3", "PAMI más de 3 recetas (demora estimada 50')")
    );

    // Array de códigos de turno
    private static String[] codigosTurnos = new String[]{"O", "P", "P1", "P3", "P+3"};
    // @formatter:on

    public static void main(String[] args) {
        imprimirLogo();

        int cantPuestos = leerCantPuestos();

        Farmacia farmacia = new Farmacia(cantPuestos);

        System.out.println("");
        String valorIngresado = leerTurno();
        while (!valorIngresado.equals("S")) {
            int ticket = farmacia.agregarTurno(valorIngresado);
            System.out.println("Se entregó el turno " + ticket + " de tipo " + tiposTurnos.get(valorIngresado));
            System.out.println("");
            valorIngresado = leerTurno();
        }

        farmacia.imprimirReporte();

        System.out.print("¿Ejecutar la simulación? (S/N): ");
        valorIngresado = scanner.nextLine();

        if (valorIngresado.toLowerCase().equals("s")) {
            farmacia.simular();
        }

    }

    public static int leerCantPuestos() {
        // Solicita y valida el ingreso de cant. de puestos de atención
        String valorIngresado = null;
        int cantPuestos = -1;
        boolean valido = false;
        while (!valido) {
            System.out.print("Ingrese la cantidad de puestos de atención: ");
            valorIngresado = scanner.nextLine();

            try {
                cantPuestos = Integer.parseInt(valorIngresado);
            } catch (NumberFormatException e) {
                System.out.printf("'%s' no es un número válido. Intente nuevamente.\n", valorIngresado);
                continue;
            }
            if (cantPuestos <= 0) {
                System.out.println("La cantidad de puestos debe ser mayor a cero.");
                continue;
            }
            valido = true;
        }

        return cantPuestos;
    }

    public static String leerTurno() {
        // Solicita y valida el ingreso de un nuevo turno

        // Impresión del menú de opciones
        for (int i = 0; i < codigosTurnos.length; i++) {
            System.out.println(codigosTurnos[i] + ": " + tiposTurnos.get(codigosTurnos[i]));
        }
        System.out.println("S: Finalizar e imprimir reporte");

        // Lectura de la opción del usuario
        String valorIngresado = null;
        boolean valido = false;
        while (!valido) {
            System.out.print("Ingrese una opción: ");
            valorIngresado = scanner.nextLine().toUpperCase();

            valido = tiposTurnos.containsKey(valorIngresado) || valorIngresado.equals("S");

            if (!valido) {
                System.out.println("'" + valorIngresado + "' No es una opción válida. Intente nuevamente.");
            }
        }
        return valorIngresado;
    }

    public static void imprimirLogo() {
        // Imprime el logo del sistema
        System.out.println("   ____                    ___       ___   ");
        System.out.println("  / __/__ _______ _  ___ _/ _ \\___  / (_)__");
        System.out.println(" / _// _ `/ __/  ' \\/ _ `/ ___/ _ \\/ / (_-<");
        System.out.println("/_/  \\_,_/_/ /_/_/_/\\_,_/_/   \\___/_/_/___/");
        System.out.println("Sistema de gestión de turnos");
        System.out.println("");

    }
}
