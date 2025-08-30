package com.tao.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    @TableField("password")
    private String password;

    @TableField("role")
    private Object role;

    private Integer enabled;

    private String email;

}