package com.example.barangayservicesui.certificates;

import com.example.BarangayServicesclient.models.Resident;
import java.time.LocalDate;

public class CertificateFactory {
    public Certificate getCertificate(String certType, Resident resident){

        switch (certType){
            case "Certification":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaCertification(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanCertification(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanCertification(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Clearance":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaClearance(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanClearance(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanClearance(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Indigency":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaIndigent(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanIndigent(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanIndigent(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Residency":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaResidency(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanResidency(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanResidency(resident.getFullName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getFormattedBirthDate(),
                            LocalDate.now());
                }
                break;

        }
        return null;
    }
}
