package com.instahelp.backend.service;

import com.instahelp.backend.data.VolunteerRepository;
import com.instahelp.backend.data.VolunteerRequestRepository;
import com.instahelp.backend.domain.Volunteer;
import com.instahelp.backend.domain.VolunteerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class VolunteerRequestServiceTest {

    @Mock
    private VolunteerRequestRepository volunteerRequestRepository;

    @Mock
    private VolunteerRepository volunteerRepository;

    @InjectMocks
    private VolunteerRequestService volunteerRequestService;

    private VolunteerRequest volunteerRequest;
    private VolunteerRequest savedVolunteerRequest;

    private Volunteer volunteer;
    private Volunteer savedVolunteer;

    private ArgumentCaptor<Long> volunteerRequestIdCaptor = ArgumentCaptor.forClass(Long.class);
    private ArgumentCaptor<Volunteer> volunteerCaptor = ArgumentCaptor.forClass(Volunteer.class);
    private ArgumentCaptor<VolunteerRequest> volunteerRequestCaptor = ArgumentCaptor.forClass(VolunteerRequest.class);

    @BeforeEach
    public void setUp() {
        volunteerRequest = VolunteerRequest.builder()
                .name("Testy Testerson")
                .title("I need this to pass")
                .details("Seriously, this request should pass!")
                .zip("10101")
                .email("testy@testerson.com")
                .phoneNumber("1112223333")
                .date(new Date())
                .build();

        savedVolunteerRequest = VolunteerRequest.builder()
                .id(42l)
                .name("Testy Testerson")
                .title("I need this to pass")
                .details("Seriously, this request should pass!")
                .zip("10101")
                .email("testy@testerson.com")
                .phoneNumber("1112223333")
                .date(new Date())
                .build();

        volunteer = Volunteer.builder()
                .name("Vinny Volunteer")
                .phoneNumber("1112223333")
                .build();

        savedVolunteer = Volunteer.builder()
                .id(15l)
                .name("Vinny Volunteer")
                .phoneNumber("1112223333")
                .build();

        MockitoAnnotations.initMocks(this);
        volunteerRequestService = new VolunteerRequestService(volunteerRequestRepository, volunteerRepository);
    }

    @Test
    public void shouldCreateNewVolunteerRequest() {
        when(volunteerRequestRepository.save(volunteerRequest)).thenReturn(savedVolunteerRequest);
        long id = volunteerRequestService.createVolunteerRequest(volunteerRequest);
        verify(volunteerRequestRepository, times(1)).save(volunteerRequest);
        assertThat(id, is(savedVolunteerRequest.getId()));
    }

    @Test
    public void shouldReturnVolunteerRequests() {
        when(volunteerRequestRepository.findAllByVolunteerIsNull()).thenReturn(Arrays.asList(volunteerRequest, savedVolunteerRequest));
        List<VolunteerRequest> volunteerRequests = volunteerRequestService.getVolunteerRequests();
        verify(volunteerRequestRepository, times(1)).findAllByVolunteerIsNull();
        assertThat(volunteerRequests.size(), is(2));
        assertThat(volunteerRequests.get(0).getName(), is(volunteerRequest.getName()));
        assertThat(volunteerRequests.get(1).getName(), is(savedVolunteerRequest.getName()));
    }

    @Test
    public void shouldAssignVolunteerToUnassignedVolunteerRequest() {
        when(volunteerRequestRepository.findById(42l)).thenReturn(Optional.of(savedVolunteerRequest));

        long id = volunteerRequestService.assignVolunteerToVolunteerRequest(volunteer, 42l);
        assertThat(id, is(savedVolunteerRequest.getId()));

        verify(volunteerRequestRepository, times(1)).findById(volunteerRequestIdCaptor.capture());
        assertThat(volunteerRequestIdCaptor.getValue(), is(42l));

        verify(volunteerRepository, times(1)).save(volunteerCaptor.capture());
        assertThat(volunteerCaptor.getValue().getName(), is(volunteer.getName()));
        assertThat(volunteerCaptor.getValue().getPhoneNumber(), is(volunteer.getPhoneNumber()));

        verify(volunteerRequestRepository, times(1)).save(volunteerRequestCaptor.capture());
        VolunteerRequest actualRequest = volunteerRequestCaptor.getValue();
        assertThat(actualRequest.getVolunteer(), is(notNullValue()));
        assertThat(actualRequest.getVolunteer(), is(volunteer));
    }

    @Test
    public void shouldThrowErrorIfNoVolunteerRequestIsFoundWithTheGivenId() {
        when(volunteerRequestRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            volunteerRequestService.assignVolunteerToVolunteerRequest(volunteer, 1l);
        });
    }

    @Test
    public void shouldUseExistingVolunteerIfOneExistsWithTheSameNameAndPhoneNumber() {
        when(volunteerRepository.findByNameAndPhoneNumber(volunteer.getName(), volunteer.getPhoneNumber())).thenReturn(Optional.of(savedVolunteer));
        when(volunteerRequestRepository.findById(42l)).thenReturn(Optional.of(savedVolunteerRequest));

        volunteerRequestService.assignVolunteerToVolunteerRequest(volunteer, 42l);
        verify(volunteerRepository, times(1)).findByNameAndPhoneNumber(volunteer.getName(), volunteer.getPhoneNumber());
        verify(volunteerRepository, times(0)).save(any(Volunteer.class));
    }
}
