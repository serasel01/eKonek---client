package com.example.barangayservicesui.certificates;

import com.example.BarangayServicesclient.RESTFacade;
import com.example.BarangayServicesclient.models.Resident;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CertificateFactoryTest {

    @Test
    void getCertificate() {
        Resident resident = RESTFacade.getInstance().getResident("Tumaga", "2306521785");

        Certificate certificate = new CertificateFactory()
                .getCertificate("Certification", resident);
        certificate.createCertificate(certificate.mapDocContent(),
                certificate.getDocument());
        certificate.saveCertificate(certificate.getDocument());
    }

}