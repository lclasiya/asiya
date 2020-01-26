package li.changlin.asiya.service;

import li.changlin.asiya.entity.User;
import li.changlin.asiya.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInte, UserDetailsService {
    @Autowired
    private UserRepo userRepo;


    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Integer id) {
        userRepo.delete(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepo.getOne(id);
    }

    @Override
    public User getUserByName(String name) {
       return userRepo.findByUsername(name);
    }

    @Override
    public List<User> listAll() {
        return userRepo.findAll();
    }


    @Override
    public Page<User> listUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
