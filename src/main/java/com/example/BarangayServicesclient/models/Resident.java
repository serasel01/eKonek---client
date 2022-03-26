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

    public Resident(ResidentBuilder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.birthPlace = builder.birthPlace;
        this.barangay = builder.barangay;
        this.civilStatus = builder.civilStatus;
        this.educationalAttainment = builder.educationalAttainment;
        this.emailAddress = builder.emailAddress;
        this.lotBlockPhase = builder.lotBlockPhase;
        this.street = builder.street;
        this.subdivisionVillageZone = builder.subdivisionVillageZone;
        this.mobileNumber = builder.mobileNumber;
        this.occupation = builder.occupation;
        this.status = builder.status;
        this.landline = builder.landline;
        this.userType = builder.userType;
        this.userRFID = builder.userRFID;
        this.birthDate = builder.birthDate;
    }

    public Resident(String firstName,
                    String middleName,
                    String lastName,
                    String barangay,
                    String userRFID){

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.barangay = barangay;
        this.userRFID = userRFID;
    }

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

    public static class ResidentBuilder {
        private String firstName, middleName, lastName, gender, birthPlace, barangay,
                civilStatus, educationalAttainment, emailAddress, lotBlockPhase, street, subdivisionVillageZone,
                mobileNumber, occupation, status, landline, userType, userRFID;
        private long birthDate;
        private int age;

        public ResidentBuilder(String firstName,
                               String middleName,
                               String lastName,
                               String gender,
                               String birthPlace,
                               String barangay,
                               String civilStatus,
                               String educationalAttainment,
                               String status,
                               String userType,
                               String userRFID,
                               long birthDate) {

            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthPlace = birthPlace;
            this.barangay = barangay;
            this.civilStatus = civilStatus;
            this.educationalAttainment = educationalAttainment;
            this.status = status;
            this.userType = userType;
            this.userRFID = userRFID;
            this.birthDate = birthDate;
        }

        public ResidentBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ResidentBuilder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public ResidentBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ResidentBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public ResidentBuilder setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
            return this;
        }

        public ResidentBuilder setCivilStatus(String civilStatus) {
            this.civilStatus = civilStatus;
            return this;
        }

        public ResidentBuilder setEducationalAttainment(String educationalAttainment) {
            this.educationalAttainment = educationalAttainment;
            return this;
        }

        public ResidentBuilder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public ResidentBuilder setLotBlockPhase(String lotBlockPhase) {
            this.lotBlockPhase = lotBlockPhase;
            return this;
        }

        public ResidentBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public ResidentBuilder setSubdivisionVillageZone(String subdivisionVillageZone) {
            this.subdivisionVillageZone = subdivisionVillageZone;
            return this;
        }

        public ResidentBuilder setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public ResidentBuilder setOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public ResidentBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public ResidentBuilder setLandline(String landline) {
            this.landline = landline;
            return this;
        }

        public ResidentBuilder setUserType(String userType) {
            this.userType = userType;
            return this;
        }

        public ResidentBuilder setUserRFID(String userRFID) {
            this.userRFID = userRFID;
            return this;
        }

        public ResidentBuilder setBirthDate(long birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Resident build(){
            return new Resident(this);
        }
    }

}
