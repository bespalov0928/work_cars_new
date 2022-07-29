package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.Body;

import java.util.List;

@Repository
public class BodyStore implements Store {
    private final SessionFactory sf;

    public BodyStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Body> findAll() {
        return tx(session -> {
            List<Body> rsl = session.createQuery("select b from Body b").list();
            return rsl;
        }, sf);
    }

    public Body findById(int id){
        return tx(session -> session.get(Body.class, id),sf);
    }
}
