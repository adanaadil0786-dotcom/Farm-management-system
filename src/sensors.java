class sensors{
    int sensorId;

    public sensors(int sensorId, String sensortype, int value, String status) {
        this.sensorId = sensorId;
        this.sensortype = sensortype;
        this.value = value;
        this.status = status;
    }

    public sensors(int sensorId, int value, String status, String sensortype) {
        this.sensorId = sensorId;
        this.value = value;
        this.status = status;
        this.sensortype = sensortype;
    }

    String sensortype;
    int value;
    String status;
    sensors(int sensorId, String sensortype) {

        this.sensorId = sensorId;
        this.sensortype = sensortype;
    }

    // Generate reading
    void generateReading() {

        value = (int)(20 + Math.random() * 80);

        if(value > 50) {
            status = "High";
        }
        else {
            status = "Normal";
        }
    }

    // Display data
    void displayData() {

        System.out.println("Sensor ID: " + sensorId);
        System.out.println("Sensor Type: " + sensortype);
        System.out.println("Value: " + value);
        System.out.println("Status: " + status);
    }
}

