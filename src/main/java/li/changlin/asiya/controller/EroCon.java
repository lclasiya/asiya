package li.changlin.asiya.controller;

import li.changlin.asiya.entity.EsVideo;
import li.changlin.asiya.entity.User;
import li.changlin.asiya.entity.Video;
import li.changlin.asiya.repository.EsVideoRepo;
import li.changlin.asiya.service.EsVideoServiceInte;
import li.changlin.asiya.service.VideoServiceInte;
import li.changlin.asiya.utils.ConstraintViolationExceptionHandler;
import li.changlin.asiya.utils.Response;
import li.changlin.asiya.utils.VideoAnalysis;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

@Controller
public class EroCon {
    @Autowired
    private EsVideoServiceInte esi;
    @Autowired
    private VideoServiceInte vsi;
    @Autowired
    private EsVideoRepo evr;

    @GetMapping("/ero")
    public String listVideo(
            @RequestParam(value="nagasa",required=false,defaultValue="") String nagasa,
            @RequestParam(value="object",required=false,defaultValue="") String object,
            @RequestParam(value="gashitsu",required=false,defaultValue="") String gashitsu,
            @RequestParam(value="tags",required=false,defaultValue="") String tags,
            @RequestParam(value="keyword",required=false,defaultValue="") String keyword,
            @RequestParam(value="async",required=false) boolean async,
            @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
            @RequestParam(value="pageSize",required=false,defaultValue="8") int pageSize,
            Model model) {
        //Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<EsVideo> videos=null;
        if (!keyword.isEmpty()){
            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            builder.withPageable(pageable);
            builder.withQuery(
                    QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title",keyword))
                            .should(QueryBuilders.matchQuery("tags",keyword))
                            .should(QueryBuilders.matchQuery("object",keyword))
            );
            videos = evr.search(builder.build());
        } else{
        videos = esi.findEsVideo(nagasa, object, gashitsu, tags, pageable);
    }
        List<EsVideo> esVideoList = videos.getContent();
        for (EsVideo esVideo : esVideoList) {
            esVideo.setImagesList(Arrays.asList(esVideo.getImages().split(",")));
            esi.saveEsVideo(esVideo);
        }
        model.addAttribute("videoList", esVideoList);
        model.addAttribute("page", videos);

        return (async==true?"ero/eroHontai :: #videoReplace":"ero/eroHontai");
    }
    @GetMapping("/ero/add")
    public String addero(){
        return "ero/eroAdd";
    }
    @PostMapping("/ero/upload")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseBody
    public Map<String,Object> uploadfile(@RequestPart("file") MultipartFile[] files){
        String pictureName=null;
        String videoName=null;
        String picturePath="";
        String videoPath="";
        Map<String,Object> map= new HashMap<>();
        Map<String,Object> nullmap= new HashMap<>();
        for (MultipartFile file : files){
            if (Pattern.matches("image/.+",file.getContentType())){
                pictureName ="image"+ UUID.randomUUID().toString() + ".jpg";
            }if (Pattern.matches("video/.+",file.getContentType())){
                videoName ="video"+ UUID.randomUUID().toString() + ".mp4";
            }
            try {
                if (pictureName != null) {
                    file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles\\" + pictureName));
                    //file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\src\\main\\resources\\static\\uploadFiles\\" + pictureName));
                    //picturePath="E:/迅雷下载/project/x1hn1k/asiya/src/main/resources/static/uploadFiles/" + pictureName;
                    picturePath="E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles/" + pictureName;
                    map.put("imageUrl","/uploadFiles/"+ pictureName);
                    map.put("videoUrl",videoPath);
                    map.put("videoMsg",nullmap);
                }
                if (videoName != null) {
                    file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles\\" + videoName));
                    //file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\src\\main\\resources\\static\\uploadFiles\\" + videoName));
                    //videoPath="E:/迅雷下载/project/x1hn1k/asiya/src/main/resources/static/uploadFiles/" + videoName;
                    videoPath="E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadFiles/" + videoName;
                    map.put("videoUrl",videoPath);
                    map.put("videoMsg",VideoAnalysis.getVideoMsg(videoPath));
                    map.put("imageUrl",picturePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    @PostMapping("/ero/submit")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Response> submitfile(@RequestBody Video video){
        User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            video.setUser(user);
            vsi.saveVideo(video);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/ero";
        return ResponseEntity.ok().body(new Response(true, "アップロード成功", redirectUrl));
    }
}
