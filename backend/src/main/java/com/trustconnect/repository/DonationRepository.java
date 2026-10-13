package com.trustconnect.repository;

import com.trustconnect.entity.Donation;
import com.trustconnect.entity.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonorId(Long donorId);

    List<Donation> findByStatus(DonationStatus status);

    List<Donation> findByDonorTrustIdAndDonationDateBetween(
            Long trustId,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(d.promisedAmount), 0) FROM Donation d WHERE d.donor.trust.id = :trustId")
    BigDecimal getTotalPromised(Long trustId);

    @Query("SELECT COALESCE(SUM(d.receivedAmount), 0) FROM Donation d WHERE d.donor.trust.id = :trustId")
    BigDecimal getTotalReceived(Long trustId);

    @Query("SELECT COALESCE(SUM(d.dueAmount), 0) FROM Donation d WHERE d.donor.trust.id = :trustId")
    BigDecimal getTotalDue(Long trustId);
}