package com.example.car.Repository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository<E> implements IRepository<E>{
    private ArrayList<E> entityList;

    public InMemoryRepository(){
        entityList = new ArrayList<>();
    }
    @Override
    public void addEntity(E newEntity) {
        entityList.add(newEntity);
    }

    @Override
    public ArrayList<E> getAllEntities() {
        return entityList;
    }
}
