package kr.co.wikibook.gallery.order.repository;

import kr.co.wikibook.gallery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByMemberIdOrderByIdDesc(Integer memberId, Pageable pageable);

    Optional<Order> findByIdAndMemberId(Integer id, Integer memberId);
}
