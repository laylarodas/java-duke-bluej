# 🌍 Parsing Export Data — CSV Practice

Mini-project from the Duke Java specialization focused on **reading and analyzing structured CSV data**.

### 🎯 Learning goals
- Work with the `FileResource` and `CSVParser` classes.
- Parse `.csv` files containing countries, exports, and trade values.
- Filter data by product or amount.
- Understand how structured text files can simulate simple databases.

### 🧰 Tech
Java · edu.duke library · Apache Commons CSV

### ▶️ How to run
Open in **BlueJ** and execute the `tester()` method in `ExportData.java`.  
When prompted, select the file `exports_small.csv` (or any CSV file with the same structure).

### 📊 Example output
```
---- countryInfo("Germany") ----
Germany: motor vehicles, machinery, chemicals: $1,547,000,000,000
---- listExportersTwoProducts("gold", "diamonds") ----
South Africa
---- numberOfExporters("gold") ----
1
---- bigExporters("$999,999,999") ----
Germany $1,547,000,000,000
United States $1,610,000,000,000
```

### 📚 Notes
CSV files are not included in this repository (see course materials for `exports_small.csv`).
