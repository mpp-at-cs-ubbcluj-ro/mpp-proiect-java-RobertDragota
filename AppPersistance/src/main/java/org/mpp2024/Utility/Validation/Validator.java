package org.mpp2024.Utility.Validation;

public interface Validator<E> {

    /**
     * @param entity
     * @throws ValidException
     */
    void validate(E entity) throws ValidException;

}
