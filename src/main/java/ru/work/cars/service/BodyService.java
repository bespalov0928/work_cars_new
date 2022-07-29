package ru.work.cars.service;

import org.springframework.stereotype.Service;
import ru.work.cars.model.Body;
import ru.work.cars.persistence.BodyStore;

import java.util.List;

@Service
public class BodyService {
    private final BodyStore bodyStore;

    public BodyService(BodyStore bodyStore) {
        this.bodyStore = bodyStore;
    }

    public List<Body> findAll(){
      return bodyStore.findAll();
    }

    public Body findById(int id){
        return bodyStore.findById(id);
    }

}
