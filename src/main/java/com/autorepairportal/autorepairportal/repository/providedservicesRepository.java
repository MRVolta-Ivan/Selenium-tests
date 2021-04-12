package com.autorepairportal.autorepairportal.repository;

import com.autorepairportal.autorepairportal.models.providedservices;
import com.autorepairportal.autorepairportal.models.workdays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface providedservicesRepository extends JpaRepository<providedservices, Long> {
    @Query(nativeQuery = true, value = "select * from providedservices where ID_Services = :Id")
    public List<providedservices> findByIDServices(Long Id);

    @Query(nativeQuery = true, value = "select * from providedservices where ID_RepairShop = :Id")
    public List<providedservices> findByIDShop(Long Id);
}
