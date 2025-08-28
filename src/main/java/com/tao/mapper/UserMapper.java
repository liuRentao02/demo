package com.tao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tao.entity.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/29 00:07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
