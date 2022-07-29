package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.Engine;

import java.util.List;
@Repository
public class EngineStore implements Store {
    private final SessionFactory sf;

    public EngineStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Engine> findAll() {
        return tx(session -> {
            List<Engine> rsl = session.createQuery("select e from Engine e").list();
            return rsl;
        }, sf);
    }

    public Engine findById(int id){
        return tx(session -> session.get(Engine.class, id), sf);
    }
}
