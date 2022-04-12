package com.example.barangayservicesui.certificates;

import com.example.BarangayServicesclient.models.Resident;
import java.time.LocalDate;

public class CertificateFactory {
    public Certificate getCertificate(String certType, Resident resident){

        switch (certType){
            case "Certification":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaCertification(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanCertification(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanCertification(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("StaMaria")){
                    return new StaMariaCertification(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Clearance":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaClearance(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanClearance(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanClearance(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Indigency":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaIndigent(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanIndigent(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanIndigent(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("StaMaria")){
                    return new StaMariaIndigent(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Residency":
                if (resident.getBarangay().equals("Tumaga")){
                    return new TumagaResidency(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Guiwan")){
                    return new GuiwanResidency(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("Tetuan")){
                    return new TetuanResidency(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());

                } else if (resident.getBarangay().equals("StaMaria")){
                    return new StaMariaResidency(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());
                }
                break;

            case "Senior Citizen":
                if (resident.getBarangay().equals("StaMaria")){
                    return new StaMariaSenior(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now())
                            .setBirthPlace(resident.getBirthPlace());
                }
                break;

            case "Good Moral":
                if (resident.getBarangay().equals("StaMaria")){
                    return new StaMariaGoodMoral(resident.getCompleteName(),
                            resident.getFullAddress(),
                            resident.getCivilStatus(),
                            resident.getGender(),
                            resident.getAge(),
                            resident.getBirthDate(),
                            LocalDate.now());
                }
                break;

        }
        return null;
    }
}
