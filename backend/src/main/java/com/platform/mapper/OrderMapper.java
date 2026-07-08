package com.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.entity.OrderPlatform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderPlatform> {

    @Select("SELECT * FROM order_platform WHERE payer_id = #{userId} OR payee_id = #{userId} ORDER BY create_time DESC")
    List<OrderPlatform> findUserOrders(@Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM order_platform WHERE (payer_id = #{userId} OR payee_id = #{userId}) AND status = 'COMPLETED'")
    Integer countCompletedOrders(@Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM order_platform WHERE (payer_id = #{userId} OR payee_id = #{userId}) AND status != 'COMPLETED' AND status != 'CANCELLED'")
    Integer countActiveOrders(@Param("userId") Long userId);
}
