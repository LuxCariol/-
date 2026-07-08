package com.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.entity.Guarantor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GuarantorMapper extends BaseMapper<Guarantor> {

    @Select("SELECT * FROM guarantor WHERE user_id = #{userId} AND status = 'ACTIVE' AND deleted = 0")
    List<Guarantor> findActiveGuarantorsByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM guarantor WHERE guarantor_id = #{guarantorId} AND status = 'ACTIVE' AND deleted = 0")
    List<Guarantor> findActiveByGuarantorId(@Param("guarantorId") Long guarantorId);

    @Select("SELECT COUNT(*) FROM guarantor WHERE user_id = #{userId} AND status = 'ACTIVE' AND deleted = 0")
    Integer countActiveGuarantors(@Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM guarantor WHERE guarantor_id = #{guarantorId} AND status = 'ACTIVE' AND deleted = 0")
    Integer countGuaranteeing(@Param("guarantorId") Long guarantorId);
}
