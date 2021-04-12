package com.autorepairportal.autorepairportal.repository;

import com.autorepairportal.autorepairportal.models.workdays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface workdaysRepository extends JpaRepository<workdays, Long> {

    @Query(nativeQuery = true, value = "select * from workdays where ID_RepairShop = :Id")
    public List<workdays> findByIDRepairShop(Long Id);
}
