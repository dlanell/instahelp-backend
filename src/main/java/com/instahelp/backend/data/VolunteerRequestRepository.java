package com.instahelp.backend.data;

import com.instahelp.backend.domain.VolunteerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRequestRepository extends JpaRepository<VolunteerRequest, Long> {
}
