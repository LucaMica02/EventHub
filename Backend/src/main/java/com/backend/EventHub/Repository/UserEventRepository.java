package com.backend.EventHub.Repository;

import org.springframework.data.jpa.repository.Query;
import com.backend.EventHub.Entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    //Query
    boolean existsByEventAndAppUser(Long event, Long appUser);
}
