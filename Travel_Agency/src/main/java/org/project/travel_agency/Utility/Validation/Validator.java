package org.project.travel_agency.Utility.Validation;

public interface Validator<E> {

    /**
     * @param entity
     * @throws ValidException
     */
    void validate(E entity) throws ValidException;

}
