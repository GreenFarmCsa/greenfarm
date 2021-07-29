package com.callforcode.greenfarm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.callforcode.greenfarm.vo.RevisePlantTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import com.callforcode.greenfarm.vo.ResultVo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Slf4j
public class BeanUtils {

    @SneakyThrows
    public static <T> T copyBean(Object src, Class<T> target) {
        if (Objects.isNull(src)) {
            return null;
        }
        BeanCopier beanCopier = BeanCopier.create(src.getClass(), target, false);
        T t = target.newInstance();
        beanCopier.copy(src, t, null);
        return t;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SneakyThrows
    public static <T> List<T> copyListBean(List src, Class<T> target) {
        if (Objects.isNull(src)) {
            return null;
        }
        List<T> list = new ArrayList<>(src.size());
        src.forEach(s -> list.add(copyBean(s, target)));
        return list;
    }

    @SneakyThrows
    public static <T> void copyBean(Object src, T target) {
        BeanCopier beanCopier = BeanCopier.create(src.getClass(), target.getClass(), false);
        beanCopier.copy(src, target, null);
    }

    public static <T> ResultVo<T> createResultVo(T data) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setData(data);
        return resultVo;
    }

    @SneakyThrows
    public static String convertJson(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }

    public static List<RevisePlantTaskVo.FileInfo> convertOperateRecord(String json) {
        try {
            return JSON.parseArray(json, RevisePlantTaskVo.FileInfo.class);
        } catch (Exception e) {
            log.error("Convert operate_record to json error.json=" + json, e);
            return null;
        }
    }

    public static List<String> convertStringWithCommaToArray(String str) {
        return splitToArray(str, ",");
    }

    public static List<String> splitToArray(String str, String separator) {
        List<String> array = new ArrayList<>();
        if (str != null && str.length() != 0) {
            for (String strTemp : str.split(separator)) {
                array.add(strTemp);
            }
        }
        return array;
    }

    public static String convertArrayToStringWithComma(List<String> array) {
        String str = "";
        // array has element.
        if (array != null && array.size() != 0) {
            for (String tmpStr : array) {
                str += tmpStr + ",";
            }
            str = str.substring(0, str.length() - 1);
        }
        // if array has no element, return "".
        return str;
    }
}
