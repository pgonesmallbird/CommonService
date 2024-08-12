package org.lgp.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lgp.domain.OrderDTO;

@Mapper
public interface OrderMapper {
    OrderDTO queryOrderByOrderId(@Param("orderId") Long orderId);

    void insert(OrderDTO orderDTO);

    void update(@Param("orderDTO") OrderDTO orderDTO);
}
