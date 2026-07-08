package com.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT * FROM message WHERE (from_user = #{userId} OR to_user = #{userId}) AND task_id = #{taskId} ORDER BY create_time ASC")
    List<Message> findTaskMessages(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
