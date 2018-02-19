package service;

import domain.User;

public interface UserService extends AbstractDomainObjectService<User> {

    User getUserByEmail(String email);
}
