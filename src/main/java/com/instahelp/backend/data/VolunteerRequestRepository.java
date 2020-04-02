package com.instahelp.backend.data;

import com.instahelp.backend.domain.VolunteerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRequestRepository extends JpaRepository<VolunteerRequest, Long> {
    @Query("SELECT vr FROM VolunteerRequest vr WHERE vr.volunteer IS NULL AND vr.date >= DATE(NOW())")
    List<VolunteerRequest> findAvailableVolunteerRequests();
}
