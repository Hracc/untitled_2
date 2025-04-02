package com.agregator.Agregator.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequestAdminDTO {

    private int connectionRequestId;
    private String organizationName ;

    private String RegNumber;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String status;
    private String addInfo;
}
