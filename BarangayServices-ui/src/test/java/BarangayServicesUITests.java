import com.example.BarangayServicesclient.Logging;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.utils.CertificateFiller;
import com.spire.doc.Document;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarangayServicesUITests {
    private static final String SAMPLE_CERT = "C:\\Users\\Serasel\\IdeaProjects\\BarangayServices-client\\BarangayServices-ui\\src\\main\\resources\\com\\example\\barangayservicesui\\certificates\\stamaria\\Cert.-of-Residency-2020.docx";

    @Test
    void generateCertificate(){
        Map<String, String> map = new HashMap<String, String>();
        Document document = new Document(SAMPLE_CERT);

        map.put("name","First Last");
        map.put("age","30 years of age");
        map.put("lot","Lot 1");
        map.put("subdivision","Pineapple Subdivision");
        map.put("street","Asteroid Street");
        map.put("day","10TH");
        map.put("month","January");
        map.put("year","2022");

        CertificateFiller certificateFiller = new CertificateFiller(map, document);
        certificateFiller.fillInText();
    }
}
