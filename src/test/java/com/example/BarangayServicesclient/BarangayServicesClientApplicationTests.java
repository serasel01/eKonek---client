package com.example.BarangayServicesclient;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BarangayServicesClientApplicationTests {
//	private static final String BASE_URI = "http://localhost:8080/api/";
//	private static final String RESIDENT_URI = "Barangays/{barangay}/Residents/{userRFID}";
//	private static final String RESIDENTS_URI = "Barangays/{barangay}/Residents";
//	private static final String RFID_URI = "RFIDs/{userRFID}";
//
//	private WebClient webClient = WebClient.create(BASE_URI);
//	RESTFacade RESTFacade = new RESTFacade(webClient);
//
//	@Test
//	void getResidentByRFID(){
//		String userRFID = "2306521785";
//		String barangay = "Tumaga";
//		Resident resident = RESTFacade
//				.getResident(barangay, userRFID);
//		assertEquals("Firsty", resident.getFirstName());
//		Logging.printInfoLog(resident.toString());
//	}

//	@Test
//	void addResident() throws JsonProcessingException {
//		Resident resident = new Resident();
//		resident.setBarangay("Tumaga");
//		resident.setUserType("Normal");
//		resident.setSubdivisionVillageZone("Sub");
//		resident.setStreet("Street");
//		resident.setOccupation("Occupation");
//		resident.setUserRFID("2301234540");
//		resident.setMobileNumber("09231456857");
//		resident.setFirstName("First");
//		resident.setMiddleName("Mid");
//		resident.setLastName("Last");
//		resident.setLandline("4567898765");
//		resident.setLotBlockPhase("Lot 1");
//		resident.setEmailAddress("name@email.com");
//		resident.setStatus("Alive");
//		resident.setGender("Male");
//		resident.setBirthPlace("Zamboanga");
//		resident.setCivilStatus("Single");
//		resident.setEducationalAttainment("College Graduate");
//		resident.setBirthDate(1643527962);
//
//		RESTFacade
//				.addResident(
//						resident.getBarangay(),
//						resident
//				);
//	}

//	@Test
//	void updateResident() throws JsonProcessingException {
//		Resident resident = new Resident();
//		resident.setBarangay("Tumaga");
//		resident.setUserType("Normal");
//		resident.setSubdivisionVillageZone("Subdivision");
//		resident.setStreet("Street");
//		resident.setOccupation("Engineer");
//		resident.setUserRFID("2301234540");
//		resident.setMobileNumber("09231451234");
//		resident.setFirstName("Firsti");
//		resident.setMiddleName("Midi");
//		resident.setLastName("Lasti");
//		resident.setLandline("9851234");
//		resident.setLotBlockPhase("Lot 1");
//		resident.setEmailAddress("name@email.com");
//		resident.setStatus("Alive");
//		resident.setGender("Male");
//		resident.setBirthPlace("Zamboanga");
//		resident.setCivilStatus("Single");
//		resident.setEducationalAttainment("College Graduate");
//		resident.setBirthDate(1643527962);
//
//		RESTFacade
//				.updateResident(
//						resident.getBarangay(),
//						resident
//				);
//	}

//	@Test
//	void deleteResident() throws JsonProcessingException {
//		RESTFacade.deleteResident(
//				"Tumaga",
//				"2301234540"
//		);
//	}
//
//	@Test
//	void getAllResidents(){
//		String barangay = "Tumaga";
//		String param = "Lastee";
//		List<Resident> residents = RESTFacade
//				.getResidents(barangay, ParameterType.ResidentName, param);
//
//		for (Resident resident: residents){
//			Logging.printInfoLog(resident.toString());
//		}
//	}
//
////	@Test
////	void getAllLogs(){
////		String barangay = "Tumaga";
////		String param = "Firsti";
////		List<Log> logs = RESTFacade
////				.getAllLogs(barangay,
////						ParameterType.ResidentName,
////						param);
////
////		for (Log log: logs){
////			Logging.printInfoLog(log.toString());
////		}
////	}
//
//	@Test
//	void getCases(){
//		String barangay = "Tumaga";
//		String userRFID = "3526989828";
//		List<Case> cases = RESTFacade
//				.getCases(barangay,
//						userRFID);
//
//		for (Case caseItem : cases){
//			Logging.printInfoLog(caseItem.toString());
//		}
//	}
//
////	@Test
////	void addLoginCreds() throws JsonProcessingException {
////		Admin admin = new Admin("$2a$12$L2Y8vy5Dji3./ar1YPPlUepWFD.wLhqzDdDAD2dtzqIRgNyLRn1Wq");
////		admin.setBarangay("Tumaga");
////
////		Logging.printInfoLog(
////				RESTFacade.addLoginCreds(
////						"679873450",
////						admin
////				)
////		);
////	}
//
//	@Test
//	void authenticateLogin() throws JsonProcessingException {
//		String userRFID = "679873450";
//		String password = "$2a$12$L2Y8vy5Dji3./ar1YPPlUepWFD.wLhqzDdDAD2dtzqIRgNyL";
//		String barangay = "Tumaga";
//		if (RESTFacade.authenticateLogin(userRFID, password, barangay)){
//			Logging.printInfoLog("LOGIN SUCCESS");
//		} else {
//			Logging.printInfoLog("LOGIN FAILED");
//		}
//
//	}

//	@Test
//	void updateLoginCreds() throws JsonProcessingException {
//		Admin admin = new Admin("$2a$12$L2Y8vy5Dji3./ar1YPPlUepWFD.wLhqzDdDAD2dtzqIRgNyLRn1Wq");
//		admin.setBarangay("Guiwan");
//
//		Logging.printInfoLog(
//				RESTFacade.updateLoginCreds(
//						"679873450",
//						admin
//				)
//		);
//	}
//
//	@Test
//	void deleteLoginCreds() throws JsonProcessingException {
//		RESTFacade.deleteLoginCreds("679873450");
//	}


//	@Test
//	void addCase() throws JsonProcessingException {
//		Case aCase = new Case();
//		aCase.setCaseId("C85MG908435");
//		aCase.setCaseName("Name of Case");
//		aCase.setDateFilled("August 11, 2010");
//		aCase.setDescription("Description of Case");
//
//		RESTFacade.addCase("Tumaga",
//				"3526989828",
//				aCase);
//	}

//	@Test
//	void addLog() throws JsonProcessingException {
//		Log log = new Log(
//				"456789",
//				"987654",
//				"Firsty Lastee",
//				"First Last",
//				"Resident Account Creation",
//				45678987);
//
//		Logging.printInfoLog(RESTFacade
//				.addLog("Tumaga", log));
//	}

    @Test
    void getAge() {
        Logging.printInfoLog(
                String.valueOf(Period.between(
                        LocalDate.parse("December 01, 1996", DateTimeFormatter.ofPattern("LLLL dd, yyyy")),
                        LocalDate.now()).getYears())
        );
    }

}
