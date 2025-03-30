package kr.co.wikibook.gallery.order.service;

import kr.co.wikibook.gallery.order.dto.OrderRead;
import kr.co.wikibook.gallery.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    List<OrderRead> findAll(Integer memberId);

    OrderRead find(Integer id, Integer memberId);

    void order(OrderRequest orderReq, Integer memberId);
}
