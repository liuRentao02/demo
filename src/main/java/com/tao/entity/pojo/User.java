package com.tao.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_name")
    private String username;

    @TableField("nick_name")
    private String nickname;

    @TableField("password")
    private String password;

    @TableField("role")
    private String role = "1"; // 字符类型的数字，默认值为"1"（用户）
}