package li.changlin.asiya.service;

import li.changlin.asiya.entity.Vote;

public interface VoteServiceInte {
    Vote getVoteById(Integer id);

    void removeVoteById(Integer id);


}
