package test;

public class Utils {

    /*
     * Clase para facilitar las pruebas automáticas.
     * 
     * Tiene un método assertEqual que verifica que dos valores sean iguales y
     * levanta una excepción si no es así.
     */

    public static class TestFailedException extends RuntimeException {
        public TestFailedException(String errorMessage) {
            super(errorMessage);
        }

    }

    public static void assertEqual(int value, int expected, String description) {
        if (value != expected) {
            throw new TestFailedException("FALLÓ " + description + ". Esperado=" + expected + ", recibido=" + value);
        } else {
            System.out.println("CORRECTO: " + description);
        }
    }

    public static void assertCondition(boolean condition, String description) {
        if (!condition) {
            throw new TestFailedException("FALLÓ " + description + ". Esperado=true, recibido=" + condition);
        } else {
            System.out.println("CORRECTO: " + description);
        }
    }
}
