package website.CensusController;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class CensusJDBCPost {
    private final JdbcTemplate jdbcTemplate;

    public CensusJDBCPost(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCensusData(DataForm dataForm) {
        String state = dataForm.getState();
        int year = Integer.parseInt(dataForm.getYear());
        long estab = dataForm.getEstab();
        long employ = dataForm.getEmploy();
        long payroll = dataForm.getPayroll();


        String sql = "INSERT INTO census_table (state, year, number_of_establishments, employment, annual_payroll_in_thousands) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (state, year) DO UPDATE SET " +
                "number_of_establishments = EXCLUDED.number_of_establishments, " +
                "employment = EXCLUDED.employment, " +
                "annual_payroll_in_thousands = EXCLUDED.annual_payroll_in_thousands";

        jdbcTemplate.update(sql, state, year, estab, employ, payroll);

    }
}

