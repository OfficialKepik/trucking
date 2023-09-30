package com.example.diplom.repository;

import com.example.diplom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Modifying
    @Query("UPDATE Order o SET o.user.id = :newUserId WHERE o.user.id = :oldUserId")
    void updateUserId(@Param("oldUserId") Long oldUserId, @Param("newUserId") Long newUserId);
}
