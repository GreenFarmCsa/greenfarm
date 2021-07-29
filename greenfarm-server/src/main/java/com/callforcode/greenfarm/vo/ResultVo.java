package com.callforcode.greenfarm.vo;

import com.callforcode.greenfarm.util.ResultCode;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "Response VO")
@Data
public class ResultVo<T> {

    private ResultCode resultCode = ResultCode.OK;

    private String message;

    private T data;
}
