package cn.com.code4fun.netty.dto;

import java.io.Serializable;

/**
 * 返回对象
 * @desc: cn.com.code4fun.netty.dto.RpcResponse
 * @author: niejian9001@163.com
 * @date: 2019-10-24 16:19
 */
public class RpcResponse implements Serializable {
    private String id;
    private Object data;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "id='" + id + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
