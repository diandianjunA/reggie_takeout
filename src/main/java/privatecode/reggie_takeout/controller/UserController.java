package privatecode.reggie_takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.entity.User;
import privatecode.reggie_takeout.service.MailService;
import privatecode.reggie_takeout.service.UserService;
import privatecode.reggie_takeout.utils.ValidateImageCodeUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    /**
     * 通过邮箱发送验证码
     * @param user 浏览器传来的用户信息，含邮箱
     * @param session 将验证码存在session域中，后面好验证登录
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取邮箱
        String email = user.getEmail();
        //生成随机验证码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        //调用api发送邮件
        String to = email;
        String title= "验证码";
        String context = "根据验证码"+securityCode+"登录瑞吉外卖客户端";
        mailService.sendVertifyCode(to,title,context);
        session.setAttribute("code",securityCode);
        return R.success("验证码发送成功");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map,HttpSession session){
        String email = map.get("email");
        String code = map.get("code");
        String codeInSession = (String)session.getAttribute("code");
        if(codeInSession!=null&&codeInSession.equals(code)){
            //查看该用户是否为新用户
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getEmail,email);
            User user = userService.getOne(userLambdaQueryWrapper);
            if(user==null){
                //是新用户,自动注册
                user = new User();
                user.setEmail(email);
                user.setStatus(1);
                userService.save(user);
            }
            //将用户的信息存到session中，这样可以通过过滤器
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
