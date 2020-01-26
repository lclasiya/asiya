package li.changlin.asiya.service;

import li.changlin.asiya.entity.*;
import li.changlin.asiya.repository.VideoRepo;
import li.changlin.asiya.utils.VideoAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoServiceInte {
    @Autowired
    private VideoRepo vr;
    @Autowired
    private EsVideoServiceInte esi;

    @Override
    public Video getVideoById(Integer id) {
        return vr.findOne(id);
    }

    @Override
    public void  removeVideoById(Integer id) {
        vr.delete(id);
        EsVideo esVideo = esi.getEsVideoByVideoid(id);
        esi.removeEsVideoById(esVideo.getId());
    }

    @Override
    public Video saveVideo(Video video) {
        boolean isNew = (video.getId() == 0);
        EsVideo esVideo = null;
        int abc = Integer.parseInt(video.getVideoQua());
        long def = VideoAnalysis.length;


        Video returnVideo = vr.save(video);

        if (isNew) {
            esVideo = new EsVideo(returnVideo);
        } else {
            esVideo = esi.getEsVideoByVideoid(video.getId());
            esVideo.update(returnVideo);
        }
        if (abc<361){
            esVideo.setGashitsu("dim");
        }else if (abc>360 && abc<601){
            esVideo.setGashitsu("fuzzy");
        }else {
            esVideo.setGashitsu("clear");
        }
        if (def<=600){
            esVideo.setNagasa("short");
        }else if (def>600 && def<1200){
            esVideo.setNagasa("middle");
        }else {
            esVideo.setNagasa("long");
        }

        esi.saveEsVideo(esVideo);
        return returnVideo;
    }

    @Override
    public Page<Video> listall(Pageable pageable) {
        return vr.findAll(pageable);
    }

    @Override
    public Video createVote(Integer id) {
        Video originalVideo = vr.findOne(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalVideo.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveVideo(originalVideo);
    }

    @Override
    public void removeVote(Integer videoId, Integer voteId) {
        Video originalVideo = vr.findOne(videoId);
        originalVideo.removeVote(voteId);
        this.saveVideo(originalVideo);
    }

    @Override
    public Video createComment(Integer videoId, String commentContent) {
        Video originalVideo = vr.findOne(videoId);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(commentContent,user);
        originalVideo.addComment(comment);
        return this.saveVideo(originalVideo);
    }

    @Override
    public void removeComment(Integer videoId, Integer commentId) {
        Video originalVideo = vr.findOne(videoId);
        originalVideo.removeComment(commentId);
        this.saveVideo(originalVideo);

    }

    @Override
    public List<Video> findVideoOfUser(User user) {
        return vr.findByUser(user);
    }
}
