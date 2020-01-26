package li.changlin.asiya.repository;

import li.changlin.asiya.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository<Vote,Integer> {
}
