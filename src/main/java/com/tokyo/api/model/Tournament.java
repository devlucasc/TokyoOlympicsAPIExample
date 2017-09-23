package com.tokyo.api.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tournament {

    public enum StageType {
        ELIMINATORIAS("eliminatoria"), OITAVAS_DE_FINAL("oitavas"),
        QUARTAS_DE_FINAL("quartas"), SEMIFINAL("semifinal"), FINAL("final");

        private final String stage;
        StageType(String stage) { this.stage = stage; }
        public String getValue() { return stage; }

        public static StageType fromString(String text) {
            for (StageType b : StageType.values()) {
                if (b.stage.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }

	private long id;

    @NotNull
	private String modality;

    @NotNull
	private String location;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;

    @NotNull
	private String country1;

    @NotNull
	private String country2;

    @NotNull
	private StageType stage;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public Tournament(){
        id=0;
    }

    public Tournament(long id, String modality, String location, Date startDate, Date endDate,
                      String country1, String country2, StageType stage){
        this.id = id;
        this.modality = modality;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.country1 = country1;
        this.country2 = country2;
        this.stage = stage;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public StageType getStage() {
        return stage;
    }

    public void setStage(StageType stage) {
        this.stage = stage;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tournament other = (Tournament) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return "Tournament [id=" + id + ", modality=" + modality + ", location=" + location
                + ", stage=" + stage.getValue() + ", startDate=" + df.format(startDate)
                + ", endDate=" + df.format(endDate) +", country1=" + country1
                + ", country2=" + country2 + "]";
    }
}
