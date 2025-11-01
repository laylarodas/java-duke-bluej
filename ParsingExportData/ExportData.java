
/**
 * Write a description of class ExportData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class ExportData {
    public void tester(){
        FileResource fr = new FileResource(); //seleccion de csv
        CSVParser parser = fr.getCSVParser();
        
        System.out.println("---- countryInfo(\"Nauru\") ----");
        System.out.println(countryInfo(parser,"Nauru"));
        
        parser = fr.getCSVParser(); //resetear parser antes de cada uso
        System.out.println("---- listExportersTwoProducts(\"gold\", \"diamonds\") ----");
        listExportersTwoProducts(parser, "gold", "diamonds");
        
        parser = fr.getCSVParser();
        System.out.println("---- numberOfExporters(\"gold\") ----");
        System.out.println(numberOfExporters(parser, "gold"));
        
        parser = fr.getCSVParser();
        System.out.println("---- bigExporters(\"$999.999.999.999\") ----");
        bigExporters(parser, "$999.999.999.999");
    }
    
    public String countryInfo(CSVParser parser, String country){
        for(CSVRecord record : parser){ //iteracion de cada registro
            String c = record.get("Country"); //leer la columna Country
            //comparar con el pais buscado
            if(c.equals(country)){
                //si coincide, extraemos exports y value
                String exports = record.get("Exports"); 
                String value = record.get("Value (dollars)");
                return c + ": " + exports + ": " + value;
            }
        }
        return "NOT FOUND";
    }
    
    public void listExportersTwoProducts(CSVParser parser, String item1, String item2){
        for(CSVRecord record : parser){
            String exports = record.get("Exports");
            if(exports.contains(item1)&& exports.contains(item2)){
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String item){
        int count = 0;
        for(CSVRecord record : parser){
            if(record.get("Exports").contains(item)){
                count++;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount){
        for(CSVRecord record : parser){
            String valueStr = record.get("Value (dollars)");
            if(valueStr.length() > amount.length()){
                System.out.println(record.get("Country") + " " + valueStr);
            }
        }
    }
}
