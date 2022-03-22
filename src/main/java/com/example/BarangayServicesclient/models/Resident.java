package com.example.BarangayServicesclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident {
    private String firstName, middleName, lastName, gender, birthPlace, barangay,
            civilStatus, educationalAttainment, emailAddress, lotBlockPhase, street, subdivisionVillageZone,
            mobileNumber, occupation, status, landline, userType, userRFID;
    private long birthDate;
    private int age;

    public String getFullName() {
        return getFirstName() + " " + getMiddleName() + " " + getLastName();
    }

    public int getAge() {
        return Period.between(LocalDate.ofInstant(
                Instant.ofEpochMilli(birthDate),
                        ZoneId.systemDefault()),
                LocalDate.now()).getYears();
    }

    public String getFormattedBirthDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL dd yyyy");
        return LocalDate.ofInstant(Instant.ofEpochMilli(birthDate),
                ZoneId.systemDefault()).format(formatter);
    }

    public String getFullAddress(){
        return getLotBlockPhase() + ", " + getSubdivisionVillageZone()
                + ", " + getStreet() + ", " + getBarangay();
    }


}
