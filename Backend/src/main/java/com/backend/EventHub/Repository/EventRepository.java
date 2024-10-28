package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.Event;
import com.backend.EventHub.Entity.EventState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.backend.EventHub.Entity.Event;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    //Query
    Page<Event> findAll(Pageable pageable);

    @Query(value = "SELECT e.* FROM Event e WHERE e.creator = :creatorId", nativeQuery = true)
    List<Event> findByCreatorId(@Param("creatorId") Long creatorId);

    @Query(value = "SELECT e.* FROM UserEvent ue JOIN Event e ON e.id = ue.event WHERE ue.appuser = :userId", nativeQuery = true)
    List<Event> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT COUNT(ue) FROM UserEvent ue WHERE ue.event = :eventId", nativeQuery = true)
    Integer getCountPeopleEvent(@Param("eventId") Long eventId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Event SET state = :state WHERE id = :eventId", nativeQuery = true)
    int updateEventState(@Param("eventId") Long eventId, @Param("state") String state);
}
