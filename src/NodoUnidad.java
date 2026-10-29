import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Nodo de árbol general: cada unidad puede tener varios hijos. */
public class NodoUnidad {
    private final String codigo;
    private final ArrayList<NodoUnidad> hijos = new ArrayList<>();

    public NodoUnidad(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<NodoUnidad> getHijos() {
        return Collections.unmodifiableList(hijos);
    }

    void agregarHijo(NodoUnidad hijo) {
        hijos.add(hijo);
    }

    void quitarHijo(NodoUnidad hijo) {
        hijos.remove(hijo);
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }
}
