package li.changlin.asiya.controller;

import li.changlin.asiya.entity.Authority;
import li.changlin.asiya.entity.User;
import li.changlin.asiya.service.AuthorityServiceInte;
import li.changlin.asiya.service.UserServiceInte;
import li.changlin.asiya.utils.AdviceAnnotation;
import li.changlin.asiya.utils.Fiction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Strings;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginCon {
    @Autowired
    private UserServiceInte usi;
    @Autowired
    private AuthorityServiceInte asi;

    @GetMapping("/login")
    public String getlogin(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginerror(Model model){
        model.addAttribute("ifError",true);
        model.addAttribute("message","登録名またはパスワードが誤っている");
        return "login";
}
    @GetMapping("/admin")
    public String admin(Model model){
        List<Fiction> fics = new ArrayList<>();
        fics.add(new Fiction("ユーザー追加","/user/add"));
        fics.add(new Fiction("ユーザー削除","/user/delete"));
        fics.add(new Fiction("ユーザー編集","/user/modify"));
        model.addAttribute("fics",fics);
        return "admin"; }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    @AdviceAnnotation
    public String postRegister( User user,String password,Model model) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(asi.getauthoritybyid(2));
        user.setAuthorities(authorities);
        user.setEncodePassword(user.getPassword());
        try {
            usi.saveUser(user);
        }catch (ConstraintViolationException e){
            List<String> msgList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                msgList.add(constraintViolation.getMessage());
            }
            String messages = StringUtils.join(msgList.toArray(), "、");
            model.addAttribute("failMsg",messages);
            return "userAddFail";
        }

        return "redirect:/ero";
    }

}
