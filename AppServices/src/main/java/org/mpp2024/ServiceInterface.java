package org.mpp2024;


import java.util.Optional;

public interface ServiceInterface <ID, E extends Entity<ID>>{
    Optional<E> add(E e);

    Optional<E> update(E e);

    Optional<E> delete(E e);

    Optional<E> find(ID id);

    Iterable<E> getAll();
}
