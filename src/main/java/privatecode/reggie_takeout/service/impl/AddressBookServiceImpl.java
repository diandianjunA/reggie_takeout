package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import privatecode.reggie_takeout.entity.AddressBook;
import privatecode.reggie_takeout.service.AddressBookService;
import privatecode.reggie_takeout.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author 17305
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-11-07 19:08:40
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




