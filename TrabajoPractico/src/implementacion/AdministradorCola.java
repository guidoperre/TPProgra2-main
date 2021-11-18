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

    DiccionarioSimpleTDA demoraPuesto;
    DiccionarioSimpleTDA demoraItem;
    DiccionarioSimpleTDA puesto;

    ColaTDA[] colas;

    int proximaCola;
    int cantidadPuestos;
    int ultimoId;

    public void inicializar(int cantidadPuestos) {
        demoraPuesto = new DicSimpleL();
        demoraPuesto.inicializarDiccionario();

        demoraItem = new DicSimpleL();
        demoraItem.inicializarDiccionario();

        puesto = new DicSimpleL();
        puesto.inicializarDiccionario();

        ultimoId = 0;
        proximaCola = 0;
        colas = new ColaTDA[cantidadPuestos];
        this.cantidadPuestos = cantidadPuestos;

        for (int i=0; i<cantidadPuestos; i++) {
            demoraPuesto.agregar(i, 0);
            colas[i] = new ColaLD();
            colas[i].inicializarCola();
        }
    }

    public ColaPrioridadTDA programacion() {
        ColaPrioridadTDA p = new ColaPrioridadLD();
        p.inicializarCola();
        ColaTDA aux;
        int item;
        int demoraTotal;
        int demoraAux;

        for (int i=0; i<cantidadPuestos; i++) {
            aux = new ColaLD();
            aux.inicializarCola();
            demoraTotal = 0;
            demoraAux = 0;

            Lib.copiar(colas[i], aux);
            while (!aux.colaVacia()) {
                item = aux.primero();
                demoraTotal += demoraAux;
                p.acolarPrioridad(item, -demoraTotal);

                demoraAux = demoraItem.recuperar(item);
                aux.desacolar();
            }
        }
        return p;
    }

    public int acolar(int estimado) {
        int cola = buscarColaMenorDemora();
        int aux = demoraPuesto.recuperar(cola) + estimado;
        demoraPuesto.agregar(cola, aux);

        int id = ++ultimoId;

        colas[cola].acolar(id);
        demoraItem.agregar(id, estimado);
        puesto.agregar(id, cola);

        return id;
    }

    public void desacolar() {
        int total = demoraPuesto.recuperar(proximaCola);
        int primero = demoraItem.recuperar(primero());
        demoraPuesto.agregar(proximaCola, total - primero);

        colas[proximaCola].desacolar();
        int aux = (proximaCola + 1) % cantidadPuestos;

        while (colas[aux].colaVacia() && aux != proximaCola) {
            aux = (aux + 1) % cantidadPuestos;
        }

        proximaCola = aux;
    }

    public int primero() {
        return colas[proximaCola].primero();
    }

    public int estimado() {
        int cola = buscarColaMenorDemora();
        return demoraPuesto.recuperar(cola);
    }

    public int cantidadColas() {
        return cantidadPuestos;
    }

    public int getPuestoProximoElemento() {
        return getPuestoElemento(primero());
    }

    public int getPuestoElemento(int idElemento) {
        return puesto.recuperar(idElemento) + 1;
    }

    public DiccionarioSimpleTDA getElementos() {
        DiccionarioSimpleTDA e = new DicSimpleA();
        e.inicializarDiccionario();
        ColaTDA aux;
        int item;
        int demoraTotal;
        int demoraAux;

        for (int i=0; i<cantidadPuestos; i++) {
            aux = new ColaLD();
            aux.inicializarCola();
            demoraTotal = 0;
            demoraAux = 0;

            Lib.copiar(colas[i], aux);
            while (!aux.colaVacia()) {
                item = aux.primero();
                demoraTotal += demoraAux;
                e.agregar(item, demoraTotal);

                demoraAux = demoraItem.recuperar(item);
                aux.desacolar();
            }
        }
        return e;
    }

    private int buscarColaMenorDemora() {
        int min = demoraPuesto.recuperar(0);
        int id = 0;
        for (int i = 1; i < cantidadPuestos; i++) {
            int valor = demoraPuesto.recuperar(i);
            if (valor < min) {
                min = valor;
                id = i;
            }
        }
        return id;
    }
}
