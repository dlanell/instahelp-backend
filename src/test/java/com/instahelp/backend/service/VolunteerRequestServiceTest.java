package com.instahelp.backend.service;

import com.instahelp.backend.data.VolunteerRequestRepository;
import com.instahelp.backend.domain.VolunteerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
public class VolunteerRequestServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VolunteerRequestRepository volunteerRequestRepository;

    @InjectMocks
    private VolunteerRequestService volunteerRequestService;

    private VolunteerRequest volunteerRequest;
    private VolunteerRequest savedVolunteerRequest;

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

        MockitoAnnotations.initMocks(this);
        volunteerRequestService = new VolunteerRequestService(volunteerRequestRepository);
    }

    @Test
    public void shouldCreateNewVolunteerRequest() {
        when(volunteerRequestRepository.save(volunteerRequest)).thenReturn(savedVolunteerRequest);
        long id = volunteerRequestService.createVolunteerRequest(volunteerRequest);
        verify(volunteerRequestRepository, times(1)).save(volunteerRequest);
        assertThat(id, is(savedVolunteerRequest.getId()));
    }
}
