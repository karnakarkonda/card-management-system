package com.infy.card.management.system.repository;

import com.infy.card.management.system.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepo extends JpaRepository<Card,Long> {
    List<Card> findByUserUserId(Integer userId);
}
