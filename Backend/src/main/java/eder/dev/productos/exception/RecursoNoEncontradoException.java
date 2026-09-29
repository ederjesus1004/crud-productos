package eder.dev.productos.exception;
// Excepción propia para cuando no encontramos algo en la base de datos.
// Extiende RuntimeException para no tener que declararla con "throws".

public class RecursoNoEncontradoException extends RuntimeException{
    public RecursoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
