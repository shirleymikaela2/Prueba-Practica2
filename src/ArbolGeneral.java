import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Árbol académico con validaciones de códigos, padre e integridad. */
public class ArbolGeneral {
    private NodoUnidad raiz;

    private String normalizar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return null;
        return codigo.trim().toUpperCase(Locale.ROOT);
    }

    /** El único código aceptado como raíz del caso es UTA. */
    public boolean crearRaiz(String codigo) {
        String limpio = normalizar(codigo);
        if (raiz != null || !"UTA".equals(limpio)) return false;
        raiz = new NodoUnidad(limpio);
        return true;
    }

    public NodoUnidad buscar(String codigo) {
        String limpio = normalizar(codigo);
        if (limpio == null) return null;
        return buscarRec(raiz, limpio);
    }

    private NodoUnidad buscarRec(NodoUnidad actual, String codigo) {
        if (actual == null) return null;
        if (actual.getCodigo().equals(codigo)) return actual;
        for (NodoUnidad hijo : actual.getHijos()) {
            NodoUnidad encontrado = buscarRec(hijo, codigo);
            if (encontrado != null) return encontrado;
        }
        return null;
    }

    /** Solo permite nodos nuevos: no hay reasignación de padres ni ciclos. */
    public boolean agregar(String codigoPadre, String nuevoCodigo) {
        String codigo = normalizar(nuevoCodigo);
        if (codigo == null || buscar(codigo) != null) return false;
        NodoUnidad padre = buscar(codigoPadre);
        if (padre == null) return false;
        padre.agregarHijo(new NodoUnidad(codigo));
        return true;
    }

    /** Nivel: raíz 0, descendientes +1; -1 si no existe. */
    public int nivel(String codigo) {
        String limpio = normalizar(codigo);
        if (limpio == null) return -1;
        return nivelRec(raiz, limpio, 0);
    }

    private int nivelRec(NodoUnidad actual, String codigo, int profundidad) {
        if (actual == null) return -1;
        if (actual.getCodigo().equals(codigo)) return profundidad;
        for (NodoUnidad hijo : actual.getHijos()) {
            int resultado = nivelRec(hijo, codigo, profundidad + 1);
            if (resultado != -1) return resultado;
        }
        return -1;
    }

    public int grado(String codigo) {
        NodoUnidad nodo = buscar(codigo);
        return nodo == null ? -1 : nodo.getHijos().size();
    }

    /** Altura medida en aristas: vacío -1, solo raíz 0. */
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoUnidad actual) {
        if (actual == null) return -1;
        int mayor = -1;
        for (NodoUnidad hijo : actual.getHijos()) {
            mayor = Math.max(mayor, alturaRec(hijo));
        }
        return 1 + mayor;
    }

    public int totalNodos() {
        return contarNodosRec(raiz);
    }

    private int contarNodosRec(NodoUnidad actual) {
        if (actual == null) return 0;
        int total = 1;
        for (NodoUnidad hijo : actual.getHijos()) total += contarNodosRec(hijo);
        return total;
    }

    public int totalHojas() {
        return contarHojasRec(raiz);
    }

    private int contarHojasRec(NodoUnidad actual) {
        if (actual == null) return 0;
        if (actual.esHoja()) return 1;
        int total = 0;
        for (NodoUnidad hijo : actual.getHijos()) total += contarHojasRec(hijo);
        return total;
    }

    public List<String> listarHojas() {
        ArrayList<String> resultado = new ArrayList<>();
        listarHojasRec(raiz, resultado);
        return resultado;
    }

    private void listarHojasRec(NodoUnidad actual, List<String> salida) {
        if (actual == null) return;
        if (actual.esHoja()) salida.add(actual.getCodigo());
        for (NodoUnidad hijo : actual.getHijos()) listarHojasRec(hijo, salida);
    }

    /** Rechaza la raíz, nodos inexistentes y unidades con descendientes. */
    public boolean eliminar(String codigo) {
        String limpio = normalizar(codigo);
        if (raiz == null || limpio == null || limpio.equals("UTA")) return false;
        return eliminarRec(raiz, limpio);
    }

    private boolean eliminarRec(NodoUnidad padre, String codigo) {
        for (NodoUnidad hijo : padre.getHijos()) {
            if (hijo.getCodigo().equals(codigo)) {
                if (!hijo.esHoja()) return false;
                padre.quitarHijo(hijo);
                return true;
            }
            if (eliminarRec(hijo, codigo)) return true;
        }
        return false;
    }

    public String imprimirJerarquia() {
        StringBuilder salida = new StringBuilder();
        if (raiz == null) return "(árbol general vacío)\n";
        imprimirRec(raiz, 0, salida);
        return salida.toString();
    }

    private void imprimirRec(NodoUnidad actual, int nivel, StringBuilder salida) {
        salida.append("  ".repeat(nivel)).append("- ").append(actual.getCodigo()).append('\n');
        for (NodoUnidad hijo : actual.getHijos()) imprimirRec(hijo, nivel + 1, salida);
    }

    public NodoBinarioUnidad convertirABinario() {
        return convertirRec(raiz);
    }

    private NodoBinarioUnidad convertirRec(NodoUnidad original) {
        if (original == null) return null;
        NodoBinarioUnidad resultado = new NodoBinarioUnidad(original.getCodigo());
        NodoBinarioUnidad anterior = null;
        for (NodoUnidad hijo : original.getHijos()) {
            NodoBinarioUnidad convertido = convertirRec(hijo);
            if (anterior == null) resultado.setIzquierdo(convertido);
            else anterior.setDerecho(convertido);
            anterior = convertido;
        }
        return resultado;
    }

    /** Lista las relaciones; HI = primer hijo y HD = siguiente hermano. */
    public String imprimirBinario() {
        NodoBinarioUnidad convertido = convertirABinario();
        if (convertido == null) return "(árbol binario vacío)\n";
        StringBuilder salida = new StringBuilder();
        imprimirBinarioRec(convertido, salida);
        return salida.toString();
    }

    private void imprimirBinarioRec(NodoBinarioUnidad nodo, StringBuilder salida) {
        if (nodo == null) return;
        salida.append(nodo.getCodigo())
              .append(" | HI: ").append(nodo.getIzquierdo() == null ? "-" : nodo.getIzquierdo().getCodigo())
              .append(" | HD: ").append(nodo.getDerecho() == null ? "-" : nodo.getDerecho().getCodigo())
              .append('\n');
        imprimirBinarioRec(nodo.getIzquierdo(), salida);
        imprimirBinarioRec(nodo.getDerecho(), salida);
    }
}
