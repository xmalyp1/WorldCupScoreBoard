package org.example.maly.service.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

abstract class AbstractRepository<T> {
    private Collection<T> items;

    AbstractRepository(Collection<T> items){
        this.items = items;
    }

    public Collection<T> getItems(){
        return Collections.unmodifiableCollection(items);
    }

    public boolean addItem(T item){
        return items.add(item);
    }

    public boolean removeItem(T item){
        return items.remove(item);
    }

    public Optional<T> findItem(Predicate<T> predicate){
        return getItems().stream().filter(predicate).findAny();
    }

}
