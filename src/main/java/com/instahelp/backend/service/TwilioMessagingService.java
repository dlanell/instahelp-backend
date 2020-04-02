package com.instahelp.backend.service;

import com.instahelp.backend.domain.VolunteerRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("twilio")
@RequiredArgsConstructor
public class TwilioMessagingService implements MessagingService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.account.auth.token}")
    private String authToken;

    @Value("${twilio.account.phone.number}")
    private String phoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendCreationSmsFor(VolunteerRequest volunteerRequest) {
         sendMessage(
                 String.format(volunteerRequest.getPhoneNumber()),
                 String.format("Your request for \"%s\" has been submitted. You will be notified as soon as a volunteer is found to help with your request.", volunteerRequest.getTitle())
         );
    }

    @Override
    public void sendVolunteerAssignedToMessage(VolunteerRequest volunteerRequest) {
        sendMessage(
                String.format(volunteerRequest.getPhoneNumber()),
                String.format("Great news, %s has volunteered to help with your request. You can reach them at: %s", volunteerRequest.getVolunteer().getName(), volunteerRequest.getVolunteer().getPhoneNumber())
        );

        sendMessage(
                String.format(volunteerRequest.getVolunteer().getPhoneNumber()),
                String.format("We have notified %s that you are interested in fulfilling their request. You can reach them at: %s to get the ball rolling and thanks for helping out!.", volunteerRequest.getName(), volunteerRequest.getPhoneNumber())
        );
    }

    private void sendMessage(String recipientPhoneNumber, String message) {
        try {
            Message.creator(
                    new PhoneNumber(String.format("+1%s", recipientPhoneNumber)),
                    new PhoneNumber(phoneNumber),
                    message
            ).create();
        } catch (Exception e) {
            System.err.println(String.format("Failed to send message \"%s\" to \"%s\".", message, recipientPhoneNumber));
            e.printStackTrace();
        }
    }
}
