package metodos;

import apis.ColaPrioridadTDA;
import apis.ColaTDA;
import apis.ConjuntoTDA;
import impl.ColaLD;
import impl.ColaPrioridadLD;
import impl.ConjuntoLD;

public class Lib {
    public static void copiar(ColaTDA origen, ColaTDA destino) {
        ColaTDA aux = new ColaLD();
        aux.inicializarCola();
        pasar(origen, aux);

        while (!aux.colaVacia()) {
            origen.acolar(aux.primero());
            destino.acolar(aux.primero());
            aux.desacolar();
        }
    }

    public static void copiar(ColaPrioridadTDA origen, ColaPrioridadTDA destino) {
        ColaPrioridadTDA aux = new ColaPrioridadLD();
        while (!origen.colaVacia()) {
            int primero = origen.primero();
            int prioridad = origen.prioridad();
            destino.acolarPrioridad(primero, prioridad);
            aux.acolarPrioridad(primero, prioridad);
            origen.desacolar();
        }
        pasar(aux, origen);
    }

    public static void pasar(ColaTDA origen, ColaTDA destino) {
        while (!origen.colaVacia()) {
            destino.acolar(origen.primero());
            origen.desacolar();
        }
    }

    public static void pasar(ConjuntoTDA origen, ConjuntoTDA destino) {
        while (!origen.conjuntoVacio()) {
            int valor = origen.elegir();
            destino.agregar(valor);
            origen.sacar(valor);
        }
    }

    public static void pasar(ColaPrioridadTDA origen, ColaPrioridadTDA destino) {
        while (!origen.colaVacia()) {
            destino.acolarPrioridad(origen.primero(), origen.prioridad());
            origen.desacolar();
        }
    }

    public static int contar(ConjuntoTDA c) {
        ConjuntoTDA aux = new ConjuntoLD();
        int resultado = 0;
        while (!c.conjuntoVacio()) {
            int valor = c.elegir();
            aux.agregar(valor);
            c.sacar(valor);
            resultado += 1;
        }
        pasar(aux, c);

        return resultado;
    }

    public static int contar(ColaPrioridadTDA cp) {
        ColaPrioridadTDA aux = new ColaPrioridadLD();
        copiar(cp, aux);

        int resultado = 0;
        while (!aux.colaVacia()) {
            resultado++;
            aux.desacolar();
        }
        return resultado;
    }

    public static void imprimir(ColaPrioridadTDA p) {
        ColaPrioridadTDA aux = new ColaPrioridadLD();
        aux.inicializarCola();
        System.out.println("Referencia ColaPrioridad: <elem> (<prio>)");
        System.out.print("    ");
        while (!p.colaVacia()) {
            System.out.print(p.primero() + " (" + p.prioridad() + "), ");
            aux.acolarPrioridad(p.primero(), p.prioridad());
            p.desacolar();
        }

        System.out.print("\n");

        pasar(aux, p);
    }
}
