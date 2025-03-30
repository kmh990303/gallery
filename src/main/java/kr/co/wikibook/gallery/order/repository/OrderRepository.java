package kr.co.wikibook.gallery.order.repository;

import kr.co.wikibook.gallery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByMemberIdOrderByIdDesc(Integer memberId);

    Optional<Order> findByIdAndMemberId(Integer id, Integer memberId);
}
