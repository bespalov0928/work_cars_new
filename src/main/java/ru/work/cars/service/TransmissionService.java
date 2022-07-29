package ru.work.cars.service;

import org.springframework.stereotype.Service;
import ru.work.cars.model.Transmission;
import ru.work.cars.persistence.TransmissionStore;

import java.util.List;

@Service
public class TransmissionService {
    private final TransmissionStore transmissionStore;

    public TransmissionService(TransmissionStore transmissionStore) {
        this.transmissionStore = transmissionStore;
    }

    public List<Transmission> findAll(){
        return transmissionStore.findAll();
    }

    public Transmission findById(int id){
        return transmissionStore.findById(id);
    }

}
