package com.taotao.store.order.bean;

/**
 * 构建sql语句中的Where条件
 */
public class Where {

    private String column;

    private Object value;

    private String operater;

    private Where(String column, Object value, String operater) {
        this.column = column;
        this.value = value;
        this.operater = operater;
    }

    public static Where build(String column, Object value, OperaterEnum operaterEnum) {
        return new Where(column, value, operaterEnum.toString());
    }

    public static Where build(String column, Object value) {
        return build(column, value, OperaterEnum.EQUAL);
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }
}
