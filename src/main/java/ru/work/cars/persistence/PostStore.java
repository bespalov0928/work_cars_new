package ru.work.cars.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.*;

import java.util.List;

@Repository
public class PostStore implements Store {
    private final SessionFactory sf;

    public PostStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Post> findAll() {
        return tx(session -> {
            List<Post> rsl = session.createQuery("select p from Post p").list();
            return rsl;
        }, sf);
    }

    public Post savePost(Post post) {
        return tx(session -> {
            session.save(post);
            return post;
        }, sf);
    }

    public Post savePost(Post post, User user, Mark mark, Body body, Transmission transmission, Engine engine) {
        Session session = sf.openSession();
        session.beginTransaction();
        post.setUser(user);
        post.setMark(mark);
        post.setBody(body);
        post.setTransmission(transmission);
        post.setEngine(engine);
        session.save(post);
        session.getTransaction().commit();
        return post;
    }

    public Post findById(int id) {
        return tx(session -> {
            return (Post) session.createQuery("select p from Post p join fetch p.photos where p.id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
        }, sf);
    }

    public void postSale(int id) {
        tx(session -> session.createQuery("update Post p set p.sale=:sale where p.id=:id")
                .setParameter("sale", true)
                .setParameter("id", id)
                .executeUpdate(), sf);
    }

    public Post postUpdate(Post post) {
        return tx(session -> {
            session.update(post);
            return post;
        }, sf);
    }

}
