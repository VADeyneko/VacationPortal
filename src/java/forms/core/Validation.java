package forms.core;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.validation.ValidationException;

public final class Validation {
    
    private static final ResourceBundle ERRORS; 
    
    private static final Pattern EMAIL;
    
    private static final Pattern NOT_EMPTY;
    
    static {
        ERRORS = ResourceBundle.getBundle("resources/errors");
        EMAIL = Pattern.compile("\\w+([\\.-_]\\w+)*@\\w{2,}\\.\\w{2,}");
        NOT_EMPTY = Pattern.compile("\\s*\\S.*");
    }
    
    private Validation() {}
    
    public static void assertEmail(String text) throws ValidationException {
        if (!EMAIL.matcher(text).matches()) {
            throw new ValidationException(ERRORS.getString("error.validation.email"));
        }
    }
    
    public static void assertNotEmpty(String text) {
        if (text.trim().isEmpty()) {
            throw new ValidationException(ERRORS.getString("error.validation.required"));
        }
    }
    
    public static void assertDate(String textDate) throws ValidationException {
        try{
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date utilDate = formatter.parse(textDate);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());      
        }
        catch (IllegalArgumentException | ParseException e ){
            throw new ValidationException(ERRORS.getString("error.validation.wrong-date"));
        }
    }

}
