/** Representación de un árbol general con hijo izquierdo/hermano derecho. */
public class NodoBinarioUnidad {
    private final String codigo;
    private NodoBinarioUnidad izquierdo; // Primer hijo del nodo general.
    private NodoBinarioUnidad derecho;   // Siguiente hermano del nodo general.

    public NodoBinarioUnidad(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() { 
        return codigo; 
    }
    public NodoBinarioUnidad getIzquierdo() { 
        return izquierdo; 
    }
    public NodoBinarioUnidad getDerecho() {
         return derecho; 
        }
    void setIzquierdo(NodoBinarioUnidad izquierdo) { 
        this.izquierdo = izquierdo; 
    }
    void setDerecho(NodoBinarioUnidad derecho) { 
        this.derecho = derecho; 
    }
}
