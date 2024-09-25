package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.OnlineEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineEventRepository extends JpaRepository<OnlineEvent, Integer> {
    //Query
}
