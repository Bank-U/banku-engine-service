package com.banku.engineservice.repository;

public interface AggregateRepository<T, ID> {
    T findById(ID id);
} 