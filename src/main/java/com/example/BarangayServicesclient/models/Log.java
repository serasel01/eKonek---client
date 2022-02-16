package com.example.BarangayServicesclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private String adminRFID, residentRFID,
            adminName, residentName, event;
    private long timestamp;
}
