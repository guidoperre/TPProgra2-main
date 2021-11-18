package implementacion;

import apis.AdministradorColasTDA;
import apis.ColaPrioridadTDA;
import apis.DiccionarioSimpleTDA;

import java.util.Map;

import static java.util.Map.entry;

public class Farmacia {

    // @formatter:off
    // Diccionario de demoras por tipo de turno
    private static Map<String, Integer> tiemposTurnos = Map.ofEntries(
        entry("O",   30),
        entry("P",   20),
        entry("P1",  10),
        entry("P3",  30), 
        entry("P+3", 50)
    );
    // @formatter:on

    private AdministradorColasTDA admin;

    public Farmacia(int cantPuestos) {
        admin = new AdministradorCola();
        admin.inicializar(cantPuestos);
    }

    public int agregarTurno(String tipo) {
        // Agrega un turno en la farmacia según el código de tipo de turno, y devuelve
        // el ID. Siempre que el tipo sea un tipo de turno válido
        int id = admin.acolar(tiemposTurnos.get(tipo));
        return id;
    }

    public void imprimirReporte() {
        // Imprime el reporte de la programación de turnos para la jornada

        DiccionarioSimpleTDA elementos = admin.elementos();

        ColaPrioridadTDA programacion = admin.programacion();

        System.out.println("");
        System.out.printf("%-20s %-20s %-20s\n", "ID", "Demora", "Puesto");
        while (!programacion.colaVacia()) {
            int id = programacion.primero();
            int demora = elementos.recuperar(id);
            int puesto = admin.puestoDelElem(id);

            int demoraHoras = demora / 60;
            int demoraMinutos = demora % 60;

            System.out.printf("%-20s %-20s %-20s\n", id, String.format("%02d:%02dhs", demoraHoras, demoraMinutos),
                    puesto);

            programacion.desacolar();
        }
    }

    public void simular() {
        DiccionarioSimpleTDA elementos = admin.elementos();
        ColaPrioridadTDA programacion = admin.programacion();

        System.out.println("");
        System.out.println("Iniciando simulación | Escala de tiempo: 1 segundo = 1 minuto");
        System.out.println("");

        boolean enEspera = false;
        int tiempo = 0;

        while (!programacion.colaVacia()) {
            int id = programacion.primero();
            int demora = elementos.recuperar(id);
            int puesto = admin.puestoDelElem(id);

            if (tiempo == demora) {
                if (enEspera) {
                    System.out.println("");
                }
                enEspera = false;
                System.out.printf("%02d:%02dhs: ", tiempo / 60, tiempo % 60);
                System.out.printf("Atendiendo el turno %s en el puesto %s\n", id, puesto);
                programacion.desacolar();
            } else if (tiempo < demora) {
                tiempo++;
                if (!enEspera) {
                    enEspera = true;
                    System.out.print("Todos los puestos ocupados, en espera");
                }
                System.out.print(".");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("Error de interrupción");
                }
            }

        }
        System.out.println("Se han atendido todos los turnos, la simulacion ha finalizado");
    }

}
