
/**
 * Write a description of class BabyNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyNames {
    //totalBirths ampliado
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalGirls = 0;
        int totalBoys = 0;
        int uniqueGirls = 0;
        int uniqueBoys = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) { //false --> no header row, las columnas se acceden por indice numerico
            String name = rec.get(0);//posicion name
            String gender = rec.get(1);//posicion genero
            int births = Integer.parseInt(rec.get(2));

            totalBirths += births;
            if (gender.equals("F")) {
                totalGirls += births;
                uniqueGirls++;      // cada línea es un nombre único
            } else {
                totalBoys += births;
                uniqueBoys++;
            }
        }
        
        System.out.println("Total births = " + totalBirths);
        System.out.println("Girls births = " + totalGirls);
        System.out.println("Boys births  = " + totalBoys);
        System.out.println("Unique girl names = " + uniqueGirls);
        System.out.println("Unique boy names  = " + uniqueBoys);
        System.out.println("Total unique names = " + (uniqueGirls + uniqueBoys));
    }
    public int getRank(int year, String name, String gender) {
        //abrir el archivo del año correspondiente
        String fname = "data/yob" + year + ".csv"; 
        FileResource fr = new FileResource(fname);
        
        //inicializar el contador de ranking
        int rank = 0;
        
        for (CSVRecord record : fr.getCSVParser(false)) {
            String recName = record.get(0);    
            String recGender = record.get(1);  
            
            //si coincide el genero,sumamos al ranking
            if (recGender.equals(gender)) {
                rank++;
                if(recName.equals(name)){
                    return rank; //si coincide nombre tambien, devolvemos rank
                }
            }
        }
        return -1;
    }
    public String getName(int year, int rank, String gender) {
        String fname = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
    
        int currentRank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                currentRank++;
                if (currentRank == rank) {//cuando el contador alcance el rank buscado, devuelve el nombre
                    return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        // rank original en 'year'
        int originalRank = getRank(year, name, gender);
        if (originalRank == -1) {
            System.out.println("No se encontró el nombre '" + name + "' (" + gender + ") en " + year + ".");
            return;
        }
    
        // nombre equivalente en 'newYear' con el mismo rank
        String equivalentName = getName(newYear, originalRank, gender);
        if ("NO NAME".equals(equivalentName)) {
            System.out.println("En " + newYear + " no existe el puesto #" + originalRank + " para genero " + gender + ".");
            return;
        }
        System.out.println(name + " nacido/a en " + year +
            " estaría en el puesto #" + originalRank + ". Si naciera en " + newYear +
            ", se llamaría " + equivalentName + ".");
    }
    //año del mejor ranking
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource(); // seleccionas múltiples archivos
        int bestYear = -1;
        int bestRank = Integer.MAX_VALUE;

        for (File f : dr.selectedFiles()) {
            int year = extractYear(f.getName());       // ej: "yob2012.csv" -> 2012
            FileResource fr = new FileResource(f);     // leemos ese archivo concreto

            int rank = getRankInFile(fr, name, gender);
            if (rank != -1 && rank < bestRank) {
                bestRank = rank;
                bestYear = year;
            }
        }
        return bestYear; // si nunca se actualizó, quedará -1
    }
    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        double sum = 0.0;
        int count = 0;
    
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int rank = getRankInFile(fr, name, gender); // mismo helper del método anterior
            if (rank != -1) {
                sum += rank;
                count++;
            }
        }
        return (count == 0) ? -1.0 : (sum / count);
    }
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        String fname = "data/yob" + year + ".csv"; // archivos completos
        FileResource fr = new FileResource(fname);
    
        int totalHigher = 0;
    
        for (CSVRecord rec : fr.getCSVParser(false)) {
            String recName = rec.get(0);
            String recGender = rec.get(1);
            int births = Integer.parseInt(rec.get(2));
    
            if (!recGender.equals(gender)) continue; // solo mismo género
    
            // Si llegamos al nombre objetivo, parar (no se suma)
            if (recName.equals(name)) {
                return totalHigher;
            }
    
            // Todavía estamos por arriba en el ranking → sumamos
            totalHigher += births;
        }
    
        // Si no se encontró el nombre, se devuelve todo lo acumulado (ese género completo)
        return totalHigher;
    }
    //HELPERS
    // Helper: calcula rank dentro de un FileResource (sin abrir por ruta/añ0)
    private int getRankInFile(FileResource fr, String name, String gender) {
        int rank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) return rank;
            }
        }
        return -1;
    }
    // Helper: extrae el año de un nombre de archivo tipo "yob2012.csv"
    private int extractYear(String filename) {
        // Tomamos solo los dígitos del nombre de archivo
        String digits = filename.replaceAll("\\D", "");
        return (digits.isEmpty()) ? -1 : Integer.parseInt(digits);
    }
    //TESTERS
    public void testGetTotalBirthsRankedHigher() {
        System.out.println("Higher than Mason (M) 2012 = " +
            getTotalBirthsRankedHigher(2012, "Mason", "M")); // debería ser nacimientos del rank 1 (Jacob en 2012)
    
        System.out.println("Higher than Sophia (F) 2012 = " +
            getTotalBirthsRankedHigher(2012, "Sophia", "F")); // 0, porque Sophia es #1
    
        System.out.println("Higher than Maomao (F) 2012 = " +
            getTotalBirthsRankedHigher(2012, "Maomao", "F")); // suma de todas las F en 2012
    }
    public void testGetAverageRank() {
        // Selecciona, por ejemplo, 2010–2014 completos
        System.out.println("Avg rank Mason (M) = " + getAverageRank("Mason", "M"));
        System.out.println("Avg rank Sophia (F) = " + getAverageRank("Sophia", "F"));
        System.out.println("Avg rank Maomao (F) = " + getAverageRank("Maomao", "F")); // -1.0
    }
    public void testYearOfHighestRank() {
        // Selecciona varios archivos (por ejemplo 2010–2014) cuando te lo pida
        int year = yearOfHighestRank("Mason", "M");
        System.out.println("Mejor año para Mason (M) = " + year);

        year = yearOfHighestRank("Sophia", "F");
        System.out.println("Mejor año para Sophia (F) = " + year);

        year = yearOfHighestRank("Maomao", "F");
        System.out.println("Mejor año para Maomao (F) = " + year); // -1
    }
    public void testWhatIsNameInYear() {
        // Con archivos completos 2012:
        // Mason (M) es rank 2 en 2012, así que en 2014 (por ejemplo)
        // debería mapearse al nombre rank 2 de M en 2014.
        whatIsNameInYear("Mason", 2012, 2014, "M");
        // Ejemplo donde el nombre no existe como F en 1880:
        whatIsNameInYear("Maomao", 2012, 2014, "F");
        // Prueba con Sophia (F, rank 1 en 2012):
        whatIsNameInYear("Sophia", 2012, 2014, "F");
    }
    public void testGetName() {
        System.out.println(getName(2012, 2, "M")); 
        System.out.println(getName(2012, 1, "F")); 
        System.out.println(getName(2012, 99999, "F")); // NO NAME
    }
    public void testTotalBirths() {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    public void testGetRank(){
        System.out.println(getRank(2012, "Mason", "M"));
        System.out.println(getRank(2012, "Maomao", "F"));
        System.out.println(getRank(2012, "Sophia", "F"));
    }
}