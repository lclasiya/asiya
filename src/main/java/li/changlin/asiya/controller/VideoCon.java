package li.changlin.asiya.controller;

import li.changlin.asiya.entity.User;
import li.changlin.asiya.entity.Video;
import li.changlin.asiya.entity.Vote;
import li.changlin.asiya.service.VideoServiceInte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
public class VideoCon {
    @Autowired
    private VideoServiceInte vsi;

    @GetMapping("/video/{videoID}")
    public String getvideo(@PathVariable("videoID")Integer videoID,@RequestParam(value="async",required=false) boolean async, Model model){
        User principal = null;
        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")){
            principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Video video = vsi.getVideoById(videoID);
        if (async != true) {
            video.setReadSize(video.getReadSize() + 1);
            vsi.saveVideo(video);
        }
        User user = video.getUser();
        String videoUrl = video.getVideoUrl().substring(28);
        List<Vote> votes = video.getVotes();
        Vote currentVote = null; // 当前用户的点赞情况
        if (principal !=null) {
            for (Vote vote : votes) {
                vote.getUser().getUsername().equals(principal.getUsername());
                currentVote = vote;
                break;
            }
        }
        model.addAttribute("video",video);
        model.addAttribute("user",user);
        model.addAttribute("currentVote",currentVote);
        model.addAttribute("videoUrl",videoUrl);
        return (async==true?"video/video :: #def":"video/video");
    }
    @PostMapping("/coverImage")
    @ResponseBody
    public String uploadCover (MultipartFile file){
        String imageName=null;
        if (Pattern.matches("image/.+",file.getContentType())) {
            imageName = "cover" + UUID.randomUUID().toString() + ".jpg";
        }
        try {
            file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles\\" + imageName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageName;
    }
    @GetMapping("/delete/{videoID}")
    public String deleteVideo(@PathVariable("videoID")Integer videoID){
        int userID = vsi.getVideoById(videoID).getUser().getId();
        vsi.removeVideoById(videoID);
        return "redirect:/u/" + userID;
    }
}
