package serviceimpl;

import dao.UserDao;
import domain.User;
import service.UserService;

import java.util.Collection;


public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User save(User object) {
        return userDao.save(object);
    }

    @Override
    public void remove(User object) {
        userDao.remove(object);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }
}
