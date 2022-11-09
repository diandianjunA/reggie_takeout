package privatecode.reggie_takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import privatecode.reggie_takeout.common.BaseContext;
import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.entity.ShoppingCart;
import privatecode.reggie_takeout.service.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart 购物车信息
     * @return 返回状态
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //设置用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //查询当前菜品或套餐是否在购物车中
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
        //先看传来的是菜品还是套餐
        Long dishId = shoppingCart.getDishId();
        if(dishId==null){
            //不是菜品，是套餐
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }else{
            //是菜品
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }
        ShoppingCart cart = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
        //如果不存在，就存进去，如果存在就数量加一
        if(cart==null){
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cart=shoppingCart;
        }else{
            Integer number = cart.getNumber();
            cart.setNumber(number+1);
            shoppingCartService.updateById(cart);
        }
        return R.success(cart);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);
        List<ShoppingCart> list = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        return R.success(list);
    }

    /**
     * 减少一个购物车商品
     * @param shoppingCart 传来的dishId火折子setmealId
     * @return 返回购物车数据
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);
        if(shoppingCart.getDishId()!=null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getSetmealId());
        }
        shoppingCart = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
        if(shoppingCart.getNumber()==1){
            shoppingCartService.removeById(shoppingCart);
        }else{
            Integer number = shoppingCart.getNumber();
            shoppingCart.setNumber(number-1);
            shoppingCartService.updateById(shoppingCart);
        }
        return R.success(shoppingCart);
    }

    /**
     * 清空购物车
     * @return 删除信息
     */
    @DeleteMapping("clean")
    public R<String> clean(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
        return R.success("清空成功");
    }
}
