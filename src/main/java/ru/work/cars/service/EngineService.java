package ru.work.cars.service;

import org.springframework.stereotype.Service;
import ru.work.cars.model.Engine;
import ru.work.cars.persistence.EngineStore;

import java.util.List;

@Service
public class EngineService {
    private final EngineStore engineStore;

    public EngineService(EngineStore engineStore) {
        this.engineStore = engineStore;
    }

    public List<Engine> findAll() {
        return engineStore.findAll();
    }

    public Engine findById(int id){
        return engineStore.findById(id);
    }

}
