package com.instahelp.backend.service;

import com.instahelp.backend.domain.VolunteerRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioMessagingService implements MessagingService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.account.auth.token}")
    private String authToken;

    @Value("${twilio.account.phone.number}")
    private String phoneNumber;

    @Override
    public void sendCreationSmsFor(VolunteerRequest volunteerRequest) {
         Twilio.init(accountSid, authToken);

         Message.creator(
                 new PhoneNumber(String.format("+1%s", volunteerRequest.getPhoneNumber())),
                 new PhoneNumber(phoneNumber),
                 String.format("Your request for \"%s\" has been submitted. You'll be notified as soon as a volunteer is found to help with your request.", volunteerRequest.getTitle())
         ).create();


    }
}
