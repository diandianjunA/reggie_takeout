package privatecode.reggie_takeout.service;

import privatecode.reggie_takeout.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17305
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-10-31 19:30:24
*/
public interface CategoryService extends IService<Category> {
    /**
     * 根据id来删除分类，但在删除前要先查询是否有菜品和套餐关联了该分类
     * @param id 要删除的分类的id
     */
    void remove(Long id);
}
