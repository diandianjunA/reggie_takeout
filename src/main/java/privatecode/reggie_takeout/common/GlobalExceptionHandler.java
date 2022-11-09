package privatecode.reggie_takeout.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import privatecode.reggie_takeout.exception.CustomException;

import java.io.FileNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> addEmployeeException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        String message = sqlIntegrityConstraintViolationException.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            return R.error(split[2]+"已存在");
        }else{
            return R.error("未知错误");
        }
    }

    @ExceptionHandler(CustomException.class)
    public R<String> deleteCategoryException(CustomException customException){
        String message = customException.getMessage();
        return R.error(message);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public R<String> fileNotFoundException(FileNotFoundException exception){
        String message=exception.getMessage();
        return R.error(message);
    }
}
