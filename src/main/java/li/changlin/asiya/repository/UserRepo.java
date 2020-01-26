package li.changlin.asiya.repository;

import li.changlin.asiya.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepo extends JpaRepository<User,Integer>, PagingAndSortingRepository<User,Integer> {
    User findByUsername(String username);
}
