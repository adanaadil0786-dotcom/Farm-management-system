public class employees {
    int employeeId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    private int getSalary() {
        return salary;
    }

    private void setSalary(int salary) {
        this.salary = salary;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public employees(int employeeId, String employeename, int salary, String jobtype) {
        this.employeeId = employeeId;
        this.employeename = employeename;
        this.salary = salary;
        this.jobtype = jobtype;
    }

    String employeename;
    int salary;
    String jobtype;
}
