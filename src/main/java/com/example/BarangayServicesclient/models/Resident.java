package com.example.BarangayServicesclient.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Resident {
    //Personal Info
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String qualifier = "";
    private String gender = "";
    private String civilStatus = "";
    private String votersID = "";
    private String nationality = "";
    private String birthPlace = "";
    private String birthDate = "";
    private boolean isPWD = false;

    //Address
    private String houseBuildingStreet = "";
    private String barangay = "";

    //Contact
    private String emailAddress = "";
    private String mobileNumber = "";
    private String landline = "";

    //Other Info
    private String religion = "";
    private String educationalAttainment = "";
    private String occupation = "";
    private String socialClass = "";
    private String bloodType = "";
    private int height = 0;
    private int weight = 0;

    //Family Background
    private String fatherFirstName = "";
    private String fatherMiddleName = "";
    private String fatherLastName = "";
    private String fatherQualifier = "";
    private String fatherBirthPlace = "";

    private String motherFirstName = "";
    private String motherMiddleName = "";
    private String motherLastName = "";
    private String motherQualifier = "";
    private String motherBirthPlace = "";

    private String spouseFirstName = "";
    private String spouseMiddleName = "";
    private String spouseLastName = "";
    private String spouseQualifier = "";

    private String userRFID = "";

    public Resident() {
    }

    public Resident(ResidentBuilder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.qualifier = builder.qualifier;
        this.gender = builder.gender;
        this.civilStatus = builder.civilStatus;
        this.votersID = builder.votersID;
        this.nationality = builder.nationality;
        this.birthPlace = builder.birthPlace;
        this.birthDate = builder.birthDate;
        this.isPWD = builder.isPWD;
        this.houseBuildingStreet = builder.houseBuildingStreet;
        this.barangay = builder.barangay;
        this.emailAddress = builder.emailAddress;
        this.mobileNumber = builder.mobileNumber;
        this.landline = builder.landline;
        this.religion = builder.religion;
        this.educationalAttainment = builder.educationalAttainment;
        this.occupation = builder.occupation;
        this.socialClass = builder.socialClass;
        this.bloodType = builder.bloodType;
        this.height = builder.height;
        this.weight = builder.weight;
        this.fatherFirstName = builder.fatherFirstName;
        this.fatherMiddleName = builder.fatherMiddleName;
        this.fatherLastName = builder.fatherLastName;
        this.fatherQualifier = builder.fatherQualifier;
        this.fatherBirthPlace = builder.fatherBirthPlace;
        this.motherFirstName = builder.motherFirstName;
        this.motherMiddleName = builder.motherMiddleName;
        this.motherLastName = builder.motherLastName;
        this.motherQualifier = builder.motherQualifier;
        this.motherBirthPlace = builder.motherBirthPlace;
        this.spouseFirstName = builder.spouseFirstName;
        this.spouseMiddleName = builder.spouseMiddleName;
        this.spouseLastName = builder.spouseLastName;
        this.spouseQualifier = builder.spouserQualifier;
        this.userRFID = builder.userRFID;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getCompleteName() {
        if (!getQualifier().equals("")){
            return getFirstName() + " " + getMiddleName() + " " + getLastName() + ", " + getQualifier();

        } else {
            return getFirstName() + " " + getMiddleName() + " " + getLastName();
        }

    }

    public int getAge() {
        return Period.between(
                LocalDate.parse(
                        "December 01, 1996",
                        DateTimeFormatter.ofPattern("LLLL dd, yyyy")),
                LocalDate.now()
        ).getYears();
    }

    public String getFullAddress(){
        return getHouseBuildingStreet() + ", " + getBarangay();
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

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getVotersID() {
        return votersID;
    }

    public void setVotersID(String votersID) {
        this.votersID = votersID;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isPWD() {
        return isPWD;
    }

    public void setPWD(boolean PWD) {
        isPWD = PWD;
    }

    public String getHouseBuildingStreet() {
        return houseBuildingStreet;
    }

    public void setHouseBuildingStreet(String houseBuildingStreet) {
        this.houseBuildingStreet = houseBuildingStreet;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEducationalAttainment() {
        return educationalAttainment;
    }

    public void setEducationalAttainment(String educationalAttainment) {
        this.educationalAttainment = educationalAttainment;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSocialClass() {
        return socialClass;
    }

    public void setSocialClass(String socialClass) {
        this.socialClass = socialClass;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getFatherMiddleName() {
        return fatherMiddleName;
    }

    public void setFatherMiddleName(String fatherMiddleName) {
        this.fatherMiddleName = fatherMiddleName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getFatherQualifier() {
        return fatherQualifier;
    }

    public void setFatherQualifier(String fatherQualifier) {
        this.fatherQualifier = fatherQualifier;
    }

    public String getFatherBirthPlace() {
        return fatherBirthPlace;
    }

    public void setFatherBirthPlace(String fatherBirthPlace) {
        this.fatherBirthPlace = fatherBirthPlace;
    }

    public String getMotherFirstName() {
        return motherFirstName;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public String getMotherMiddleName() {
        return motherMiddleName;
    }

    public void setMotherMiddleName(String motherMiddleName) {
        this.motherMiddleName = motherMiddleName;
    }

    public String getMotherLastName() {
        return motherLastName;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getMotherQualifier() {
        return motherQualifier;
    }

    public void setMotherQualifier(String motherQualifier) {
        this.motherQualifier = motherQualifier;
    }

    public String getMotherBirthPlace() {
        return motherBirthPlace;
    }

    public void setMotherBirthPlace(String motherBirthPlace) {
        this.motherBirthPlace = motherBirthPlace;
    }

    public String getSpouseFirstName() {
        return spouseFirstName;
    }

    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }

    public String getSpouseMiddleName() {
        return spouseMiddleName;
    }

    public void setSpouseMiddleName(String spouseMiddleName) {
        this.spouseMiddleName = spouseMiddleName;
    }

    public String getSpouseLastName() {
        return spouseLastName;
    }

    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }

    public String getSpouseQualifier() {
        return spouseQualifier;
    }

    public void setSpouseQualifier(String spouseQualifier) {
        this.spouseQualifier = spouseQualifier;
    }

    public String getUserRFID() {
        return userRFID;
    }

    public void setUserRFID(String userRFID) {
        this.userRFID = userRFID;
    }

    public static class ResidentBuilder {
        //Personal Info
        private String firstName;
        private String middleName;
        private String lastName;
        private String qualifier;
        private String gender;
        private String civilStatus;
        private String votersID;
        private String nationality;
        private String birthPlace;
        private String birthDate;
        private boolean isPWD;

        //Address
        private String houseBuildingStreet;
        private String barangay;

        //Contact
        private String emailAddress;
        private String mobileNumber;
        private String landline;

        //Other Info
        private String religion;
        private String educationalAttainment;
        private String occupation;
        private String socialClass;
        private String bloodType;
        private int height;
        private int weight;

        //Family Background
        private String fatherFirstName;
        private String fatherMiddleName;
        private String fatherLastName;
        private String fatherQualifier;
        private String fatherBirthPlace;

        private String motherFirstName;
        private String motherMiddleName;
        private String motherLastName;
        private String motherQualifier;
        private String motherBirthPlace;

        private String spouseFirstName;
        private String spouseMiddleName;
        private String spouseLastName;
        private String spouserQualifier;

        private String userRFID;

        public ResidentBuilder(String firstName,
                               String middleName,
                               String lastName,
                               String gender,
                               String civilStatus,
                               String nationality,
                               String birthPlace,
                               String birthDate,
                               boolean isPWD,
                               String houseBuildingStreet,
                               String barangay,
                               String fatherFirstName,
                               String fatherMiddleName,
                               String fatherLastName,
                               String fatherBirthPlace,
                               String motherFirstName,
                               String motherMiddleName,
                               String motherLastName,
                               String motherBirthPlace,
                               String userRFID) {

            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.gender = gender;
            this.civilStatus = civilStatus;
            this.nationality = nationality;
            this.birthPlace = birthPlace;
            this.birthDate = birthDate;
            this.isPWD = isPWD;
            this.houseBuildingStreet = houseBuildingStreet;
            this.barangay = barangay;
            this.fatherFirstName = fatherFirstName;
            this.fatherMiddleName = fatherMiddleName;
            this.fatherLastName = fatherLastName;
            this.fatherBirthPlace = fatherBirthPlace;
            this.motherFirstName = motherFirstName;
            this.motherMiddleName = motherMiddleName;
            this.motherLastName = motherLastName;
            this.motherBirthPlace = motherBirthPlace;
            this.userRFID = userRFID;
        }

        public String getFirstName() {
            return firstName;
        }

        public ResidentBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getMiddleName() {
            return middleName;
        }

        public ResidentBuilder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public ResidentBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public String getQualifier() {
            return qualifier;
        }

        public ResidentBuilder setQualifier(String qualifier) {
            this.qualifier = qualifier;
            return this;
        }

        public String getGender() {
            return gender;
        }

        public ResidentBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public String getCivilStatus() {
            return civilStatus;
        }

        public ResidentBuilder setCivilStatus(String civilStatus) {
            this.civilStatus = civilStatus;
            return this;
        }

        public String getVotersID() {
            return votersID;
        }

        public ResidentBuilder setVotersID(String votersID) {
            this.votersID = votersID;
            return this;
        }

        public String getNationality() {
            return nationality;
        }

        public ResidentBuilder setNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public ResidentBuilder setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
            return this;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public ResidentBuilder setBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public boolean isPWD() {
            return isPWD;
        }

        public ResidentBuilder setPWD(boolean PWD) {
            isPWD = PWD;
            return this;
        }

        public String getHouseBuildingStreet() {
            return houseBuildingStreet;
        }

        public ResidentBuilder setHouseBuildingStreet(String houseBuildingStreet) {
            this.houseBuildingStreet = houseBuildingStreet;
            return this;
        }

        public String getBarangay() {
            return barangay;
        }

        public ResidentBuilder setBarangay(String barangay) {
            this.barangay = barangay;
            return this;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public ResidentBuilder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public ResidentBuilder setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public String getLandline() {
            return landline;
        }

        public ResidentBuilder setLandline(String landline) {
            this.landline = landline;
            return this;
        }

        public String getReligion() {
            return religion;
        }

        public ResidentBuilder setReligion(String religion) {
            this.religion = religion;
            return this;
        }

        public String getEducationalAttainment() {
            return educationalAttainment;
        }

        public ResidentBuilder setEducationalAttainment(String educationalAttainment) {
            this.educationalAttainment = educationalAttainment;
            return this;
        }

        public String getOccupation() {
            return occupation;
        }

        public ResidentBuilder setOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public String getSocialClass() {
            return socialClass;
        }

        public ResidentBuilder setSocialClass(String socialClass) {
            this.socialClass = socialClass;
            return this;
        }

        public String getBloodType() {
            return bloodType;
        }

        public ResidentBuilder setBloodType(String bloodType) {
            this.bloodType = bloodType;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public ResidentBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        public int getWeight() {
            return weight;
        }

        public ResidentBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public String getFatherFirstName() {
            return fatherFirstName;
        }

        public ResidentBuilder setFatherFirstName(String fatherFirstName) {
            this.fatherFirstName = fatherFirstName;
            return this;
        }

        public String getFatherMiddleName() {
            return fatherMiddleName;
        }

        public ResidentBuilder setFatherMiddleName(String fatherMiddleName) {
            this.fatherMiddleName = fatherMiddleName;
            return this;
        }

        public String getFatherLastName() {
            return fatherLastName;
        }

        public ResidentBuilder setFatherLastName(String fatherLastName) {
            this.fatherLastName = fatherLastName;
            return this;
        }

        public String getFatherQualifier() {
            return fatherQualifier;
        }

        public ResidentBuilder setFatherQualifier(String fatherQualifier) {
            this.fatherQualifier = fatherQualifier;
            return this;
        }

        public String getFatherBirthPlace() {
            return fatherBirthPlace;
        }

        public ResidentBuilder setFatherBirthPlace(String fatherBirthPlace) {
            this.fatherBirthPlace = fatherBirthPlace;
            return this;
        }

        public String getMotherFirstName() {
            return motherFirstName;
        }

        public ResidentBuilder setMotherFirstName(String motherFirstName) {
            this.motherFirstName = motherFirstName;
            return this;
        }

        public String getMotherMiddleName() {
            return motherMiddleName;
        }

        public ResidentBuilder setMotherMiddleName(String motherMiddleName) {
            this.motherMiddleName = motherMiddleName;
            return this;
        }

        public String getMotherLastName() {
            return motherLastName;
        }

        public ResidentBuilder setMotherLastName(String motherLastName) {
            this.motherLastName = motherLastName;
            return this;
        }

        public String getMotherQualifier() {
            return motherQualifier;
        }

        public ResidentBuilder setMotherQualifier(String motherQualifier) {
            this.motherQualifier = motherQualifier;
            return this;
        }

        public String getMotherBirthPlace() {
            return motherBirthPlace;
        }

        public ResidentBuilder setMotherBirthPlace(String motherBirthPlace) {
            this.motherBirthPlace = motherBirthPlace;
            return this;
        }

        public String getSpouseFirstName() {
            return spouseFirstName;
        }

        public ResidentBuilder setSpouseFirstName(String spouseFirstName) {
            this.spouseFirstName = spouseFirstName;
            return this;
        }

        public String getSpouseMiddleName() {
            return spouseMiddleName;
        }

        public ResidentBuilder setSpouseMiddleName(String spouseMiddleName) {
            this.spouseMiddleName = spouseMiddleName;
            return this;
        }

        public String getSpouseLastName() {
            return spouseLastName;
        }

        public ResidentBuilder setSpouseLastName(String spouseLastName) {
            this.spouseLastName = spouseLastName;
            return this;
        }

        public String getSpouserQualifier() {
            return spouserQualifier;
        }

        public ResidentBuilder setSpouserQualifier(String spouserQualifier) {
            this.spouserQualifier = spouserQualifier;
            return this;
        }

        public String getUserRFID() {
            return userRFID;
        }

        public ResidentBuilder setUserRFID(String userRFID) {
            this.userRFID = userRFID;
            return this;
        }

        public Resident build(){
            return new Resident(this);
        }
    }

    @Override
    public String toString() {
        return "Resident{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", qualifier='" + qualifier + '\'' +
                ", gender='" + gender + '\'' +
                ", civilStatus='" + civilStatus + '\'' +
                ", votersID='" + votersID + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", isPWD=" + isPWD +
                ", houseBuildingStreet='" + houseBuildingStreet + '\'' +
                ", barangay='" + barangay + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", landline='" + landline + '\'' +
                ", religion='" + religion + '\'' +
                ", educationalAttainment='" + educationalAttainment + '\'' +
                ", occupation='" + occupation + '\'' +
                ", socialClass='" + socialClass + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", fatherFirstName='" + fatherFirstName + '\'' +
                ", fatherMiddleName='" + fatherMiddleName + '\'' +
                ", fatherLastName='" + fatherLastName + '\'' +
                ", fatherQualifier='" + fatherQualifier + '\'' +
                ", fatherBirthPlace='" + fatherBirthPlace + '\'' +
                ", motherFirstName='" + motherFirstName + '\'' +
                ", motherMiddleName='" + motherMiddleName + '\'' +
                ", motherLastName='" + motherLastName + '\'' +
                ", motherQualifier='" + motherQualifier + '\'' +
                ", motherBirthPlace='" + motherBirthPlace + '\'' +
                ", spouseFirstName='" + spouseFirstName + '\'' +
                ", spouseMiddleName='" + spouseMiddleName + '\'' +
                ", spouseLastName='" + spouseLastName + '\'' +
                ", spouserQualifier='" + spouseQualifier + '\'' +
                ", userRFID='" + userRFID + '\'' +
                '}';
    }
}
