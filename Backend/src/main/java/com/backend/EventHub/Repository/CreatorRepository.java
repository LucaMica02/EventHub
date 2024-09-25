package com.backend.EventHub.Repository;

import com.backend.EventHub.Entity.AppUser;
import com.backend.EventHub.Entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.backend.EventHub.Entity.Creator;

import java.util.List;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {

    Boolean existsByAppUser(Long appUser);

    @Query(value = "SELECT SUM(f.stars) FROM Feedback f JOIN Event e ON f.event = e.id GROUP BY e.creator HAVING e.creator = :creatorId", nativeQuery = true)
    Integer getTotalStars(@Param("creatorId") Long creatorId);

    @Query(value = "SELECT COUNT(e.creator) FROM Feedback f JOIN Event e ON f.event = e.id GROUP BY e.creator HAVING e.creator = :creatorId", nativeQuery = true)
    Integer getFeedbackCount(@Param("creatorId") Long creatorId);

    @Query(value = "SELECT COUNT(e.creator) FROM Event e JOIN Creator c ON e.creator = c.appUser GROUP BY c.appUser HAVING c.appUser = :creatorId", nativeQuery = true)
    Integer getEventCount(@Param("creatorId") Long creatorId);

}
