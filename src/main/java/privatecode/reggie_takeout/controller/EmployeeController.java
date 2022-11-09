package privatecode.reggie_takeout.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.entity.Employee;
import privatecode.reggie_takeout.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author 点点君
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param employee 前台发送来的数据，封装成一个对象
     * @param request 向前台共享数据
     * @return 返回响应结果
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        //1. 将页面提交的代码进行md5加密
        String password = employeeService.md5Digest(employee.getPassword());
        //2. 根据页面提交的用户名userName查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp==null){
            return R.error("登陆失败");
        }
        //3. 密码比对
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //4. 判断用户当前状态
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        //5. 登陆成功，将员工id存入Session并返回登陆成功的结果
        HttpSession session = request.getSession();
        session.setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request 从请求域中删除该员工的登录信息
     * @return 返回响应结果
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加员工
     * @param employee 前端获取的员工信息
     * @param request 请求信息，用于获取请求域中的属性
     * @return 返回响应结果
     */
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee,HttpServletRequest request){
        employee.setPassword(employeeService.md5Digest("123456"));
        employee.setStatus(1);
        //employee.setCreateTime(new Date());
        //employee.setUpdateTime(new Date());
        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("添加成功");
    }

    /**
     * 获取分页信息
     * @param page 当前第几页
     * @param pageSize 每页多少条数据
     * @param name 有无模糊查询
     * @return 返回分页信息
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name){
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> employeeQueryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        employeeQueryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        employeeQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,employeeQueryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 更新员工信息
     * @param employee 前端发送的员工信息
     * @param request 通过request获取session作用域中的登录信息，从而得知更新人的ID
     * @return 返回修改情况
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpServletRequest request){
        //由于更新了信息，故属性中的更新时间和更新人也要发生改变
        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(new Date());
//        employee.setUpdateUser(empId);
        //更新信息
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    /**
     * 跳转到修改页，通过数据库查询到对应员工的信息并填入
     * @param id 对应员工的id
     * @return 对应员工的所有信息
     */
    @GetMapping("/{id}")
    public R<Employee> updatePage(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
}

