package ru.work.cars.service;

import org.springframework.stereotype.Repository;
import ru.work.cars.model.*;
import ru.work.cars.persistence.PostStore;

import java.util.List;

@Repository
public class PostService {

    private final PostStore postStore;

    public PostService(PostStore postStore) {
        this.postStore = postStore;
    }

    public List<Post> findAll(){
        return postStore.findAll();
    }

    public void savePost(Post post){
        postStore.savePost(post);
    }

    public void savePostNew(Post post, User user, Mark mark, Body body, Transmission transmission, Engine engine){
        postStore.savePost(post, user, mark, body, transmission, engine);
    }

    public Post findById(int id){
        return postStore.findById(id);
    }

    public void postSale(int id){
        postStore.postSale(id);
    }

    public void postUpdate(Post post){
        postStore.postUpdate(post);
    }



}
