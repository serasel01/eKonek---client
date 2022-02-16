package com.example.BarangayServicesclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident {
    private String firstName, middleName, lastName, gender, birthPlace, barangay,
            civilStatus, educationalAttainment, emailAddress, lotBlockPhase, street, subdivisionVillageZone,
            mobileNumber, occupation, status, landline, userType, userRFID;
    private long birthDate;

    public String getBirthPlace() {
        return birthPlace;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getEducationalAttainment() {
        return educationalAttainment;
    }

    public void setEducationalAttainment(String educationalAttainment) {
        this.educationalAttainment = educationalAttainment;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLotBlockPhase() {
        return lotBlockPhase;
    }

    public void setLotBlockPhase(String lotBlockPhase) {
        this.lotBlockPhase = lotBlockPhase;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubdivisionVillageZone() {
        return subdivisionVillageZone;
    }

    public void setSubdivisionVillageZone(String subdivisionVillageZone) {
        this.subdivisionVillageZone = subdivisionVillageZone;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserRFID() {
        return userRFID;
    }

    public void setUserRFID(String userRFID) {
        this.userRFID = userRFID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
