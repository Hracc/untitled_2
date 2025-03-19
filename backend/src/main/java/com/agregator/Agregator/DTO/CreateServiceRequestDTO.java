package com.agregator.Agregator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CreateServiceRequestDTO {

    private int organizationId;
    private LocalDateTime dateTime;  // Время начала услуги
    private int serviceDetailId;     // ID выбранной услуги
    private String addInfo;          // Дополнительная информация

}
