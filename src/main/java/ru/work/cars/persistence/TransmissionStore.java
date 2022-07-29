package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.Transmission;

import java.util.List;

@Repository
public class TransmissionStore implements Store {
    private final SessionFactory sf;

    public TransmissionStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Transmission> findAll() {
        return tx(session -> {
            List<Transmission> rsl = session.createQuery("select t from Transmission t").list();
            return rsl;
        }, sf);
    }

    public Transmission findById(int id){
        return tx(session -> session.get(Transmission.class, id), sf);
    }
}
