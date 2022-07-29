package ru.work.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "photo")
    private byte[] photo;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Photo() {
    }

    public static Photo of(byte[] photo, Post post) {
        Photo ph = new Photo();
        ph.photo = photo;
        ph.post = post;
        return ph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
