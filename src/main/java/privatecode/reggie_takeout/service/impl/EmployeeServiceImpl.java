package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import privatecode.reggie_takeout.entity.Employee;
import privatecode.reggie_takeout.service.EmployeeService;
import privatecode.reggie_takeout.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author 17305
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-10-12 21:20:01
*/
@Service
@Transactional
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{
    @Override
    public String md5Digest(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}




