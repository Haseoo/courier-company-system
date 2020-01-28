package com.github.haseoo.courier.repositories.jpa;

import com.github.haseoo.courier.models.ParcelStateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ParcelStateJPARepository extends JpaRepository<ParcelStateRecord, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM PARCELSTATERECORD WHERE ID = ?1", nativeQuery = true)
    public void deleteAndFlushById(Long id);
}
