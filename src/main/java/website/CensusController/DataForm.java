package website.CensusController;


public class DataForm {

    private String state;
    public String getState() {
        return state;
    }
    public void setState (String state) {
        this.state = state;
    }

    private String year;
    public String getYear() {
        return year;
    }
    public void setYear (String year) {
        this.year = year;
    }

    private long estab;
    public long getEstab() {
        return estab;
    }
    public void setEstab(long estab) {
        this.estab = estab;
    }

    private long employ;
    public long getEmploy() {
        return employ;
    }
    public void setEmploy(long employ) {
        this.employ = employ;
    }

    private long payroll;
    public long getPayroll() {
        return payroll;
    }
    public void setPayroll(long payroll) {
        this.payroll = payroll;
    }

    @Override
    public String toString() {
        return "DataForm{" +
                "state='" + state + '\'' +
                ", year='" + year + '\'' +
                ", estab=" + estab +
                ", employ=" + employ +
                ", payroll=" + payroll +
                '}';
    }

}
