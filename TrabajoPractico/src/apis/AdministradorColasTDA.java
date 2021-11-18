package apis;

public interface AdministradorColasTDA {

    void inicializar(int cantidad);

    ColaPrioridadTDA programacion();

    int acolar(int tiempoEstimado);

    void desacolar();

    int primero();

    int estimado();

    int cantidadColas();

    int getPuestoProximoElemento();

    int getPuestoElemento(int id);

    DiccionarioSimpleTDA getElementos();
}
