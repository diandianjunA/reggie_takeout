package privatecode.reggie_takeout.mapper;

import org.springframework.stereotype.Repository;
import privatecode.reggie_takeout.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17305
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-10-31 19:30:24
* @Entity privatecode.reggie_takeout.entity.Category
*/
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}




