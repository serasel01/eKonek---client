package com.example.barangayservicesui.utils;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.BookmarksNavigator;

import java.util.HashMap;
import java.util.Map;

public class CertificateFiller {
    private Map<String, String> map;
    private Document doc;

    public CertificateFiller(Map<String, String> map, Document doc) {
        this.map = map;
        this.doc = doc;
    }

    public void fillInText(){
        //Replace bookmark content with text
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //Locate the bookmark
            BookmarksNavigator bookmarkNavigator = new BookmarksNavigator(doc);
            bookmarkNavigator.moveToBookmark(entry.getKey());
            bookmarkNavigator.replaceBookmarkContent(entry.getValue(), true);
        }

        doc.saveToFile("CreateByReplacingBookmarks.doc", FileFormat.Docm_2013);
    }
}
