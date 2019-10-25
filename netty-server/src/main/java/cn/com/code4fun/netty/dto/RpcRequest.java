package cn.com.code4fun.netty.dto;

import java.io.Serializable;

/**
 * 传输请求对象
 * @desc: cn.com.code4fun.netty.dto.RpcRequest
 * @author: niejian9001@163.com
 * @date: 2019-10-24 16:18
 */
public class RpcRequest implements Serializable {
    private String id;
    private Object data;

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

    @Override
    public String toString() {
        return "RpcRequest{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }
}
