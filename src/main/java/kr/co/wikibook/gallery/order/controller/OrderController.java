package kr.co.wikibook.gallery.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.account.helper.AccountHelper;
import kr.co.wikibook.gallery.order.dto.OrderRequest;
import kr.co.wikibook.gallery.order.dto.OrderRead;
import kr.co.wikibook.gallery.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AccountHelper accountHelper;

    @GetMapping("/api/orders")
    public ResponseEntity<?> readAll(HttpServletRequest req) {
        Integer memberId = accountHelper.getMemberId(req);

        List<OrderRead> orders = orderService.findAll(memberId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<?> read(HttpServletRequest req, @PathVariable Integer id) {
        Integer memberId = accountHelper.getMemberId(req);

        OrderRead order = orderService.find(id, memberId);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<?> add(HttpServletRequest req, @RequestBody OrderRequest orderReq) {
        Integer memberId = accountHelper.getMemberId(req);

        orderService.order(orderReq, memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
