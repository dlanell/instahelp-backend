package com.instahelp.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String title;
    private String details;
    private Date date;
    private String zip;
    private String phoneNumber;
    private String email;
    private String preferredPaymentMethod;

    @ManyToOne
    @JoinColumn(name="volunteer_id")
    @JsonIgnore
    private Volunteer volunteer;
}
