package com.instahelp.backend.controller;

import com.instahelp.backend.domain.VolunteerRequest;
import com.instahelp.backend.service.VolunteerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolunteerRequestController {

    @Autowired
    private final VolunteerRequestService volunteerRequestService;

    @PostMapping("/volunteerRequests")
    public ResponseEntity<Long> createVolunteerRequest(@RequestBody VolunteerRequest volunteerRequest) {
        Long id = volunteerRequestService.createVolunteerRequest(volunteerRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/volunteerRequests")
    public ResponseEntity<List<VolunteerRequest>> getVolunteerRequests() {
        List<VolunteerRequest> volunteerRequests = volunteerRequestService.getVolunteerRequests();
        return new ResponseEntity<>(volunteerRequests, HttpStatus.OK);
    }
}
