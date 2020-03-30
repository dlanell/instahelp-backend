package com.instahelp.backend.controller;

import com.instahelp.backend.domain.VolunteerRequest;
import com.instahelp.backend.service.VolunteerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/volunteer-requests")
@RequiredArgsConstructor
public class VolunteerRequestController {

    @Autowired
    private final VolunteerRequestService volunteerRequestService;

    @PostMapping
    public ResponseEntity<Long> createVolunteerRequest(@RequestBody VolunteerRequest volunteerRequest) {
        Long id = volunteerRequestService.createVolunteerRequest(volunteerRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VolunteerRequest>> getVolunteerRequests() {
        List<VolunteerRequest> volunteerRequests = volunteerRequestService.getVolunteerRequests();
        return new ResponseEntity<>(volunteerRequests, HttpStatus.OK);
    }

    @PostMapping("/{volunteerRequestId}/volunteer")
    public ResponseEntity<Long> volunteerForRequest(
            @RequestBody VolunteerDTO volunteerDto,
            @PathVariable Long volunteerRequestId) {
        Long id = volunteerRequestService.assignVolunteerToVolunteerRequest(volunteerDto.toVolunteer(), volunteerRequestId);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<String> handleVolunteerRequestNotFound() {
        return new ResponseEntity<>("Could not find a volunteer request with the given Id", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<String> handleVolunteerRequestAlreadyExists() {
        return new ResponseEntity<>("A request with the given information already exists", HttpStatus.BAD_REQUEST);
    }
}
