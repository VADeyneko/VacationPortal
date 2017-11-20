package forms.core;

import javax.validation.ValidationException;

public interface Convertable<E> {

    E convertTo(Class<E> cls) throws ValidationException;
    
}
