package org.lgp.services.impl;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lgp.domain.OrderDTO;
import org.lgp.enums.OrderEventEnum;
import org.lgp.enums.OrderStatusEnum;
import org.lgp.mapper.OrderMapper;
import org.lgp.services.OrderService;
import org.lgp.utils.StateEventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StateEventUtil stateEventUtil;

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private StateMachine<OrderStatusEnum, OrderEventEnum> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatusEnum, OrderEventEnum, OrderDTO> stateMachinePersister;

    @Override
    public Long submit() {
        OrderDTO orderDTO = new OrderDTO();
        long orderId = IdUtil.getSnowflakeNextId();
        log.info("start submit, orderId:{}", orderId);
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderNo("AHbb1-" + orderId);
        orderDTO.setOrderStatusEnum(OrderStatusEnum.INIT);
        orderMapper.insert(orderDTO);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.SUBMIT).setHeader("order", orderDTO).build();
        if (!stateEventUtil.sendEvent(message)) {
            log.error("submit failed:{}", orderDTO.getOrderNo());
        } else {
            log.info("submit success:{}", orderDTO.getOrderNo());
        }
        return orderId;
    }

    @Override
    public void pass(Long orderId) {
        log.info("start pass, orderId:{}", orderId);
        OrderDTO order = orderMapper.queryOrderByOrderId(orderId);
        order.setOrderStatusEnum(OrderStatusEnum.REVIEW);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.PASS).setHeader("order", order).build();
        if (!stateEventUtil.sendEvent(message)) {
            log.error("pass failed:{}", order.getOrderId());
        } else {
            log.info("pass success:{}", order.getOrderId());
        }
    }

    @Override
    public void reject(Long orderId) {
        log.info("start reject, orderId:{}", orderId);
        OrderDTO order = orderMapper.queryOrderByOrderId(orderId);
        order.setOrderStatusEnum(OrderStatusEnum.REVIEW);
        Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.REJECT).setHeader("order", order).build();
        if (!stateEventUtil.sendEvent(message)) {
            log.error("reject failed:{}", order.getOrderId());
        } else {
            log.info("reject success:{}", order.getOrderId());
        }
    }
}
