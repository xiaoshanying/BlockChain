package com.wmz.blockchain.model;

import java.io.Serializable;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
public class BlockResult<T> implements Serializable {

    private static final long serialVersionUID = -1008504539192471696L;

    private boolean success;

    private String message = "成功";

    private String code = "0000";

    private T data;

    public BlockResult() {
        super();
        this.success = true;
    }

    public BlockResult(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public BlockResult(T data, String message) {
        super();
        this.success = true;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BlockResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
