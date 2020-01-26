package li.changlin.asiya.controller;

import li.changlin.asiya.entity.User;
import li.changlin.asiya.service.VideoServiceInte;
import li.changlin.asiya.service.VoteServiceInte;
import li.changlin.asiya.utils.ConstraintViolationExceptionHandler;
import li.changlin.asiya.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolationException;

@Controller
public class VoteCon {
    @Autowired
    private VideoServiceInte vsi;
    @Autowired
    private VoteServiceInte vosi;

    @PostMapping("/votes")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> createVote(Integer videoId) {

        try {
            vsi.createVote(videoId);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
    }
    @DeleteMapping("/votes/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Integer videoId) {

        boolean isOwner = false;
        User user = vosi.getVoteById(id).getUser();

        // 判断操作用户是否是点赞的所有者
        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal !=null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            vsi.removeVote(videoId, id);
            vosi.removeVoteById(id);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "取消点赞成功", null));
    }


}
