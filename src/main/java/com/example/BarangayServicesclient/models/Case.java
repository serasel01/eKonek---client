package com.example.BarangayServicesclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Case {
    private String caseId;
    private String caseName;
    private String dateFilled;
    private String description;
}
