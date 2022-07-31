package ru.work.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.work.cars.model.Photo;

import java.util.List;

@Repository
public class PhotoStore implements Store {
    public final SessionFactory sf;

    public PhotoStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Photo> findAll() {
        return tx(session -> session.createQuery("select p from Photo p").list(), sf);
    }

    public Photo findById(int id) {
        return tx(session -> session.get(Photo.class, id), sf);
    }

    public Photo savePhoto(Photo photo) {
        return tx(session -> {
            session.save(photo);
            return photo;
        }, sf);
    }

    public Photo updatePhoto(Photo photo) {
        return tx(session -> {
            session.update(photo);
            return photo;
        }, sf);
    }

    public boolean delPhoto(int idPost) {
        return tx(session -> {
            int index = session.createQuery("delete from Photo p where  p.post.id=:idPost")
                    .setParameter("idPost", idPost)
                    .executeUpdate();
            return index != 0;
        }, sf);
    }

}
