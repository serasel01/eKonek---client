package com.example.BarangayServicesclient;

import com.example.BarangayServicesclient.models.Admin;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BarangayServicesClientApplicationTests {
	private static final String BASE_URI = "http://localhost:8080/api/";
	private static final String RESIDENT_URI = "Barangays/{barangay}/Residents/{userRFID}";
	private static final String RESIDENTS_URI = "Barangays/{barangay}/Residents";
	private static final String RFID_URI = "RFIDs/{userRFID}";

	private WebClient webClient = WebClient.create(BASE_URI);
	BarangayRESTClient barangayRESTClient = new BarangayRESTClient(webClient);

	@Test
	void contextLoads() {
	}

	@Test
	void getAllResidents(){
		String barangay = "Tumaga";
		List<Resident> residents = barangayRESTClient
				.getAllResidents(barangay);

		for (Resident resident: residents){
			Logging.printInfoLog(resident.toString());
		}
	}

	@Test
	void getSearchedResidents(){
		String barangay = "Tumaga";
		String param = "Last";

		List<Resident> residents = barangayRESTClient
				.getSearchedResidents(barangay, param);

		for (Resident resident: residents){
			Logging.printInfoLog(resident.toString());
		}
	}

	@Test
	void getAllLogs(){
		String barangay = "Tumaga";
		List<Log> logs = barangayRESTClient
				.getAllLogs(barangay);

		for (Log log: logs){
			Logging.printInfoLog(log.toString());
		}
	}


	@Test
	void getResidentByRFID(){
		String userRFID = "2306521785";
		String barangay = "Tumaga";
		Resident resident = barangayRESTClient
				.getResident(barangay, userRFID);
		assertEquals("Firsty", resident.getFirstName());
	}

	@Test
	void addResident() throws JsonProcessingException {
		Resident resident = new Resident();
		resident.setBarangay("Tumaga");
		resident.setUserType("Normal");
		resident.setSubdivisionVillageZone("Sub");
		resident.setStreet("Street");
		resident.setOccupation("Occupation");
		resident.setUserRFID("2301234540");
		resident.setMobileNumber("09231456857");
		resident.setFirstName("First");
		resident.setMiddleName("Mid");
		resident.setLastName("Last");
		resident.setLandline("4567898765");
		resident.setLotBlockPhase("Lot 1");
		resident.setEmailAddress("name@email.com");
		resident.setStatus("Alive");
		resident.setGender("Male");
		resident.setBirthPlace("Zamboanga");
		resident.setCivilStatus("Single");
		resident.setEducationalAttainment("College Graduate");
		resident.setBirthDate(1643527962);

		barangayRESTClient
				.addResident(
						resident.getBarangay(),
						resident.getUserRFID(),
						resident
				);
	}

	@Test
	void addLoginCreds() throws JsonProcessingException {
		Admin admin = new Admin("$2a$12$L2Y8vy5Dji3./ar1YPPlUepWFD.wLhqzDdDAD2dtzqIRgNyLRn1Wq");
		admin.setBarangay("Tumaga");

		Logging.printInfoLog(
				barangayRESTClient.addLoginCreds(
						"679873450",
						admin
				)
		);
	}

	@Test
	void addLog() throws JsonProcessingException {
		Log log = new Log(
				"456789",
				"987654",
				"Firsty Lastee",
				"First Last",
				"Account Creation",
				45678987);

		Logging.printInfoLog(barangayRESTClient
				.addLog("Tumaga", log));
	}

	@Test
	void updateResident() throws JsonProcessingException {
		Resident resident = new Resident();
		resident.setBarangay("Tumaga");
		resident.setUserType("Normal");
		resident.setSubdivisionVillageZone("Subdivision");
		resident.setStreet("Street");
		resident.setOccupation("Engineer");
		resident.setUserRFID("2306521740");
		resident.setMobileNumber("09231451234");
		resident.setFirstName("First");
		resident.setMiddleName("Mid");
		resident.setLastName("Last");
		resident.setLandline("9851234");
		resident.setLotBlockPhase("Lot 1");
		resident.setEmailAddress("name@email.com");
		resident.setStatus("Alive");
		resident.setGender("Male");
		resident.setBirthPlace("Zamboanga");
		resident.setCivilStatus("Single");
		resident.setEducationalAttainment("College Graduate");
		resident.setBirthDate(1643527962);

		barangayRESTClient
				.updateResident(
						resident.getBarangay(),
						resident.getUserRFID(),
						resident
				);
	}

	@Test
	void updateLoginCreds() throws JsonProcessingException {
		Admin admin = new Admin("$2a$12$L2Y8vy5Dji3./ar1YPPlUepWFD.wLhqzDdDAD2dtzqIRgNyLRn1Wq");
		admin.setBarangay("Guiwan");

		Logging.printInfoLog(
				barangayRESTClient.updateLoginCreds(
						"679873450",
						admin
				)
		);
	}

	@Test
	void deleteResident() throws JsonProcessingException {
		barangayRESTClient.deleteResident(
				"Tumaga",
				"2301234540"
		);
	}

	@Test
	void deleteLoginCreds() throws JsonProcessingException {
		barangayRESTClient.deleteLoginCreds("679873450");
	}
}
