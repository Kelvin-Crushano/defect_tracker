package com.sgic.semita.services;

import com.sgic.semita.dtos.EmailRecipientsDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.EmailEndPoints;
import com.sgic.semita.entities.EmailRecipients;
import com.sgic.semita.entities.User;
import com.sgic.semita.repositories.EmailEndPointsRepository;
import com.sgic.semita.repositories.EmailRecipientsRepository;
import com.sgic.semita.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmailRecipientsServiceImpl implements EmailRecipientsService{

    @Autowired
    private EmailRecipientsRepository emailRecipientsRepository;

    @Autowired
    private EmailEndPointsRepository emailEndPointsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<EmailRecipients> createEmailRecipients(List<EmailRecipientsDto> emailRecipientsDtos) {
        List<EmailRecipients> emailRecipientsList = emailRecipientsDtos.stream().map(dto -> {
            EmailEndPoints emailEndPoints = emailEndPointsRepository.findById(dto.getEmailEndpointId())
                    .orElseThrow(() -> new RuntimeException("Email Endpoints Not Found"));

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User Nor Found"));
            EmailRecipients emailRecipients = new EmailRecipients();
            emailRecipients.setEmailEndPoints(emailEndPoints);
            emailRecipients.setUser(user);
            emailRecipients.setStatus(false);

            return emailRecipients;
        }).collect(Collectors.toList());
        return emailRecipientsRepository.saveAll(emailRecipientsList);
    }

    // Delete
    @Override
    public boolean deleteEmailRecipient(Long id) {
        if (emailRecipientsRepository.existsById(id)) {
            emailRecipientsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get Email Recipients by Email Endpoints Id
    @Override
    @Transactional(readOnly = true)
    public List<EmailRecipientsDto> getEmailRecipientsByEndpointId(Long emailEndpointId, Pageable pageable) {
        Page<EmailRecipients> emailRecipientsPage = emailRecipientsRepository.findByEmailEndPointsId(emailEndpointId, pageable);

        return emailRecipientsPage.stream()
                .map(emailRecipient -> {
                    EmailRecipientsDto dto = new EmailRecipientsDto();
                    BeanUtils.copyProperties(emailRecipient, dto);
                    dto.setEmailEndpointId(emailRecipient.getEmailEndPoints().getId());
                    dto.setUserId(emailRecipient.getUser().getId());
                    dto.setUserEmail(emailRecipient.getUser().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }



    //Update
    @Override
    public EmailRecipients updateEmailRecipientStatus(Long id, Boolean status) {
        EmailRecipients emailRecipients = emailRecipientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Email Recipients Not Found"));
        emailRecipients.setStatus(status);
        return emailRecipientsRepository.save(emailRecipients);
    }
    // get user
    @Override
    public List<UserDto> getUsersNotAssociatedWithEmailEndpoint(Long emailEndpointId) {
        emailEndPointsRepository.findById(emailEndpointId).orElseThrow(()-> new RuntimeException("Email Endpoints Not Fund"));
        List<User> users = userRepository.findUsersNotAssociatedWithEmailEndpoint(emailEndpointId);
        return users.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    BeanUtils.copyProperties(user, userDto);
                    return userDto;
                })
                .collect(Collectors.toList());
    }
}
