package li.changlin.asiya.service;

import li.changlin.asiya.entity.Comment;
import li.changlin.asiya.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentServiceInte {
    @Autowired
    private CommentRepo cr;

    @Override
    public Comment getCommentById(Integer id) {
        return cr.findOne(id);
    }

    @Override
    @Transactional
    public void removeCommentById(Integer id) {
        cr.delete(id);
    }
}
