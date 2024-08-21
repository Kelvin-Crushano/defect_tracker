package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRecipientsDto {
    private Long id;
    private Long emailEndpointId;
    private Long userId;
    private String userEmail;
    private boolean status;
}
