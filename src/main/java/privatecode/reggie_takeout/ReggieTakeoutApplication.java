package privatecode.reggie_takeout;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
//扫描mapper接口
@MapperScan("privatecode/reggie_takeout/mapper")
//扫描filter
@ServletComponentScan("privatecode/reggie_takeout/filter")

@EnableTransactionManagement
public class ReggieTakeoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieTakeoutApplication.class, args);
        log.info("项目启动成功...");
    }

}
