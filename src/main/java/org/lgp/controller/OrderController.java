package org.lgp.controller;

import org.lgp.domain.R;
import org.lgp.services.OrderService;
import org.lgp.vo.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/machine/test")
    public String testOrderStatusMachine() {
        Long order1 = orderService.submit();
//        Long order2 = orderService.submit();
//        Long order3 = orderService.submit();
//        Long order4 = orderService.submit();

        new Thread(() -> {
            orderService.pass(order1);
//            orderService.pass(order2);
        }, "机器1").start();
//        orderService.reject(order3);
//        orderService.reject(order4);

        return Constants.SUCCESS;
    }

    @GetMapping("/machine/submit")
    public R<Long> submit() {
        return R.ok(orderService.submit());
    }

    @GetMapping("/machine/pass/{orderId}")
    public R<Long> pass(@PathVariable("orderId") Long orderId) {
        orderService.pass(orderId);
        return R.ok();
    }

    @GetMapping("/machine/reject/{orderId}")
    public R<Long> reject(@PathVariable("orderId") Long orderId) {
        orderService.reject(orderId);
        return R.ok();
    }
}
