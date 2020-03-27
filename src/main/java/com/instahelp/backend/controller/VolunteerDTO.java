package com.instahelp.backend.controller;

import com.instahelp.backend.domain.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDTO {
    private String name;
    private String phoneNumber;

    public Volunteer toVolunteer() {
        return Volunteer.builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
