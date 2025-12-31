package com.lvr.ihave.web.interceptor;

import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.constant.StatusEnum;
import com.lvr.ihave.ex.PhoneNotFoundException;
import com.lvr.ihave.ex.UserPasswordErrorException;
import com.lvr.ihave.ex.UsernameTakenException;
import com.lvr.ihave.util.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JSONResult handleException(Exception e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = StatusEnum.SYSTEM_ERROR.getMsg();
        }
        return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), msg);
    }

    @ResponseBody
    @ExceptionHandler(UsernameTakenException.class)
    public JSONResult handleUserTokenException(UsernameTakenException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = StatusEnum.TOKEN_IS_EXPIRED.getMsg();
        }
        return JSONResult.fail(StatusEnum.TOKEN_IS_EXPIRED.getCode(), msg);
    }

    @ResponseBody
    @ExceptionHandler(PhoneNotFoundException.class)
    public JSONResult handlePhoneNotFoundException(PhoneNotFoundException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = Constant.PHONE_UNSINED;
        }
        return JSONResult.fail(StatusEnum.NOT_FOUND.getCode(), msg);
    }
    @ResponseBody
    @ExceptionHandler(UserPasswordErrorException.class)
    public JSONResult handleUserPasswordErrorException(UserPasswordErrorException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = Constant.LOGIN_FAILED;
        }
        return JSONResult.fail(StatusEnum.FAIL.getCode(), msg);
    }

}
