package privatecode.reggie_takeout.service;

import privatecode.reggie_takeout.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17305
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-11-08 22:02:34
*/
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
