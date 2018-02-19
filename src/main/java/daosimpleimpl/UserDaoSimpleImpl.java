package daosimpleimpl;

import dao.UserDao;
import domain.User;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class UserDaoSimpleImpl implements UserDao {
    private Map<Long, User> userById = new Hashtable<>();
    private long maxId = 0;

    @Override
    public User getUserByEmail(String email) {
        return userById.values().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public User save(User user) {
        maxId++;
        user.setId(maxId);
        userById.put(maxId, user);
        return user;
    }

    @Override
    public void remove(User user) {
        userById.remove(user.getId());
    }

    @Override
    public User getById(Long id) {
        return userById.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return userById.values();
    }
}
