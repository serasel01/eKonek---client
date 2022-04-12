package com.example.barangayservicesui.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextFileReader {
    private String data = "";

    public TextFileReader readFileSingleLine(String filePath){
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return this;
    }

    public ArrayList<String> readFileMultipleLines(String filePath){
        ArrayList<String> listData = new ArrayList<>();

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                listData.add(data);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        listData.sort(String.CASE_INSENSITIVE_ORDER);
        return listData;
    }

    public ArrayList<String> getStringArrayDataComma(){
        ArrayList<String> listData = new ArrayList<>();
        String[] arrayData = data.split(", ");

        for (int i = 0; i < arrayData.length; i++){
            listData.add(arrayData[i]);
        }

        return listData;
    }


}
