package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import privatecode.reggie_takeout.entity.User;
import privatecode.reggie_takeout.service.UserService;
import privatecode.reggie_takeout.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 17305
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-11-06 17:12:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




