package privatecode.reggie_takeout.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import privatecode.reggie_takeout.common.BaseContext;
import privatecode.reggie_takeout.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        //定义不需要处理的请求路径
        String[] urls = {"/employee/login", "/employee/logout", "/backend/**","/front/**","/user/sendMsg","/user/login"};
        boolean check = check(urls, requestURI);
        if(check){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //判断后台人员是否登录
        if(request.getSession().getAttribute("employee")!=null){

            //将id存到当前线程中
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //判断移动端用户是否登录
        if(request.getSession().getAttribute("user")!=null){

            //将id存到当前线程中
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //如果未登录
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls , String requestURI){
        for(String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
