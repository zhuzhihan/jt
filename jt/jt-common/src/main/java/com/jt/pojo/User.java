package com.jt.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@TableName("tb_user")
@Accessors(chain = true)
public class User extends BasePojo {
	@TableId(type = IdType.AUTO) // 主键自增
	private Long id; // 用户id
	private String username; // 用户名
	private String password; // 密码
	private String phone; // 电话
	private String email; // 电子邮件
}
