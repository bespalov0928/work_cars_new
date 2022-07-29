package ru.work.cars.service;

import org.springframework.stereotype.Repository;
import ru.work.cars.model.Post;
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
}
