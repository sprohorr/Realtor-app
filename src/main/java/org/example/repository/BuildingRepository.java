package org.example.repository;

import org.example.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

    boolean existsBuildingByAddress(String address);

    Building findBuildingByAddress(String address);
}
