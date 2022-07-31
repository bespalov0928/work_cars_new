package ru.work.cars.service;

import org.springframework.stereotype.Service;
import ru.work.cars.model.Photo;
import ru.work.cars.persistence.PhotoStore;

import java.util.List;

@Service
public class PhotoService {
    private final PhotoStore photoStore;

    public PhotoService(PhotoStore photoStore) {
        this.photoStore = photoStore;
    }

    public List<Photo> findAll() {
        return photoStore.findAll();
    }

    public Photo findById(int id) {
        return photoStore.findById(id);
    }

    public Photo savePhoto(Photo photo) {
        return photoStore.savePhoto(photo);
    }

    public Photo updatePhoto(Photo photo) {
        return photoStore.updatePhoto(photo);
    }

    public boolean delPhoto(int idPost) {
        return photoStore.delPhoto(idPost);
    }


}
