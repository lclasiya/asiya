package li.changlin.asiya.service;

import li.changlin.asiya.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserServiceInte {
    User saveUser(User user);

    void removeUser(Integer id);

    User getUserById(Integer id);

    User getUserByName(String name);

    List<User> listAll();
    Page<User> listUsers(Pageable pageable);


}
