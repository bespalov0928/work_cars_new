package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.User;

@Component
public class UserStore implements Store {
    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    public User save(User user) {
        return tx(session -> {
            session.save(user);
            return user;
        }, sf);
    }

    public User findById(int id) {
        return (User) tx(session -> session.createQuery("select u from User u where u.id=:id")
                .setParameter("id", id)
                .uniqueResult(), sf);
    }

    public User findByNamePass(String username, String pass) {
        return (User) tx(session -> session.createQuery("select u from User u where u.username=:name and u.password=:pass")
                .setParameter("name", username)
                .setParameter("pass", pass)
                .uniqueResult(), sf);
    }

   public User findByName(String username) {
        return (User) tx(session -> session.createQuery("select u from User u where u.username=:name")
                .setParameter("name", username)
                .uniqueResult(), sf);
    }
}
