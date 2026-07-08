package com.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE student_no = #{studentNo} AND deleted = 0")
    User findByStudentNo(@Param("studentNo") String studentNo);

    @Select("SELECT * FROM user WHERE credit_score >= #{minScore} AND deleted = 0 ORDER BY credit_score DESC")
    List<User> findHighCreditUsers(@Param("minScore") Integer minScore);

    @Select("SELECT u.* FROM user u INNER JOIN guarantor g ON u.id = g.user_id WHERE g.guarantor_id = #{guarantorId} AND g.status = 'ACTIVE' AND u.deleted = 0")
    List<User> findGuaranteedUsers(@Param("guarantorId") Long guarantorId);
}
