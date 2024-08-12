package org.lgp.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.lgp.domain.OrderDTO;
import org.lgp.enums.OrderEventEnum;
import org.lgp.enums.OrderStatusEnum;
import org.lgp.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class OrderPersist {

    private static final Map<Long, StateMachineContext<OrderStatusEnum, OrderEventEnum>> map = new HashMap<>();

    @Bean
    public DefaultStateMachinePersister<OrderStatusEnum, OrderEventEnum, OrderDTO> stateMachinePersister(@Autowired OrderMapper orderMapper) {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatusEnum, OrderEventEnum, OrderDTO>() {
            @Override
            public void write(StateMachineContext<OrderStatusEnum, OrderEventEnum> stateMachineContext, OrderDTO orderDTO) throws Exception {
                log.info("stateMachinePersister write.");
                if (orderDTO == null) {
                    return;
                }
                orderDTO.setStateMachineContext(JSON.toJSONString(stateMachineContext));
                Long orderId = orderDTO.getOrderId();
                if (orderId == null) {
                    orderMapper.insert(orderDTO);
                    return;
                }
                orderMapper.update(orderDTO);
            }

            @Override
            public StateMachineContext<OrderStatusEnum, OrderEventEnum> read(OrderDTO orderDTO) throws Exception {
                OrderDTO order = orderMapper.queryOrderByOrderId(orderDTO.getOrderId());
                if (order== null) {
                    return null;
                }
                String stateMachineContext = order.getStateMachineContext();
                return JSON.parseObject(stateMachineContext, StateMachineContext.class);
            }
        });
    }
}



