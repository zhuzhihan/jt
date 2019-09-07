package com.jt.thro;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j // 引入日志api
public class SysResultControllererAdvice {
	// @ResponseBody
	@ExceptionHandler(RuntimeException.class) // 异常处理器
	public SysResult sysResultExpeResult(Exception e) {
		e.printStackTrace(); // 方便调错 开发阶段留着
		log.error("服务器异常" + e.getMessage());
		return SysResult.fail();
	}
}
