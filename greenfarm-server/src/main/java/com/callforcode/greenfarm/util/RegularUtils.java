package com.callforcode.greenfarm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtils {

    public static boolean getPatternFlag(String ruleExpression, String text) {
        boolean flag = false;
        Pattern pattern = Pattern.compile(ruleExpression);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            flag = true;
        }
        return flag;
    }
}
