package org.project.travel_agency.Service;

import org.project.travel_agency.Domain.Entity;

import java.util.Optional;

public interface ServiceInterface <ID, E extends Entity<ID>>{
    Optional<E> add(E e);
    Optional<E> update(E e);
    Optional<E> delete(E e);
    Optional<E> find(ID id);
    Iterable<E> getAll();

}
