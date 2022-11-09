package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import privatecode.reggie_takeout.entity.OrderDetail;
import privatecode.reggie_takeout.service.OrderDetailService;
import privatecode.reggie_takeout.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 17305
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-11-08 22:02:37
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




