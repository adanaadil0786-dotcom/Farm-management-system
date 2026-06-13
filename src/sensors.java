public class sensors {

    private int    sensorId;
    private String sensorType;
    private int    value;
    private String status;

    public sensors(int sensorId, String sensorType) {
        this.sensorId   = sensorId;
        this.sensorType = sensorType;
        this.value      = 0;
        this.status     = "Idle";
    }

    public sensors(int sensorId, String sensorType, int value, String status) {
        this.sensorId   = sensorId;
        this.sensorType = sensorType;
        this.value      = value;
        this.status     = status;
    }

    public void generateReading() {
        value  = (int)(20 + Math.random() * 80);
        status = value > 50 ? "High" : "Normal";
    }

    public void displayData() {
        System.out.println("Sensor ID: "   + sensorId);
        System.out.println("Sensor Type: " + sensorType);
        System.out.println("Value: "       + value);
        System.out.println("Status: "      + status);
    }

    // Getters
    public int    getSensorId()   { return sensorId; }
    public String getSensorType() { return sensorType; }
    public int    getValue()      { return value; }
    public String getStatus()     { return status; }

    // Setters
    public void setSensorId(int id)         { this.sensorId = id; }
    public void setSensorType(String type)  { this.sensorType = type; }
    public void setValue(int value)         { this.value = value; }
    public void setStatus(String status)    { this.status = status; }
}