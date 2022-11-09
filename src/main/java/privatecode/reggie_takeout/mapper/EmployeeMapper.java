package privatecode.reggie_takeout.mapper;

import org.springframework.stereotype.Repository;
import privatecode.reggie_takeout.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17305
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-10-12 21:20:01
* @Entity privatecode.reggie_takeout.entity.Employee
*/
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

}




