package org.example.otherEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 统一响应结果
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResultVO<T> {
    // 响应码 默认1000成功
    private int code;
    // 响应信息 来说明响应情况
    private String msg;
    // 响应的具体数据
    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }
}
