package ru.work.cars.service;

import org.springframework.stereotype.Service;
import ru.work.cars.model.User;
import ru.work.cars.persistence.UserStore;

@Service
public class UserService {

    private final UserStore userStore;

    public UserService(UserStore userStore) {
        this.userStore = userStore;
    }

    public User save(User user) {
        return userStore.save(user);
    }

    public User findById(int id) {
        return userStore.findById(id);
    }

    public User findByNamePass(String username, String pass) {
        return userStore.findByNamePass(username, pass);
    }

   public User findByName(String username) {
        return userStore.findByName(username);
    }


}
