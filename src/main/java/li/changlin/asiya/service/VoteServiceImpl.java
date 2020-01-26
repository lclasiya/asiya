package li.changlin.asiya.service;

import li.changlin.asiya.entity.Vote;
import li.changlin.asiya.repository.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteServiceInte {
    @Autowired
    private VoteRepo vr;

    @Override
    public Vote getVoteById(Integer id) {
        return vr.findOne(id);
    }

    @Override
    public void removeVoteById(Integer id) {
        vr.delete(id);
    }
}
