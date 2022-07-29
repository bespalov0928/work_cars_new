package ru.work.cars.service;

import org.springframework.stereotype.Service;
import ru.work.cars.model.Mark;
import ru.work.cars.persistence.MarkStore;

import java.util.List;

@Service
public class MarkService {
    private final MarkStore markStore;

    public MarkService(MarkStore markStore) {
        this.markStore = markStore;
    }

    public List<Mark> findAll(){
        List<Mark> markList = markStore.findAll();
        return markList;
    }

    public Mark findById(int id){
        return markStore.findById(id);
    }
}
