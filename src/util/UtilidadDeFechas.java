package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UtilidadDeFechas {
    
    //El patrón utilizado para la conversión
    private static final String FECHA_PATTERN = "dd/MM/yyyy";
    
    //El formateador de fecha
    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern(FECHA_PATTERN);
    
    //Devuelve la fecha de entrada como un string formateado
    public static String formato(LocalDate fecha){
        
        if (fecha == null){
            return null;
        }
        return FECHA_FORMATTER.format(fecha);
        
    }
    
    //Convierte un string en un objeto de tipo LocalDate (o null si no puede convertirlo)
    public static LocalDate convertir(String fecha) {
        try {
            return FECHA_FORMATTER.parse(fecha, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    //Comprueba si un string de fecha es una fecha válida y devuelve 1 o 0
    //Usamos el método anterior para la comprobación
    public static boolean fechaValida(String fecha) {
        
        return UtilidadDeFechas.convertir(fecha) != null;
        
    }
}