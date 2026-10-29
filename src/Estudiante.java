/** La matrícula es la clave inmutable del ABB. */
public class Estudiante {
    private final int matricula;
    private String nombre;
    private double promedio;

    public Estudiante(int matricula, String nombre, double promedio) {
        if (matricula <= 0) throw new IllegalArgumentException("Matrícula debe ser positiva.");
        actualizar(nombre, promedio);
        this.matricula = matricula;
    }

    public int getMatricula() { return matricula; }
    public String getNombre() { return nombre; }
    public double getPromedio() { return promedio; }

    public void actualizar(String nombre, double promedio) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre no puede estar vacío.");
        if (!Double.isFinite(promedio) || promedio < 0 || promedio > 10)
            throw new IllegalArgumentException("Promedio debe estar entre 0 y 10.");
        this.nombre = nombre.trim();
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return matricula + " - " + nombre + " - promedio: " + String.format(java.util.Locale.US, "%.2f", promedio);
    }
}
