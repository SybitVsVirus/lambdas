package client;

public class PatientRequest {

    String phoneNumber;
    String regionAtRisk;
    String patientId;
    String contact;
    String fever;
    String coughing;
    String otherSymptoms;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegionAtRisk() {
        return regionAtRisk;
    }

    public void setRegionAtRisk(String regionAtRisk) {
        this.regionAtRisk = regionAtRisk;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getCoughing() {
        return coughing;
    }

    public void setCoughing(String coughing) {
        this.coughing = coughing;
    }

    public String getOtherSymptoms() {
        return otherSymptoms;
    }

    public void setOtherSymptoms(String otherSymptoms) {
        this.otherSymptoms = otherSymptoms;
    }
}