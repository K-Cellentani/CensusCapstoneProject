# Census Capstone Project: HTTP Interfaces with Spring Boot and PostgresSQL database intergration

The capstone project focuses on creating HTTP interfaces using Spring Boot, facilitating users to contribute census data to a PostgreSQL database. The project demonstrates skills in Java for backend development, JDBC connections, database management, and web application design. It also integrates Python scripts for dynamic chart generation and utilizes Thymeleaf for dynamic data binding.

## Package `com.data`

The capstone project utilizes census data from the County Business Patterns as the base set of data for the years 2019, 2020, and 2021. The CSV datasets downloaded from the Census Bureau can be found here:

- [2019 Census Data](https://www.census.gov/data/datasets/2019/econ/cbp/2019-cbp.html)
- [2020 Census Data](https://www.census.gov/data/datasets/2020/econ/cbp/2020-cbp.html)
- [2021 Census Data](https://www.census.gov/data/datasets/2021/econ/cbp/2021-cbp.html)


### `CSVToPostgres.java`

- Establishes a JDBC connection to the PostgreSQL database.
- Creates tables for each year of census data if they don't exist, defining their structure based on the CSV files.
- Loads data from CSV files into the respective tables using the PostgreSQL COPY command, handling CSV format specifics.

### `TranslateData.java`

- Connects to the PostgreSQL database using JDBC.
- Alters the data types of certain columns in each year's table to ensure numeric values are stored correctly.
- Adds a new column `Year` to each table and populates it with the corresponding year values (2019, 2020, 2021).

### `CreateConsTable.java`

- Connects to the PostgreSQL database using JDBC.
- Creates an empty consolidate table to hold all the census data into one table (`census_table`).
- Filters and aggregates data from each year based on specific conditions using SQL queries, providing a summarized view of the census data.

In summary, these files collectively manage the process of creating tables, loading data from CSV files into a PostgreSQL database, and performing necessary data transformations to ensure consistency and accuracy in the representation of census data for the specified years.

## Spring-Boot Java Files `website package`

1. **HandlingFormSubmissionApplication.java (main file):**
   - The main class to run the Spring Boot application.
   - Configures component scanning for relevant packages.
   - Uses `SpringApplication.run` to start the application.

2. **DataForm.java:**
   - Initializes variables of state, year, establishments, employment, and payroll.
   - Provides getter and setter methods for each variable.

3. **CensusControllerApplication.java:**
   - A Spring Boot controller handling HTTP requests.
   - Uses `CensusJDBCPost` and `NationalAverages` for JDBC operations, submitting data to the database and generating charts.
   - Defines methods for handling form submissions (`censusSubmit`), displaying forms (`censusForm`), and generating charts (`censusChart`). 

4. **CensusJDBCPost.java:**
   - A configuration class for posting census data to a PostgreSQL database using Spring JDBC.
   - Uses `JdbcTemplate` to execute SQL queries for adding census data.

5. **Chart.java:**
   - Represents the user's chart request.
   - Contains a data field to store the selected chart type.
   - Provides getter and setter methods.

6. **NationalAverages:**
   - A Spring component managing the retrieval and processing of census data for chart generation.
   - Uses `JdbcTemplate` for querying PostgreSQL for data.
   - Generates a CSV file (`chartResult.csv`) based on user-selected data type (establishments, employment, annual payroll).
   - Starts the Python script (`pychart.py`) to create a chart image and also passes the timestamp variable to add to the image name.

7. **pychart.py:**
   - A Python script using pandas and matplotlib to generate a chart image.
   - Reads data from `chartResult.csv` and plots the chart.
   - Saves the chart image as `image_{timestamp}.jpg` in the static resources folder.

This ensemble of files forms a web application allowing users to submit census data through a form, store the data in a PostgreSQL database, and visualize national averages through dynamically generated charts. The Python script enhances the functionality by creating chart images for user interaction.

## HTML Files `templates package`

1. **dataform.html:**
   - In the first container, the user can submit new data to the census database. Binds to the `DataForm` object in the `CensusControllerApplication`. Represents the form data fields such as state, year, establishments, employment, and payroll.
   - The second container allows the user to request a chart of the selected field. This variable is called `data` which is used above in the `Chart.java` and `NationalAverages.java` files.

2. **submission.html:**
   - Displays the submitted data, i.e., the following variables: `${dataForm.state}`, `${dataForm.year}`, `${dataForm.estab}`, `${dataForm.employ}`, `${dataForm.payroll}`.

3. **chart.html:**
   - The page displays the selected chart data field and the corresponding chart image.
   - The image source (`th:src`) is dynamically generated based on the timestamp.

Overall, Thymeleaf is used for dynamic data binding between Java controllers and HTML templates. The variables are passed back and forth seamlessly, enabling the rendering of dynamic content in the web application. Thymeleaf expressions are used to embed Java data into HTML views, making the web pages dynamic and responsive to user interactions.

## For a viewing of a live run see the video below:

https://github.com/K-Cellentani/CensusCapstoneProject/assets/145611899/0cae9098-d364-40a7-a5c7-c23f8a672bfa

