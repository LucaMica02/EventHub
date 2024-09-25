package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    //Query
    boolean existsByEventAndAppUser(Long event, Long appUser);

    Feedback getByEventAndAppUser(Long event, Long appUser);

    @Query(value = "SELECT f.* FROM Event e JOIN Feedback f ON e.id = f.event WHERE e.creator = :id", nativeQuery = true)
    List<Feedback> getFeedbackByCreator(@Param("id") Long id);
}

