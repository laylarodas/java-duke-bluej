# 👶 Baby Names Analysis (Java + Duke CSV Library)

A Java mini-project from the **Duke University / Coursera “Java Programming”** course.  
It analyzes historical U.S. baby name data (`yobYYYY.csv`) provided by the SSA (Social Security Administration)  
to explore name popularity, rankings, and gender-based trends over multiple years.

---

## 🧭 Project Overview
This program reads CSV files containing baby name data and allows you to:
- Count total births and unique names per gender.
- Retrieve the rank of a specific name in a given year.
- Find the name that has a specific rank.
- Compare name popularity across different years.
- Find the year of the highest rank for a given name.
- Compute the average rank of a name over multiple years.
- Calculate the total births of names ranked higher than a given one.

All methods follow the *Duke University programming style* using `edu.duke.FileResource` and `org.apache.commons.csv`.

---

## 🗂️ Folder Structure

```
BabyNames/
├── data/
│ ├── yob2010.csv
│ ├── yob2011.csv
│ ├── yob2012.csv
│ ├── yob2013.csv
│ └── yob2014.csv
├── BabyNames.java
└── README.md

```


> 📁 Place all CSV files under the `/data` directory.  
> These files **must not include headers** — each row follows the format:  
> `Name,Gender,NumberOfBirths`.

---

## ⚙️ Implemented Methods

| Method | Description |
|:--|:--|
| `totalBirths(FileResource fr)` | Counts total births and unique names by gender. |
| `getRank(int year, String name, String gender)` | Returns the rank of a name for the given year and gender (1 = most popular). |
| `getName(int year, int rank, String gender)` | Returns the name that has the specified rank. |
| `whatIsNameInYear(String name, int year, int newYear, String gender)` | Prints the equivalent name in another year with the same popularity rank. |
| `yearOfHighestRank(String name, String gender)` | Returns the year with the highest (best) rank for the name. |
| `getAverageRank(String name, String gender)` | Calculates the average rank across multiple selected years. |
| `getTotalBirthsRankedHigher(int year, String name, String gender)` | Sums all births for names of the same gender that are ranked higher. |

---

## 🧪 Example Outputs

```text
Total births = 3665115
Girls births = 1753922
Boys births  = 1911193
Unique girl names = 18018
Unique boy names  = 13077
Total unique names = 31095

getRank(2012, "Mason", "M") → 2
getName(2012, 1, "F") → Sophia

Sophia born in 2012 would be Emma if born in 2014.

yearOfHighestRank("Mason", "M") → 2011
getAverageRank("Sophia", "F") → 1.6
getTotalBirthsRankedHigher(2012, "Mason", "M") → 19030

## 🧰 Dependencies

- `edu.duke` package (provided by Duke University course library)
- `org.apache.commons.csv` for parsing CSV files
- Java 8 or higher

---

## 🧑‍💻 How to Run

1. Open the project in **BlueJ** or **any Java IDE**.
2. Make sure the folder `data/` contains your CSV files.
3. Compile and run the tester methods from `BabyNames.java`, for example:
   ```java
   testTotalBirths();
   testGetRank();
   testGetName();
   testWhatIsNameInYear();
   testYearOfHighestRank();
   testGetAverageRank();
   testGetTotalBirthsRankedHigher();

4. For methods using multiple files, a directory chooser window will open — select the desired CSV files.

---

## 📈 Example Dataset Format

```
Sophia,F,220568
Emma,F,203004
Olivia,F,172509
Jacob,M,195603
Mason,M,191030
Ethan,M,180050

```


---

## ✨ Author

**Layla Rodas**  
🎓 Java Developer in training — *DAM Student (Desarrollo de Aplicaciones Multiplataforma)*  
📚 Learning Java, Spring Boot, AWS, and Android  
🔗 [GitHub Portfolio](https://github.com/laylarodas)

---

> _"Data becomes meaningful when we learn to ask the right questions."_ 💡
