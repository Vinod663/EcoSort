package org.example.ecosortsoftware.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String employeeId;
    private String divisionId;//not in employee table
    private String employeeName;
    private String Email;
    private String municipalId;

}
