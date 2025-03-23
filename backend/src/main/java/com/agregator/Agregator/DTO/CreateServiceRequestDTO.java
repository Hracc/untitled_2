package com.agregator.Agregator.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateServiceRequestDTO {

    private int organizationId;
    private LocalDateTime dateTime;  // Время начала услуги
    private List<Integer> serviceDetailId;     // ID выбранной услуги
    private String addInfo;          // Дополнительная информация

}
