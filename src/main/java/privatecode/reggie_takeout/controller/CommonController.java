package privatecode.reggie_takeout.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import privatecode.reggie_takeout.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件的上传下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file 浏览器发来的文件
     * @return 返回文件名称和状态
     */
    @PostMapping("/upload")
    public R<String> updateFile(MultipartFile file){
        //file是一个临时文件，需要转存到其他位置，否则本次请求结束后该文件会被删除
        //首先判断一下输出的目录是否存在
        File dir = new File(basePath);
        //如果不存在就创建这个目录
        if(!dir.exists()){
            dir.mkdirs();
        }
        //获得原始文件名
        String originalFilename = file.getOriginalFilename();
        //获得文件的后缀
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        //与UUID随机生成的文件名连接，得到新的随机文件名
        String fileName = UUID.randomUUID() +suffix;
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name 文件名
     * @param response 响应，用于将文件流写入传到浏览器
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //通过输入流读取文件内容
            fileInputStream = new FileInputStream(new File(basePath + name));
            //通过输出流将文件写回到浏览器
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
            response.setContentType("image/type");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
