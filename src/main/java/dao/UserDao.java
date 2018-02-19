package dao;

import domain.User;

import java.util.Collection;

public interface UserDao {
    User getUserByEmail(String email);

    User save(User user);

    void remove(User user);

    User getById(Long id);

    Collection<User> getAll();
}
