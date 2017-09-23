package com.tokyo.api;

import com.tokyo.api.model.Tournament;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


class SpringBootRestTestClient {
 
    private static final String REST_SERVICE_URI = "http://localhost:8080/api";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static boolean listAllTournaments(){
        System.out.println("Testing get all tournaments API-----------");
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI + "/tournaments/", List.class);

            if (usersMap != null) {
                for (LinkedHashMap<String, Object> map : usersMap) {
                    System.out.println("Tournament : id=" + map.get("id") + ", modality=" + map.get("modality") + ", Country1=" + map.get("country1") + ", Country2=" + map.get("country2"));
                }
                return true;
            } else {
                System.out.println("No tournaments exists----------");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static boolean getTournament(String modality){
        System.out.println("Testing getTournament API----------");
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<LinkedHashMap<String, Object>> tournaments = restTemplate.getForObject(REST_SERVICE_URI + "/tournaments/" + modality, List.class);
            if (tournaments != null) {
                for (LinkedHashMap<String, Object> map : tournaments) {
                    System.out.println("Tournament : id=" + map.get("id") + ", modality=" + map.get("modality") + ", Country1=" + map.get("country1") + ", Country2=" + map.get("country2"));
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private static Date formatDate(String date) {
        if (date == null || date.equals(""))
            return null;
        Date datedt;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            datedt = formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
        return datedt;
    }
     
    /* POST */
    private static boolean addTournament(Tournament tournament) {
        try {
            System.out.println("Testing create Tournament API----------");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation(REST_SERVICE_URI + "/tournament/", tournament, Tournament.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean addDummy() {
        boolean check;
        //test day/location limit
        System.out.println("Testing day/location limits ...............");
        check = addTournament(new Tournament(0, "futebol", "estadio1",
                formatDate("21/09/2017 13:00:00"), formatDate("21/09/2017 13:30:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));
        check = check && addTournament(new Tournament(0, "futebol", "estadio1",
                formatDate("21/09/2017 13:30:00"), formatDate("21/09/2017 14:00:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));
        check = check && addTournament(new Tournament(0, "volei", "estadio1",
                formatDate("21/09/2017 14:00:00"), formatDate("21/09/2017 14:30:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));
        check = check && addTournament(new Tournament(0, "volei", "estadio1",
                formatDate("21/09/2017 14:30:00"), formatDate("21/09/2017 15:00:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));
        check = check && !addTournament(new Tournament(0, "futebol", "estadio1",
                formatDate("21/09/2017 15:00:00"), formatDate("21/09/2017 15:30:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));

        //test same country values
        System.out.println("Testing same country values ...............");
        check = check && !addTournament(new Tournament(0, "natacao", "estadio1",
                formatDate("22/09/2017 15:00:00"), formatDate("22/09/2017 15:30:00"),
                "BR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));
        check = check && addTournament(new Tournament(0, "natacao", "estadio1",
                formatDate("22/09/2017 15:00:00"), formatDate("22/09/2017 15:30:00"),
                "BR", "BR", Tournament.StageType.FINAL));

        //test min duration
        System.out.println("Testing duration ...............");
        check = check && !addTournament(new Tournament(0, "tenis", "estadio1",
                formatDate("23/09/2017 15:00:00"), formatDate("22/09/2017 15:20:00"),
                "CO", "BR", Tournament.StageType.FINAL));

        //test conflict
        System.out.println("Testing conflict ...............");
        check = check && addTournament(new Tournament(0, "corrida", "estadio1",
                formatDate("20/09/2017 13:00:00"), formatDate("20/09/2017 13:40:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));
        check = check && !addTournament(new Tournament(0, "corrida", "estadio1",
                formatDate("20/09/2017 13:20:00"), formatDate("20/09/2017 13:50:00"),
                "AR", "BR", Tournament.StageType.QUARTAS_DE_FINAL));

        //test null
        check = check && !addTournament(new Tournament(0, null, null,
                formatDate("18/09/2017 13:20:00"), formatDate("18/09/2017 13:50:00"),
                "WXZ", "XYZ", Tournament.StageType.QUARTAS_DE_FINAL));
        return check;
    }
 
    public static void main(String args[]) throws Exception {
        boolean check;
        check = !listAllTournaments();
        check = check && !getTournament("futebol");
        check = check && addDummy();
        check = check && listAllTournaments();
        check = check && getTournament("futebol");
        check = check && getTournament("volei");
        check = check && !getTournament("basquete");
        assert (check);
    }
}