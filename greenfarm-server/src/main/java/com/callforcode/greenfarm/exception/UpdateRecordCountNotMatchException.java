package com.callforcode.greenfarm.exception;

public class UpdateRecordCountNotMatchException extends GFException {
    public UpdateRecordCountNotMatchException(String message, int target, int actual) {
        super(message + ".target count=" + target + " actual count=" + actual);
    }
}
