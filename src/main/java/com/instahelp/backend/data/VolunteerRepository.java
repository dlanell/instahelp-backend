package com.instahelp.backend.data;

import com.instahelp.backend.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findByNameAndPhoneNumber(String name, String phoneNumber);
}
