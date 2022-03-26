package com.example.barangayservicesui.certificates;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StaMariaSenior extends Certificate{
    private final String template = "src/main/resources/certificates/StaMaria/CERTIFICATE-OF-SENIORCITIZEN.docx";
    private String birthPlace;

    public StaMariaSenior(String name,
                          String address,
                          String civilStatus,
                          String sex,
                          int age,
                          String birthDate,
                          LocalDate dateIssued) {
        super(name, address, civilStatus, sex, age, birthDate, dateIssued);
        this.setDocument(new Document(template));
    }

    @Override
    public Map<String, String> mapDocContent() {
        Map<String, String> mapContent = new HashMap<>();
        mapContent.put("name", getName());
        mapContent.put("age", String.valueOf(getAge()));
        mapContent.put("address", getAddress());
        mapContent.put("birthDate", getBirthDate());
        mapContent.put("birthPlace", getBirthPlace());
        mapContent.put("dateIssued", getFormattedDateIssued());
        return mapContent;
    }

    @Override
    public void saveCertificate(Document document) throws IOException {
        String fileName = "src/main/resources/CreatedCertificates/" +
                getName() + "-StaMariaSeniorCitizen.doc";

        document.saveToFile(fileName,
                FileFormat.Docm_2013);

        Desktop.getDesktop()
                .open(new File(fileName));
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public StaMariaSenior setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
        return this;
    }
}
