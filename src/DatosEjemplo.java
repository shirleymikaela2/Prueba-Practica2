/** Carga exclusivamente los datos estructurales exigidos por la guía.
 *  Nombres y promedios son datos ilustrativos porque la guía no los especifica.
 */
public class DatosEjemplo {
    public static final int[] MATRICULAS = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65, 75, 85};

    public static ArbolGeneral crearGeneral() {
        ArbolGeneral a = new ArbolGeneral();
        a.crearRaiz("UTA");
        a.agregar("UTA", "FISEI");
        a.agregar("UTA", "FCHE");
        a.agregar("UTA", "FCS");
        a.agregar("FISEI", "SOFTWARE");
        a.agregar("FISEI", "TI");
        a.agregar("SOFTWARE", "3A");
        a.agregar("SOFTWARE", "3B");
        a.agregar("TI", "4A");
        return a;
    }

    public static ABB crearABB() {
        ABB a = new ABB();
        for (int i = 0; i < MATRICULAS.length; i++) {
            int matricula = MATRICULAS[i];
            double promedioEjemplo = 7.0 + 0.5 * (i % 4);
            a.insertar(matricula, "Estudiante " + matricula, promedioEjemplo);
        }
        return a;
    }
}
