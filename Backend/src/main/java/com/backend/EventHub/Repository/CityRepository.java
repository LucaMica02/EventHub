package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.backend.EventHub.Entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    //Query
    Boolean existsByNameAndNation(String name, Long nation);

    City getByNameAndNation(String name, Long nation);

    @Query(value = "SELECT n.name FROM Nation n JOIN City c ON c.nation = n.id where c.id = :cityId", nativeQuery = true)
    String getNationNameByCityId(@Param("cityId") Long cityId);

}
