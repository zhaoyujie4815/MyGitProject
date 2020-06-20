package com.zhaoyujie.util;

/**
 * 自定义异常类型
 * 
 * @author zhaoyujie
 *
 */
public enum ExceptionType {

    NotFound, // 未找到
    LookupDatabaseIsEmpty, // 查找数据库为空
    AnUnknownError, // 未知错误
    FailureToEarn, // 获取失败
    AlreadyExist, // 已存在
    BindingObjectIsEmpty, // 绑定对象为空
    Inconsistency, // 信息不一致
    WrongFormat, // 格式错误
    NotFoundField, // 未找到字段
    NotLoggedIn, // 未登录
    OperationError, // 操作错误
    FaildToConvert // 操作错误

}
