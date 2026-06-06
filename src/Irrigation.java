class irrigations{
    public irrigations(int irrigationId, int level, String status) {
        this.irrigationId = irrigationId;
        this.level = level;
        this.status = status;
    }

    int irrigationId;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIrrigationId() {
        return irrigationId;
    }

    public void setIrrigationId(int irrigationId) {
        this.irrigationId = irrigationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    int level;
    String status;
}

