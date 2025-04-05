package kr.co.wikibook.gallery.order.service;

import kr.co.wikibook.gallery.cart.service.CartService;
import kr.co.wikibook.gallery.item.dto.ItemRead;
import kr.co.wikibook.gallery.item.service.ItemService;
import kr.co.wikibook.gallery.order.dto.OrderRequest;
import kr.co.wikibook.gallery.order.entity.Order;
import kr.co.wikibook.gallery.order.entity.OrderItem;
import kr.co.wikibook.gallery.order.dto.OrderRead;
import kr.co.wikibook.gallery.order.repository.OrderRepository;
import kr.co.wikibook.gallery.common.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final ItemService itemService;

    @Override
    public List<OrderRead> findAll(Integer memberId) {
        return orderRepository.findAllByMemberIdOrderByIdDesc(memberId).stream().map(Order::toRead).toList();
    }

    @Override
    public OrderRead find(Integer id, Integer memberId) {
        Optional<Order> orderOptional = orderRepository.findByIdAndMemberId(id, memberId);

        if (orderOptional.isPresent()) {
            OrderRead order = orderOptional.get().toRead();

            List<OrderItem> orderItems = orderItemService.findAll(order.getId());

            List<Integer> orderItemIds = orderItems.stream().map(OrderItem::getItemId).toList();

            List<ItemRead> items = itemService.findAll(orderItemIds);

            order.setItems(items);

            return order;
        }

        return null;
    }

    @Override
    @Transactional
    public void order(OrderRequest orderReq, Integer memberId) {
        List<ItemRead> items = itemService.findAll(orderReq.getItemIds());
        long amount = 0L;

        for (ItemRead item : items) {
            amount += item.getPrice() - item.getPrice().longValue() * item.getDiscountPer() / 100;
        }

        orderReq.setAmount(amount);

        if ("card".equals(orderReq.getPayment())) {
            orderReq.setCardNumber(EncryptionUtils.encrypt(orderReq.getCardNumber()));
        }

        Order order = orderRepository.save(orderReq.toEntity(memberId));

        List<OrderItem> newOrderItems = new ArrayList<>();

        orderReq.getItemIds().forEach((itemId) -> {
            OrderItem newOrderItem = new OrderItem(order.getId(), itemId);
            newOrderItems.add(newOrderItem);
        });

        orderItemService.saveAll(newOrderItems);

        cartService.removeAll(order.getMemberId());
    }
}
