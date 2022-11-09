package privatecode.reggie_takeout.service;

import privatecode.reggie_takeout.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17305
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2022-10-12 21:20:01
*/
public interface EmployeeService extends IService<Employee> {
    public String md5Digest(String password);
}
