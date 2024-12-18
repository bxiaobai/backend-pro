package com.backend.pro.common;

public interface CodeConstant {
    //------系统返回码
    ErrorCode SUCCESS = new ErrorCode(0, "ok");
    ErrorCode PARAMS_ERROR = new ErrorCode(40000, "请求参数错误");
    ErrorCode NOT_LOGIN_ERROR = new ErrorCode(40100, "未登录");
    ErrorCode NO_AUTH_ERROR = new ErrorCode(40101, "无权限");
    ErrorCode NOT_FOUND_ERROR = new ErrorCode(40400, "请求数据不存在");
    ErrorCode FORBIDDEN_ERROR = new ErrorCode(40300, "禁止访问");
    ErrorCode SYSTEM_ERROR = new ErrorCode(50000, "系统内部异常");
    ErrorCode OPERATION_ERROR = new ErrorCode(50001, "操作失败");
    ErrorCode WEB_SERVER_REMOVE_ERROR = new ErrorCode(60000, "Web服务器异常,取消失败");
    ErrorCode WEB_SERVER_SAVE_ERROR = new ErrorCode(60000, "Web服务器异常,添加失败");


    //-----字典错误码
    ErrorCode SYSTEM_DICT_EXISTS = new ErrorCode(100000, "系统字典已存在");
    ErrorCode SYSTEM_DICT_HAS_CHILDREN = new ErrorCode(100001, "无法删除，该字典类型还有字典数据");
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(100002, "当前字典类型不存在");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(100003, "字典类型不处于开启状态，不允许选择");

    //---科室错误吗
    ErrorCode SYSTEM_DEPT_EXISTS = new ErrorCode(200000, "系统科室已存在");
    ErrorCode SYSTEM_CHILDREN_NOT_NULL_EXISTS = new ErrorCode(200000, "存在子科室，无法删除");

    //---输液室
    ErrorCode SYSTEM_ROOM_EXISTS = new ErrorCode(300000, "系统输液室已存在");
    ErrorCode SYSTEM_ROOM_HAS_CHILDREN = new ErrorCode(300001, "无法删除，该输液室还有输液室数据");

    //---座位
    ErrorCode SYSTEM_SEAT_EXISTS = new ErrorCode(400000, "该座位号已有数据");
    ErrorCode SEAT_COL_ROW_EXISTS = new ErrorCode(400001, "行号和列号已存在座位");

    //---模板
    ErrorCode TEMPLATE_EXISTS = new ErrorCode(500000, "改时间模板已存在");
//    未查询到该输液室模板
    ErrorCode TEMPLATE_NOT_EXISTS = new ErrorCode(500001, "未查询到模板,请联系管理员");

    //---号源
    ErrorCode SOURCE_EXISTS = new ErrorCode(600000, "未查询到号源");

    //--患者已经预约过
    ErrorCode DETAILS_EXISTS = new ErrorCode(700000 , "患者已经预约过当日,请取消后重新预约");

    //患者表
    ErrorCode CARD_EXISTS = new ErrorCode(800000, "该患者已存在");
    ErrorCode CARD_NOT_EXISTS = new ErrorCode(800001, "该患者不存在");

}
