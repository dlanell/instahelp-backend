package com.instahelp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahelp.backend.domain.Volunteer;
import com.instahelp.backend.domain.VolunteerRequest;
import com.instahelp.backend.service.VolunteerRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRequestService volunteerRequestService;

    private ArgumentCaptor<VolunteerRequest> volunteerRequestArgumentCaptor = ArgumentCaptor.forClass(VolunteerRequest.class);
    private ArgumentCaptor<Volunteer> volunteerArgumentCaptor = ArgumentCaptor.forClass(Volunteer.class);
    private ArgumentCaptor<Long> volunteerRequestIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

    private VolunteerRequest volunteerRequest;
    private VolunteerRequest otherVolunteerRequest;

    private VolunteerDTO volunteerDTO;

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

        otherVolunteerRequest = VolunteerRequest.builder()
                .name("Hungry Harry")
                .title("Need food")
                .details("Haven't eaten in weeks")
                .zip("10101")
                .email("hungry@harry.com")
                .phoneNumber("4445556666")
                .date(new Date())
                .build();

        volunteerDTO = VolunteerDTO.builder()
                .name("Vinny Volunteer")
                .phoneNumber("1112223333")
                .build();
    }

    @Test
    public void shouldCreateNewVolunteerRequest() throws Exception {
        when(volunteerRequestService.createVolunteerRequest(any(VolunteerRequest.class))).thenReturn(42l);
        mockMvc.perform(post("/volunteer-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(volunteerRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("42"));

        verify(volunteerRequestService, times(1))
                .createVolunteerRequest(volunteerRequestArgumentCaptor.capture());

        VolunteerRequest actual = volunteerRequestArgumentCaptor.getValue();
        assertThat(actual.getName(), is(volunteerRequest.getName()));
        assertThat(actual.getTitle(), is(volunteerRequest.getTitle()));
        assertThat(actual.getDetails(), is(volunteerRequest.getDetails()));
        assertThat(actual.getZip(), is(volunteerRequest.getZip()));
        assertThat(actual.getEmail(), is(volunteerRequest.getEmail()));
        assertThat(actual.getPhoneNumber(), is(volunteerRequest.getPhoneNumber()));
        assertThat(actual.getDate(), is(volunteerRequest.getDate()));
    }

    @Test
    public void shouldReturnAllVolunteerRequests() throws Exception {
        when(volunteerRequestService.getVolunteerRequests()).thenReturn(Arrays.asList(volunteerRequest, otherVolunteerRequest));
        mockMvc.perform(get("/volunteer-requests")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(volunteerRequest.getName()))
                .andExpect(jsonPath("$.[1].name").value(otherVolunteerRequest.getName()));

        verify(volunteerRequestService, times(1)).getVolunteerRequests();
    }

    @Test
    public void shouldAssignVolunteerToVolunteerRequest() throws Exception {
        when(volunteerRequestService.assignVolunteerToVolunteerRequest(any(Volunteer.class), anyLong())).thenReturn(42l);
        mockMvc.perform(post("/volunteer-requests/21/volunteer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(volunteerDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));

        verify(volunteerRequestService, times(1))
                .assignVolunteerToVolunteerRequest(
                        volunteerArgumentCaptor.capture(),
                        volunteerRequestIdArgumentCaptor.capture());

        Long actualId = volunteerRequestIdArgumentCaptor.getValue();
        assertThat(actualId, is(21l));

        Volunteer actualVolunteer = volunteerArgumentCaptor.getValue();
        assertThat(actualVolunteer.getName(), is(volunteerDTO.getName()));
        assertThat(actualVolunteer.getPhoneNumber(), is(volunteerDTO.getPhoneNumber()));
    }

    @Test
    public void shouldReturnBadReqeustWhenVolunteerRequestIdCannotBeFound() throws Exception {
        when(volunteerRequestService.assignVolunteerToVolunteerRequest(any(Volunteer.class), anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/volunteer-requests/21/volunteer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(volunteerDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestWhenVolunteerRequestAlreadyExists() throws Exception {
        when(volunteerRequestService.createVolunteerRequest(any(VolunteerRequest.class))).thenThrow(new DuplicateKeyException("uh oh"));
        mockMvc.perform(post("/volunteer-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(volunteerRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
