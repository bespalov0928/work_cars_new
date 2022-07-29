package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.Mark;

import java.util.List;

@Repository
public class MarkStore implements Store {

    private final SessionFactory sf;

    public MarkStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Mark> findAll() {
        return tx(session -> {
            List<Mark> rsl = session.createQuery("select m from Mark m").list();
            return rsl;
        }, sf);
    }

    public Mark findById(int id){
        return tx(session -> session.get(Mark.class, id), sf);
    }
}
