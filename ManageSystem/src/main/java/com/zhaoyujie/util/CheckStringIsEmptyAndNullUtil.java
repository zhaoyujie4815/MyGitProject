package com.zhaoyujie.util;

/**
 * 用来校验前端所传过来的字段是否为空的工具类
 * 
 * @author zhaoyujie
 *
 */
public class CheckStringIsEmptyAndNullUtil {

    /**
     * 用来校验前端所传过来的字段是否为空
     * 
     * @param targetStr
     * @throws MyException
     */
    public static void checkStringIsEmptyAndNull(String targetStr, String getString) throws MyException {

        // 判断是否为空
        if (targetStr == null || targetStr.isEmpty()) {
            throw new MyException(ExceptionType.NotFound, "缺少 " + getString + " 字段！");
        }

    }

}
