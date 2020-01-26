package li.changlin.asiya.service;

import li.changlin.asiya.entity.Authority;
import li.changlin.asiya.repository.AuthorityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityServiceInte {

    @Autowired
    private AuthorityRepo atr;

    @Override
    public Authority getauthoritybyid(Integer id) {
        return atr.findOne(id);
    }
}
