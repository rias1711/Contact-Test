package com.codegym.service;

public interface GenericService<T> {
    Iterable<T> findAll();

    void save(T t);

    T findById(Long id);

    void remove(Long id);
}
