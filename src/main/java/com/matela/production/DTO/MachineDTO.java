package com.matela.production.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MachineDTO {
    private Long machineId;
    private Double volume;
    private Double coutProduction;
    private Double coutTheorique;
}
