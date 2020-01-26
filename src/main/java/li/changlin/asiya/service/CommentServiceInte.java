package li.changlin.asiya.service;

import li.changlin.asiya.entity.Comment;

public interface CommentServiceInte {
    Comment getCommentById(Integer id);

    void removeCommentById(Integer id);
}
