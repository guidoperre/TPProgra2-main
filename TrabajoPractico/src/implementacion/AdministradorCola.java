package implementacion;

import apis.AdministradorColasTDA;
import apis.ColaPrioridadTDA;
import apis.ColaTDA;
import apis.DiccionarioSimpleTDA;
import impl.ColaLD;
import impl.ColaPrioridadLD;
import impl.DicSimpleA;
import impl.DicSimpleL;
import metodos.Lib;

public class AdministradorCola implements AdministradorColasTDA {
    int cantPuestos;
    int lastID;
    DiccionarioSimpleTDA demorasPorPuesto;
    DiccionarioSimpleTDA demorasPorItem;
    DiccionarioSimpleTDA puestoPorItem;

    ColaTDA colas[];

    // Índice del round-robin para desacolar
    int siguienteCola;

    // Inicializa el administrador de colas para una cantidad de puestos dada
    public void inicializar(int cantPuestos) {
        this.cantPuestos = cantPuestos;
        lastID = 0;

        colas = new ColaTDA[cantPuestos];

        demorasPorPuesto = new DicSimpleL();
        demorasPorPuesto.inicializarDiccionario();

        // Internamente los puestos van de 0 a n-1
        for (int i = 0; i < cantPuestos; i++) {
            demorasPorPuesto.agregar(i, 0);
            colas[i] = new ColaLD();
            colas[i].inicializarCola();
        }

        siguienteCola = 0;

        demorasPorItem = new DicSimpleL();
        demorasPorItem.inicializarDiccionario();

        puestoPorItem = new DicSimpleL();
        puestoPorItem.inicializarDiccionario();
    }

    // Devuelve el ID del puesto con menor demora
    private int hallarColaMenorDemora() {
        int minValor = demorasPorPuesto.recuperar(0);
        int minID = 0;
        for (int i = 1; i < cantPuestos; i++) {
            int valor = demorasPorPuesto.recuperar(i);
            if (valor < minValor) {
                minValor = valor;
                minID = i;
            }
        }

        return minID;
    }

    // Agrega un item a la cola con menos demora estimada, devuelve un identificador
    // único para el ítem
    public int acolar(int estimado) {
        int cola = hallarColaMenorDemora();
        int nuevoEstimado = demorasPorPuesto.recuperar(cola) + estimado;
        demorasPorPuesto.agregar(cola, nuevoEstimado);

        int id = ++lastID;

        colas[cola].acolar(id);
        demorasPorItem.agregar(id, estimado);
        puestoPorItem.agregar(id, cola);

        return id;
    }

    // Desacola el próximo elemento a ser atendido
    // El desacolado es round-robin
    public void desacolar() {
        int demoraTotal = demorasPorPuesto.recuperar(siguienteCola);
        int demoraPrimero = demorasPorItem.recuperar(primero());
        demorasPorPuesto.agregar(siguienteCola, demoraTotal - demoraPrimero);

        colas[siguienteCola].desacolar();

        int siguiente = (siguienteCola + 1) % cantPuestos;
        while (colas[siguiente].colaVacia() && siguiente != siguienteCola) {
            siguiente = (siguiente + 1) % cantPuestos;
        }

        siguienteCola = siguiente;
    }

    // Devuelve una cola prioridad donde la prioridad es el tiempo estimado de
    // atención de los elementos a partir de la apertura de los puestos
    public ColaPrioridadTDA programacion() {
        ColaPrioridadTDA progra = new ColaPrioridadLD();
        progra.inicializarCola();

        ColaTDA aux;
        int demoraAcumulada;
        int demoraAnterior;
        int elemActual;
        for (int i = 0; i < cantPuestos; i++) {
            aux = new ColaLD();
            aux.inicializarCola();
            Lib.copiar(colas[i], aux);
            demoraAcumulada = 0;
            demoraAnterior = 0;
            while (!aux.colaVacia()) {
                elemActual = aux.primero();
                demoraAcumulada += demoraAnterior;
                progra.acolarPrioridad(elemActual, -demoraAcumulada);

                demoraAnterior = demorasPorItem.recuperar(elemActual);
                aux.desacolar();
            }
        }
        return progra;
    }

    // Devuelve la cantidad de colas / puestos del administrador
    public int cantColas() {
        return cantPuestos;
    }

    // Devuelve el identificador del próximo elemento a ser atendido
    public int primero() {
        return colas[siguienteCola].primero();
    }

    // Devuelve el tiempo estimado en que será llamado el próximo elemento desde la
    // apertura de los puestos (??)
    //
    // Se asume que se espera devolver el tiempo estimado para el próximo item a
    // agregar, ya que la demora del próximo item "a procesar" según lo que devuelve
    // el método "primero" siempre sería cero.
    public int estimado() {
        int cola = hallarColaMenorDemora();
        return demorasPorPuesto.recuperar(cola);
    }

    // Devuelve el puesto del elemento primero()
    public int puestoProximoElem() {
        return puestoDelElem(primero());
    }

    // Devuelve el puesto correspondiente a un elemento dado
    public int puestoDelElem(int idElemento) {
        return puestoPorItem.recuperar(idElemento) + 1;
    }

    // Devuelve un diccionario con los elementos encolados y la demora estimada de
    // atención
    //
    // Se asume que se pide la demora total desde la apertura de puestos y no la
    // demora individual para cada elemento.
    //
    // Es decir que si hay 1 solo puesto y se acola id=1 con demora 10 e id=2 con
    // demora=15, devolverá:
    // {1: 0, 2: 10}
    public DiccionarioSimpleTDA elementos() {
        DiccionarioSimpleTDA elementos = new DicSimpleA();
        elementos.inicializarDiccionario();

        ColaTDA aux;
        int demoraAcumulada;
        int demoraAnterior;
        int elemActual;
        for (int i = 0; i < cantPuestos; i++) {
            aux = new ColaLD();
            aux.inicializarCola();
            Lib.copiar(colas[i], aux);
            demoraAcumulada = 0;
            demoraAnterior = 0;
            while (!aux.colaVacia()) {
                elemActual = aux.primero();
                demoraAcumulada += demoraAnterior;
                elementos.agregar(elemActual, demoraAcumulada);

                demoraAnterior = demorasPorItem.recuperar(elemActual);
                aux.desacolar();
            }
        }

        return elementos;
    }
}
