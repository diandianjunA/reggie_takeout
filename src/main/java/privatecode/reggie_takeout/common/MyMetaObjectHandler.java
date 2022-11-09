package privatecode.reggie_takeout.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义的元数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            metaObject.setValue("createTime",new Date());
            metaObject.setValue("updateTime",new Date());
            metaObject.setValue("createUser",BaseContext.getCurrentId());
            metaObject.setValue("updateUser",BaseContext.getCurrentId());
        } catch (Exception ignored) {
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            metaObject.setValue("updateTime",new Date());
            metaObject.setValue("updateUser",BaseContext.getCurrentId());
        } catch (Exception ignored) {
        }
    }
}
