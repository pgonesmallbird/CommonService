package org.lgp.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lgp.domain.OrderDTO;
import org.lgp.enums.OrderEventEnum;
import org.lgp.enums.OrderStatusEnum;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class StateEventUtil {
    @Resource
    private StateMachine<OrderStatusEnum, OrderEventEnum> orderStateMachine;

    @Resource
    private StateMachinePersister<OrderStatusEnum, OrderEventEnum, OrderDTO> stateMachinePersister;

    public synchronized boolean sendEvent(Message<OrderEventEnum> message) {
        boolean result = false;
        try {

            OrderDTO orderDTO = (OrderDTO) message.getHeaders().get("order");
            orderStateMachine.startReactively();
            stateMachinePersister.restore(orderStateMachine, orderDTO);
            orderStateMachine.sendEvent(Mono.just(message)).doOnComplete(()-> {
                log.info("sendEvent success.");
            }).doOnError(e-> {
                log.error("sendEvent error.", e);
            }).subscribe();
            result = orderStateMachine.sendEvent(message);

            stateMachinePersister.persist(orderStateMachine, orderDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            orderStateMachine.stopReactively();
        }
        return result;
    }


}
