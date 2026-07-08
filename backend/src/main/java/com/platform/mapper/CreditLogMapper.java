package com.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.entity.CreditLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CreditLogMapper extends BaseMapper<CreditLog> {

    @Select("SELECT * FROM credit_log WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<CreditLog> findByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM credit_log WHERE user_id = #{userId} AND create_time >= #{since} ORDER BY create_time DESC")
    List<CreditLog> findRecentByUserId(@Param("userId") Long userId, @Param("since") java.time.LocalDateTime since);
}
