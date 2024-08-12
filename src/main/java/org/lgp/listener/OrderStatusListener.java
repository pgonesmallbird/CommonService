package org.lgp.listener;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lgp.domain.OrderDTO;
import org.lgp.enums.OrderEventEnum;
import org.lgp.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStatusListener {
    @Autowired
    private StateMachine<OrderStatusEnum, OrderEventEnum> orderStateMachine;


    @Resource
    private StateMachinePersister<OrderStatusEnum, OrderEventEnum, OrderDTO> stateMachinePersister;

    @OnTransition(source = "INIT", target = "REVIEW")
    public boolean submit(Message<OrderEventEnum> message) {
        OrderDTO order = (OrderDTO) message.getHeaders().get("order");
        order.setOrderStatusEnum(OrderStatusEnum.REVIEW);
        log.info("操作提交[{}]确认，状态机信息：{}", order.getOrderId());
//        stateMachinePersister.persist(orderStateMachine, order);
        return true;
    }

    @OnTransition(source = "REVIEW", target = "FINISH")
    public boolean pass(Message<OrderEventEnum> message) {
        OrderDTO order = (OrderDTO) message.getHeaders().get("order");
        order.setOrderStatusEnum(OrderStatusEnum.FINISH);
        log.info("操作提交[{}]确认，状态机信息：{}", order.getOrderNo(), message.getHeaders());
        return true;
    }

    @OnTransition(source = "REVIEW", target = "CANCEL")
    public boolean reject(Message<OrderEventEnum> message) {
        OrderDTO order = (OrderDTO) message.getHeaders().get("order");
        order.setOrderStatusEnum(OrderStatusEnum.CANCEL);
        log.info("操作驳回[{}]确认，状态机信息：{}", order.getOrderNo(), message.getHeaders());
        return true;
    }

}
