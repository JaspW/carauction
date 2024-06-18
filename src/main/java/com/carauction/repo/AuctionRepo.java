package com.carauction.repo;

import com.carauction.models.Auction;
import com.carauction.models.enums.AuctionStatus;
import com.carauction.models.enums.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepo extends JpaRepository<Auction, Long> {
    List<Auction> findAllByTransmission(Transmission transmission);

    List<Auction> findAllByNameContaining(String name);

    List<Auction> findAllByStatus(AuctionStatus status);
}
