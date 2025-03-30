package com.agregator.Agregator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConnectionRequestAdminDTO extends ConnectionRequestDTO {

    private int connectionRequestId;


    public ConnectionRequestAdminDTO(int connectionRequestId, String RegNumber, LocalDate dateBegin, LocalDate dateEnd, String status, String addInfo) {
        super(RegNumber, dateBegin, dateEnd, status, addInfo);
        this.connectionRequestId = connectionRequestId;
    }
}
