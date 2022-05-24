package org.example.maly.service.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

abstract class AbstractRepository<T> {
    private Collection<T> items;

    AbstractRepository(Collection<T> items){
        this.items = items;
    }

    public Collection<T> getItems(){
        return items;
    }

    public boolean addItem(T item){
        if(item == null)
            return false;
        return items.add(item);
    }

}
