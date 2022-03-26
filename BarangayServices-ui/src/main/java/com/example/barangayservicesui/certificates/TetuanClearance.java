package com.example.barangayservicesui.certificates;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TetuanClearance extends Certificate{
    private final String template = "src/main/resources/certificates/Tetuan/BARANGAY-CLEARANCE.docx";

    public TetuanClearance(String name,
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
        mapContent.put("address", getAddress());
        mapContent.put("dateIssued", getFormattedDateIssued());
        mapContent.put("dateIssued2", getFormattedDateIssued());
        return mapContent;
    }

    @Override
    public void saveCertificate(Document document) throws IOException {
        String fileName = "src/main/resources/CreatedCertificates/" +
                getName() + "-TetuanClearance.doc";

        document.saveToFile(fileName,
                FileFormat.Docm_2013);

        Desktop.getDesktop()
                .open(new File(fileName));
    }
}
