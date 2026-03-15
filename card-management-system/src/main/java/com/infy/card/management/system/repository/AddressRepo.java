package com.infy.card.management.system.repository;

import com.infy.card.management.system.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {
    Address findByUserUserId(Integer userId);
}
