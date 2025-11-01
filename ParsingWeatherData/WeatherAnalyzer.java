
/**
 * Write a description of class WeatherAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class WeatherAnalyzer {
    //TESTERS
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();//obtiene el archivo
        CSVParser parser = fr.getCSVParser();//lo parsea
        
        //llamado al metodo
        CSVRecord coldest = coldestHourInFile(parser);
        if (coldest == null) {
            System.out.println("No valid temperature readings in file.");
            return;
        }

        //imprimir resultado temperatura y fecha
        System.out.println("Coldest temperature was " + coldest.get("TemperatureF") + " at " + coldest.get("DateUTC"));
        
    }
    public void testFileWithColdestTemperature(){
        //buscamos entre los archivos cual tiene la temperatura mas baja
        String fileName = fileWithColdestTemperature();
        System.out.println("Coldest day was in file: " + fileName);
        
        //luego listamos sus temperaturas
        FileResource fr = new FileResource(fileName); //abrir leer
        CSVParser parser = fr.getCSVParser();//parsear
        
        //utilizamos la lógica del metodo 1
        CSVRecord coldest = coldestHourInFile(parser);
        if (coldest == null) {
            System.out.println("No valid temperature readings in file.");
            return;
        }

        System.out.println("Coldest temperature on that day was " + coldest.get("TemperatureF"));
        
        //crear parser una vez usado
        parser = fr.getCSVParser();
        System.out.println("All the Temperatures on the coldest day were: ");
        for(CSVRecord record : parser){
            System.out.println(record.get("DateUTC") + ": " +  record.get("TemperatureF"));
        }
    }
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        
        CSVRecord record = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + record.get("Humidity") + " at " + record.get("DateUTC"));
    }
    public void testLowestHumidityInManyFiles(){
        CSVRecord record = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + record.get("Humidity") + " at " + record.get("DateUTC"));
    }
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        
        double avg = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + avg);
    }
    //temperatura promedio filtrando por humedad alta (>= value)
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        int threshold = 80;
        double avg = averageTemperatureWithHighHumidityInFile(parser, threshold);
        if (Double.isNaN(avg)) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + avg);
        }
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sum = 0.0;
        int count = 0;
        for(CSVRecord record : parser){
            String tempStr = record.get("TemperatureF");
            String humStr = record.get("Humidity");
            
            if(!isValidTemperature(tempStr)) continue;
            if(humStr.equals("N/A")) continue;
            
            int humidity = Integer.parseInt(humStr);
            if(humidity >= value){
                sum += Double.parseDouble(tempStr);
                count++;
            }
        }
        return (count == 0) ? Double.NaN : (sum / count);
    }
    public double averageTemperatureInFile(CSVParser parser){
        double sum = 0.0;
        int count = 0;
        for(CSVRecord record : parser){
            String tempStr = record.get("TemperatureF");
            if(!isValidTemperature(tempStr)) continue;
            sum += Double.parseDouble(tempStr);
            count++;
        }
        //si no hubo lecturas validas, devolvemos NaN
        return (count == 0) ? Double.NaN : (sum/count);
    }
    public CSVRecord lowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestSoFar = null;
        
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);//!!no olvidar pasar el f para leer
            CSVRecord currentLowest = lowestHumidityInFile(fr.getCSVParser());
            
            if(currentLowest == null) continue;
            
            if(lowestSoFar == null){
                lowestSoFar = currentLowest;
            } else {
                int current = Integer.parseInt(currentLowest.get("Humidity"));
                int lowest = Integer.parseInt(lowestSoFar.get("Humidity"));
                
                if(current < lowest){
                    lowestSoFar = currentLowest;
                }
            }
        }
        return lowestSoFar;
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        
        for(CSVRecord record : parser){
            String humStr = record.get("Humidity");
            
            //ignorar filas vacias
            if(humStr.equals("N/A")) continue;
            
            int hum = Integer.parseInt(humStr);//string --> int
            
            if(lowestSoFar == null){
                lowestSoFar = record;
            } else {
                int lowest = Integer.parseInt(lowestSoFar.get("Humidity"));
                if(hum < lowest){
                    lowestSoFar = record;
                }
            }
        }
        return lowestSoFar;
    }
    public String fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource();//permite seleccionar varios archivos
        CSVRecord coldestSoFar = null; //guardamos el registro mas frio global
        String coldestFileName = null;//nombre del archivo con el registro mas frio global
        
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentColdest = coldestHourInFile(fr.getCSVParser());
            
            if (currentColdest == null) continue; //saltar archivos sin datos válidos
            
            if(coldestSoFar == null){
                coldestSoFar = currentColdest;
                coldestFileName = f.getPath(); //guardamos la ruta 
            } else {
                double current = Double.parseDouble(currentColdest.get("TemperatureF"));
                double global = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                
                if(current < global){
                    coldestSoFar = currentColdest;
                    coldestFileName = f.getPath();
                }
            }
        }
        return coldestFileName;
    }
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestSoFar = null; //empieza en null, aun no hay 
        
        for(CSVRecord record: parser){
            String tempStr = record.get("TemperatureF");
            //chequear que sea valido
            if(!isValidTemperature(tempStr)) continue;
            
            double temp = Double.parseDouble(tempStr);//string --> Double
            
            if(coldestSoFar == null){
                coldestSoFar = record;
            } else {
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if(temp < coldestTemp){
                    coldestSoFar = record;
                }
            }
        }
        return coldestSoFar; //devuelve la fila completa2
    }
    // Comprueba si la temperatura es numérica y distinta del valor centinela -9999
    private boolean isValidTemperature(String tempStr){
        try {
            double t = Double.parseDouble(tempStr);
            return t != -9999;
        } catch(Exception e){
            return false;
        }
    }
}
