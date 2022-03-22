package com.example.barangayservicesui.certificates;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GuiwanIndigent extends Certificate{
    private final String template = "src/main/resources/certificates/Guiwan/CERTIFICATE-OF-INDIGENCY.docx";

    public GuiwanIndigent(String name,
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
        mapContent.put("dateIssued", getFormattedDateIssued());
        return mapContent;
    }

    @Override
    public void saveCertificate(Document document) {
        document.saveToFile( "CreatedCertificates/" +
                getName() + "-GuiwanIndigent.doc",
                FileFormat.Docm_2013
        );
    }
}
