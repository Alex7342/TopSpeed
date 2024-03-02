package com.example.car.Repository;

import java.util.ArrayList;
import java.util.List;

public interface IRepository<E> {
    void addEntity(E newEntity);

    ArrayList<E> getAllEntities();
}
