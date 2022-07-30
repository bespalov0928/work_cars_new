package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.Post;

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

    public Post findById(int id) {
        return tx(session -> session.get(Post.class, id), sf);
    }
}
