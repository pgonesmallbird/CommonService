package org.lgp.services;

public interface OrderService {
    Long submit();
    void pass(Long orderId);
    void reject(Long orderId);
}
