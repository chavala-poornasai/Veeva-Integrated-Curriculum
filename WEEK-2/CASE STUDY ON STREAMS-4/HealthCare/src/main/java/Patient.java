
class Patient {
    int patient_id;
    String patient_name;
    String dept;
    String doctor_name;
    String diagnosis;
    String treatment_given;
    String patient_type;
    int heart_rate;
    int BP;
    int sugar_value;
    int severity;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment_given() {
        return treatment_given;
    }

    public void setTreatment_given(String treatment_given) {
        this.treatment_given = treatment_given;
    }

    public String getPatient_type() {
        return patient_type;
    }

    public void setPatient_type(String patient_type) {
        this.patient_type = patient_type;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getBP() {
        return BP;
    }

    public void setBP(int BP) {
        this.BP = BP;
    }

    public int getSugar_value() {
        return sugar_value;
    }

    public void setSugar_value(int sugar_value) {
        this.sugar_value = sugar_value;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    Patient(int patient_id, String patient_name, String dept,
            String doctor_name, String diagnosis,
            String treatment_given, String patient_type,
            int heart_rate, int BP, int sugar_value, int severity) {

        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.dept = dept;
        this.doctor_name = doctor_name;
        this.diagnosis = diagnosis;
        this.treatment_given = treatment_given;
        this.patient_type = patient_type;
        this.heart_rate = heart_rate;
        this.BP = BP;
        this.sugar_value = sugar_value;
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patient_id +
                ", Name: " + patient_name +
                ", Department: " + dept +
                ", Doctor: " + doctor_name +
                ", Diagnosis: " + diagnosis +
                ", Treatment: " + treatment_given +
                ", Patient Type: " + patient_type +
                ", Heart Rate: " + heart_rate +
                ", BP: " + BP +
                ", Sugar: " + sugar_value +
                ", Severity: " + severity;
    }
}
