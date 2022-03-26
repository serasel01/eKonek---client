package com.example.barangayservicesui.certificates;

import com.spire.doc.Document;
import com.spire.doc.documents.BookmarksNavigator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class Certificate {
    private String name;
    private String address;
    private String civilStatus;
    private String sex;
    private int age;
    private String birthDate;
    private LocalDate dateIssued;
    private Document document;

    public Certificate(String name,
                       String address,
                       String civilStatus,
                       String sex,
                       int age,
                       String birthDate,
                       LocalDate dateIssued) {

        this.name = name;
        this.address = address;
        this.civilStatus = civilStatus;
        this.sex = sex;
        this.age = age;
        this.birthDate = birthDate;
        this.dateIssued = dateIssued;
    }

    public abstract Map<String, String> mapDocContent();
    public abstract void saveCertificate(Document document) throws IOException;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getFormattedDateIssued() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL dd, yyyy");
        return getDateIssued().format(formatter);
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Document createCertificate(Map<String, String> map, Document document){
        //Replace bookmark content with text
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //Locate the bookmark
            BookmarksNavigator bookmarkNavigator =
                    new BookmarksNavigator(document);
            bookmarkNavigator.moveToBookmark(entry.getKey());
            bookmarkNavigator.replaceBookmarkContent(
                    entry.getValue(), true);
        }

        return document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
