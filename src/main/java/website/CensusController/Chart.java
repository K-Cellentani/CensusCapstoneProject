package website.CensusController;


public class Chart {

    public String data;
    public String getData() {
        return data;
    }
    public void setData (String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "RequestChart{" +
                "data='" + data +
                '}';
    }

}
