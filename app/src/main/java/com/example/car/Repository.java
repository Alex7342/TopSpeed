package com.example.car;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private ArrayList<Result> resultList;

    public Repository() {
        this.resultList = new ArrayList<Result>();
    }

    public ArrayList<Result> getResultList() {
        return this.resultList;
    }

    public void addResult(Result newResult) {
        this.resultList.add(newResult);
    }
}