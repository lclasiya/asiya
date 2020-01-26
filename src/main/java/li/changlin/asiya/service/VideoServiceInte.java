package li.changlin.asiya.service;

import li.changlin.asiya.entity.EsVideo;
import li.changlin.asiya.entity.User;
import li.changlin.asiya.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoServiceInte {
    Video getVideoById(Integer id);

    void  removeVideoById(Integer id);

    Video saveVideo(Video video);

    Page<Video> listall(Pageable pageable);

    Video createVote(Integer id);

    void removeVote(Integer videoId, Integer voteId);

    Video createComment(Integer videoId, String commentContent);

    void removeComment(Integer videoId, Integer commentId);

    List<Video> findVideoOfUser(User user);
}
