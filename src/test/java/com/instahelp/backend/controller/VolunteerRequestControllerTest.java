package com.instahelp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahelp.backend.domain.VolunteerRequest;
import com.instahelp.backend.service.VolunteerRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRequestService volunteerRequestService;

    private ArgumentCaptor<VolunteerRequest> volunteerRequestArgumentCaptor = ArgumentCaptor.forClass(VolunteerRequest.class);

    private VolunteerRequest volunteerRequest;

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
    }

    @Test
    public void shouldCreateNewVolunteerRequest() throws Exception {
        when(volunteerRequestService.createVolunteerRequest(any(VolunteerRequest.class))).thenReturn(42l);
        mockMvc.perform(post("/volunteerRequest")
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

}
