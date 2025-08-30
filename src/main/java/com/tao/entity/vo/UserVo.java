package com.tao.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserVo
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String username;

    private String password;

    private String nickname;

    private String email;
    //    验证码
    private String code;

    //省略了get和set方法，自己生成一下
}
