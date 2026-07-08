package com.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    @Select("SELECT * FROM task WHERE status = 'PENDING' AND deleted = 0 ORDER BY create_time DESC")
    List<Task> findPendingTasks();

    @Select("SELECT * FROM task WHERE publisher_id = #{userId} OR executor_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<Task> findUserTasks(@Param("userId") Long userId);

    @Select("SELECT * FROM task WHERE category = #{category} AND deleted = 0 ORDER BY create_time DESC LIMIT #{limit}")
    List<Task> findTasksByCategory(@Param("category") String category, @Param("limit") Integer limit);
}
