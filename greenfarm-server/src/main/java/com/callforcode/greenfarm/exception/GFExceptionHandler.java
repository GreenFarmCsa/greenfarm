package com.callforcode.greenfarm.exception;

import com.callforcode.greenfarm.util.ResultCode;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public final class GFExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo handle(Exception e) {
        log.error("Exception has occurred.", e);
        ResultVo resultVo = new ResultVo();
        resultVo.setMessage(e.getMessage());
        resultVo.setResultCode(getResultCode(e));
        return resultVo;
    }

    private ResultCode getResultCode(Exception e) {
        if (e instanceof GFIllegalSessionException) {
            return ResultCode.UNAUTHENTICATED;
        } else if (e instanceof GFOperationDenieException) {
            return ResultCode.UNAUTHORIZED;
        } else if (e instanceof GFException) {
            return ResultCode.ERROR;
        } else {
            return ResultCode.FAULT;
        }
    }

}
