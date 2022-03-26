package com.example.barangayservicesui.certificates;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TumagaIndigent extends Certificate{
    private final String template = "src/main/resources/certificates/Tumaga/CERTIFICATE-OF-INDIGENCY.docx";

    public TumagaIndigent(String name,
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
        mapContent.put("name2", getName());
        mapContent.put("address", getAddress());
        mapContent.put("age", String.valueOf(getAge()));
        mapContent.put("sex", getSex());
        mapContent.put("civilStatus", getCivilStatus());
        mapContent.put("birthDate", getBirthDate());
        mapContent.put("dateIssued", getFormattedDateIssued());
        return mapContent;
    }

    @Override
    public void saveCertificate(Document document) throws IOException {
        String fileName = "src/main/resources/CreatedCertificates/" +
                getName() + "-TumagaIndigent.doc";

        document.saveToFile(fileName,
                FileFormat.Docm_2013);

        Desktop.getDesktop()
                .open(new File(fileName));
    }
}
