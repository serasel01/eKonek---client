package com.example.barangayservicesui.enums;

public enum EducationalAttainment {
    ElementaryUndergraduate("Elementary Undergraduate"),
    ElementaryGraduate("Elementary Graduate"),
    JuniorHighSchoolUndergraduate("Junior High School Undergraduate"),
    JuniorHighSchoolGraduate("Junior High School Graduate"),
    SeniorHighSchoolUndergraduate("Senior High School Undergraduate"),
    SeniorHighSchoolGraduate("Senior High School Graduate"),
    CollegeUndergraduate("College Undergraduate"),
    CollegeGraduate("College Graduate"),
    MasterUndergraduate("Master Undergraduate"),
    MasterDegree("Master Degree"),
    DoctorateUndergraduate("Doctorate Undergraduate"),
    DoctorateDegree("Doctorate Degree"),
    Vocational("Vocational");

    private String educationalAttainment;

    EducationalAttainment(String educationalAttainment) {
        this.educationalAttainment = educationalAttainment;
    }

    public String getEducationalAttainment() {
        return educationalAttainment;
    }
}
