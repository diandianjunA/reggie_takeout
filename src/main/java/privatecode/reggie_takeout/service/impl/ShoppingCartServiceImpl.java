package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import privatecode.reggie_takeout.entity.ShoppingCart;
import privatecode.reggie_takeout.service.ShoppingCartService;
import privatecode.reggie_takeout.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author 17305
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-11-08 18:56:41
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




