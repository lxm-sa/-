package com.yb.base.vo;

import com.yb.base.vo.Result;

/**
 * 功能描述：返回结果实体类
 * Created by Administrator on 2019/7/18.
 */
public class ResultEntity<T> extends Result {

    private  T businessObject;

    public T getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(T businessObject) {
        this.businessObject = businessObject;
    }

}
