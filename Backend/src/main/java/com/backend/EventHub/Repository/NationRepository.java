package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long> {
    //Query
    Nation getByName(String name);

    Boolean existsByName(String name);
}
