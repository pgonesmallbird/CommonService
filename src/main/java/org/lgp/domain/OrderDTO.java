package org.lgp.domain;

import lombok.Data;
import org.lgp.enums.OrderStatusEnum;

@Data
public class OrderDTO {
    private Long id;
    private Long orderId;
    private String orderNo;
    private OrderStatusEnum orderStatusEnum;
    private String stateMachineContext;
}
