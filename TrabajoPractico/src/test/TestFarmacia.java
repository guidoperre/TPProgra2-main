package test;

import implementacion.Farmacia;

public class TestFarmacia {
    public static void main(String[] args) {

        String[] turnos = new String[] { "P+3", "P+3", "P3", "P", "O", "P3", "P", "P+3", "O", "O", "P+3", "P1", "O",
                "P1", "P+3", "P3", "P", "P+3", "P", "P1", "P1", "P+3", "P3", "P1", "P+3", "P", "P1", "P", "O", "P+3",
                "P+3", "P1", "P+3", "P3", "O", "P+3", "O", "P+3", "O", "P3" };

        Farmacia farmacia = new Farmacia(6);

        for (int i = 0; i < turnos.length; i++) {
            farmacia.agregarTurno(turnos[i]);
        }

        System.out.println("");

        farmacia.imprimirReporte();

        System.out.println("");

        // farmacia.simular();

        /*
        @formatter:off
        // Prueba con turnos Random, comentada para usar turnos predefinidos
        String[] codigosTurnos = new String[] { "O", "P", "P1", "P3", "P+3" };
        Random gen = new Random();
        for (int i = 0; i < 40; i++) {
            int idx = gen.nextInt(codigosTurnos.length);
            System.out.print("\"" + codigosTurnos[idx] + "\", ");
            farmacia.agregarTurno(codigosTurnos[idx]);
        }
        @formatter:on
        */

    }
}
