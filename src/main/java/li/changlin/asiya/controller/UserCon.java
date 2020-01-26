package li.changlin.asiya.controller;

import li.changlin.asiya.entity.Authority;
import li.changlin.asiya.entity.EsVideo;
import li.changlin.asiya.entity.User;
import li.changlin.asiya.entity.Video;
import li.changlin.asiya.service.AuthorityServiceInte;
import li.changlin.asiya.service.EsVideoServiceInte;
import li.changlin.asiya.service.UserServiceInte;
import li.changlin.asiya.service.VideoServiceInte;
import li.changlin.asiya.utils.ConstraintViolationExceptionHandler;
import li.changlin.asiya.utils.Response;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Controller
public class UserCon {
    @Autowired
    private UserServiceInte usi;
    @Autowired
    private AuthorityServiceInte asi;
    @Autowired
    private EsVideoServiceInte esi;
    @Autowired
    private VideoServiceInte vsi;
    @Autowired
    private UserDetailsService uds;

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> getlist(HttpServletRequest request){
        int pageNumber =  Integer.parseInt(request.getParameter("pageNumber"))-1;
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Map<String,Object> map = new HashMap<>();
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable= new PageRequest(pageNumber,pageSize,sort);
        List<User> users = usi.listUsers(pageable).getContent();
        for (User user:users) {
            user.setAuthority(user.getAuthorities().toString());
        }
        map.put("total", usi.listAll().size());
        map.put("rows", usi.listUsers(pageable).getContent());
        return map; }

    @GetMapping("/user/add")
    public String adduser(Model model){
        model.addAttribute("user",new User(null,0,null,null,null,null));
        return "user/add";
        }

    @GetMapping("/user/edit/{id}")
    public String edituser(@PathVariable("id") Integer id, Model model) {
        User user = usi.getUserById(id);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @DeleteMapping(value = "/user/{id}")
    @ResponseBody
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Model model) {
        try {
            usi.removeUser(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "操作完了"));
    }

    @PostMapping("/user")
    public ResponseEntity<Response> addOrEdituser(User user,Integer authorityId){
            List<Authority> authorities = new ArrayList<>();
            authorities.add(asi.getauthoritybyid(authorityId));
            if (user.getId() != 0){
                User originalUser= usi.getUserById(user.getId());
                originalUser.setUsername(user.getUsername());
                originalUser.setSex(user.getSex());
                originalUser.setEmail(user.getEmail());
                originalUser.setAge(user.getAge());
                originalUser.setTend(user.getTend());
                originalUser.setAuthorities(authorities);
                String rawPassword = originalUser.getPassword();
                PasswordEncoder  encoder = new BCryptPasswordEncoder();
                String encodePasswd = encoder.encode(user.getPassword());
                boolean isMatch = encoder.matches(rawPassword, encodePasswd);
                if (!isMatch) {
                    originalUser.setEncodePassword(user.getPassword());
                }
                try {
                    usi.saveUser(originalUser);
                }  catch (ConstraintViolationException e)  {
                    return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
                }
                return ResponseEntity.ok().body(new Response(true, "操作完了"));
            }else {
                user.setAuthorities(authorities);
                user.setEncodePassword(user.getPassword());
                try {
                    usi.saveUser(user);
                }  catch (ConstraintViolationException e)  {
                    return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
                }
                return ResponseEntity.ok().body(new Response(true, "操作完了"));
            }


        }
    @PostMapping("/userIcon")
    @ResponseBody
    public String uploadIcon (MultipartFile file){
        String iconName=null;
        if (Pattern.matches("image/.+",file.getContentType())) {
            iconName = "icon" + UUID.randomUUID().toString() + ".jpg";
        }
        try {
            file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\uploadIcon\\" + iconName));
            //file.transferTo(new File("E:\\迅雷下载\\project\\x1hn1k\\asiya\\src\\main\\resources\\static\\uploadIcon\\" + iconName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/uploadIcon/"+ iconName;
    }
    @GetMapping("/u/{id}")
    public String userProfile (@PathVariable("id")Integer id,Model model){
        User user = usi.getUserById(id);
        List<Video> videos= vsi.findVideoOfUser(user);
        List<EsVideo> esVideos=new ArrayList<>();
        for (Video v : videos){
            esVideos.add(esi.getEsVideoByVideoid(v.getId()));
        }
        model.addAttribute("videoList", esVideos);
        model.addAttribute("user",user);
       return "/user/userProfile";
    }
    @GetMapping("/userEdit")
    @PreAuthorize("authentication.name.equals(#username)")
    public String getUserEdit(@RequestParam(value = "name") String username, Model model) {
        User user = (User) uds.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "user/userEdit";
    }

    @PostMapping("/userEdit/{myname}")
    @PreAuthorize("authentication.name.equals(#myname)")
    public String userEdit(@PathVariable("myname") String myname,HttpServletRequest request,User user) {
        User originalUser = usi.getUserById(user.getId());
        originalUser.setAvatar(user.getAvatar());
        originalUser.setTend(user.getTend());
        originalUser.setAge(user.getAge());
        originalUser.setEmail(user.getEmail());
        originalUser.setSex(user.getSex());
        String rawPassword = originalUser.getPassword();
        PasswordEncoder  encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(user.getPassword());
        boolean isMatch = encoder.matches(rawPassword, encodePasswd);
        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }
        usi.saveUser(originalUser);
        SecurityContextHolder.clearContext();
        return "redirect:/ero";
    }
}
