package org.lgp.config;

import lombok.extern.slf4j.Slf4j;
import org.lgp.enums.OrderEventEnum;
import org.lgp.enums.OrderStatusEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;
import java.util.Optional;

@Configuration
@EnableStateMachine(name = "orderStateMachine")
@Slf4j
public class OrderStatusMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {
    @Bean
    public StateMachineListener<OrderStatusEnum, OrderEventEnum> listener() {
        return new StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum>() {
            @Override
            public void stateChanged(State<OrderStatusEnum, OrderEventEnum> from, State<OrderStatusEnum, OrderEventEnum> to) {
                log.warn("State from {} change to {}", Optional.ofNullable(from).map(State::getId).orElse(OrderStatusEnum.NULL), Optional.ofNullable(to).map(State::getId).orElse(OrderStatusEnum.NULL));
            }
        };
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(OrderStatusEnum.INIT).target(OrderStatusEnum.REVIEW)
                .event(OrderEventEnum.SUBMIT)
                .and()
                .withExternal()
                .source(OrderStatusEnum.REVIEW).target(OrderStatusEnum.FINISH)
                .event(OrderEventEnum.PASS)
                .and()
                .withExternal()
                .source(OrderStatusEnum.REVIEW).target(OrderStatusEnum.CANCEL)
                .event(OrderEventEnum.REJECT);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states) throws Exception {
        states.withStates().initial(OrderStatusEnum.INIT)
                .end(OrderStatusEnum.FINISH)
                .end(OrderStatusEnum.CANCEL)
                .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderEventEnum> config) throws Exception {
        config.withConfiguration().autoStartup(false).listener(listener());
    }
}
