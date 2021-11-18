package apis;

public interface AdministradorColasTDA {

    // Inicializa el administrador de colas para una cantidad de puestos dada
    void inicializar(int cantPuestos);

    // Agrega un item a la cola con menos demora estimada, devuelve un identificador
    // único para el ítem
    public abstract int acolar(int tiempoEstimado);

    public abstract void desacolar();

    public abstract ColaPrioridadTDA programacion();

    public abstract int cantColas();

    public abstract int primero();

    public abstract int estimado();

    public abstract int puestoProximoElem();

    public abstract int puestoDelElem(int idElemento);

    public abstract DiccionarioSimpleTDA elementos();

}
