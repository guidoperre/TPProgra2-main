package test;

import metodos.Lib;
import apis.ColaPrioridadTDA;
import apis.DiccionarioSimpleTDA;
import apis.AdministradorColasTDA;
import implementacion.AdministradorCola;

public class TestAdministradorCola {
    public static void main(String[] args) {
        AdministradorColasTDA adm = new AdministradorCola();

        // Pruebas de inicializar
        adm.inicializar(5);
        Utils.assertEqual(adm.cantidadColas(), 5, "5 colas");

        adm.inicializar(10);
        Utils.assertEqual(adm.cantidadColas(), 10, "10 colas");

        adm.inicializar(4);
        Utils.assertEqual(adm.estimado(), 0, "Estimado 0 sin elementos");

        // Pruebas de acolar
        int id1 = adm.acolar(10);
        int id2 = adm.acolar(10);
        Utils.assertCondition(id1 != id2, "ID únicos");

        // Pruebas de estimado
        Utils.assertEqual(adm.estimado(), 0, "Estimado cero cuando hay colas vacías");

        adm.acolar(20);
        adm.acolar(5);
        Utils.assertEqual(adm.estimado(), 5, "Estimado mínimo cuando todas las colas tienen elementos");

        adm.acolar(10);
        Utils.assertEqual(adm.estimado(), 10, "Estimado mínimo luego de acolar mas elementos");

        // Pruebas de primero y desacolar
        int desacolado1 = adm.primero();
        Utils.assertEqual(desacolado1, id1, "Primer desacolado");

        adm.desacolar();
        Utils.assertEqual(adm.estimado(), 0, "Estimado 0 cuando se vacía una de las colas");

        int desacolado2 = adm.primero();
        Utils.assertEqual(desacolado2, id2, "Segundo desacolado");

        adm.inicializar(3);
        adm.acolar(1);
        adm.acolar(10);
        adm.acolar(100);
        int elem1 = adm.acolar(1);
        int elem2 = adm.acolar(1);

        Utils.assertEqual(adm.estimado(), 3, "Estimado mínimo 3");

        adm.desacolar();
        adm.desacolar();
        adm.desacolar();
        Utils.assertEqual(adm.estimado(), 0, "Estimado 0 con 3 colas, 1 vacía");

        Utils.assertEqual(adm.primero(), elem1, "Primero con ID correcto");

        adm.desacolar();
        Utils.assertEqual(adm.primero(), elem2, "Segundo con ID correcto");

        adm.desacolar();

        // Prueba de puestoDelElem
        adm.inicializar(3);
        elem1 = adm.acolar(3);
        Utils.assertEqual(adm.getPuestoElemento(elem1), 1, "Puesto del primer elemento");

        elem2 = adm.acolar(2);
        Utils.assertEqual(adm.getPuestoElemento(elem2), 2, "Puesto del segundo elemento");

        int elem3 = adm.acolar(3);
        Utils.assertEqual(adm.getPuestoElemento(elem3), 3, "Puesto del tercer elemento");

        int elem4 = adm.acolar(3);
        Utils.assertEqual(adm.getPuestoElemento(elem4), 2, "Puesto del cuarto elemento");

        // Prueba de puestoProximoElem
        Utils.assertEqual(adm.getPuestoProximoElemento(), 1, "Proximo elemento");
        adm.desacolar();

        Utils.assertEqual(adm.getPuestoProximoElemento(), 2, "Proximo elemento 2");

        adm.desacolar();
        adm.desacolar();

        Utils.assertEqual(adm.getPuestoProximoElemento(), 2, "Proximo elemento 3");

        // Prueba de elementos
        adm.inicializar(3);

        DiccionarioSimpleTDA elementos = adm.getElementos();
        int cantElementos = Lib.contar(elementos.claves());
        Utils.assertEqual(cantElementos, 0, "Cant. de elementos 0");

        for (int i = 0; i < 10; i++) {
            adm.acolar(i + 1);
        }

        elementos = adm.getElementos();
        cantElementos = Lib.contar(elementos.claves());
        Utils.assertEqual(cantElementos, 10, "Cant. de elementos 10");

        Utils.assertEqual(elementos.recuperar(1), 0, "Demora elemento 1");
        Utils.assertEqual(elementos.recuperar(2), 0, "Demora elemento 2");
        Utils.assertEqual(elementos.recuperar(3), 0, "Demora elemento 3");
        Utils.assertEqual(elementos.recuperar(4), 1, "Demora elemento 4");
        Utils.assertEqual(elementos.recuperar(5), 2, "Demora elemento 5");
        Utils.assertEqual(elementos.recuperar(10), 12, "Demora elemento 10");

        // Pruebas de programacion
        ColaPrioridadTDA programacion = adm.programacion();

        cantElementos = Lib.contar(programacion);
        Utils.assertEqual(cantElementos, 10, "Cant. elementos");

        Utils.assertEqual(programacion.primero(), 1, "Programacion primero");
        Utils.assertEqual(programacion.prioridad(), 0, "Programación primero prio");
        programacion.desacolar();

        Utils.assertEqual(programacion.primero(), 2, "Programacion segundo");
        Utils.assertEqual(programacion.prioridad(), 0, "Programamacion segundo prio");
        programacion.desacolar();

        programacion.desacolar();

        Utils.assertEqual(programacion.primero(), 4, "Programacion cuarto");
        Utils.assertEqual(programacion.prioridad(), -1, "Programacio cuarto prio");

        Lib.imprimir(programacion);

        System.out.println("Todas las pruebas correctas");

    }
}
