package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.InPersonEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InPersonEventRepository extends JpaRepository<InPersonEvent, Long> {
    //Query
}
