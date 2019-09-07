package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable {
	private Integer status; // 200表示正确
	private String msg;
	private Object data;

	public static SysResult success() {
		return new SysResult(200, "服务器执行成功!!", null);
	}

	public static SysResult success(Object data) {
		return new SysResult(200, "服务器执行成功!", data);
	}

	public static SysResult success(String msg, Object data) {
		return new SysResult(200, msg, data);
	}

	public static SysResult fail() {
		return new SysResult(201, "服务器处理失败!", null);
	}

}
