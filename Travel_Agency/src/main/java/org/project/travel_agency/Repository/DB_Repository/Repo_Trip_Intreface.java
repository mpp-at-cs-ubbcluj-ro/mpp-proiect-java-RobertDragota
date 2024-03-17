package org.project.travel_agency.Repository.DB_Repository;

import org.project.travel_agency.Domain.Entity;

import java.util.Optional;

public interface Repo_Trip_Intreface<ID,E extends Entity<ID>> {
    Optional<E> add(E e);
    Optional<E> update(E e);
    Optional<E> delete(E e);
    Optional<E> find(ID id);
    Iterable<E> getAll();
}
