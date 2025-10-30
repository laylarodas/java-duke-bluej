
/**
 * Write a description of class Parte4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Parte4 {
    public void printYoutubeLinks(){
        //leer la url palabra por palabra
        FileResource ur = new FileResource("manyLinks.html");
        for(String word : ur.words()){//procesar cada palabra
            String lower = word.toLowerCase();//normalizar
            int yt = lower.indexOf("youtube.com");//buscamos si contiene youtube.com
            if(yt == -1)continue;//si no esta, que para a la siguiente palabra
        
            //si esta, buscamos las comillas antes y despues de youtube.com
            int startQuote = word.lastIndexOf("\"", yt); 
            int endQuote   = word.indexOf("\"", yt);
            //extraer el enlace entre comillas
            if (startQuote != -1 && endQuote != -1 && endQuote > startQuote) {
            String url = word.substring(startQuote + 1, endQuote);//aqui usamos word para respetar las mayusculas y minusculas del enlace original
            System.out.println(url);
            }
        }
    }
    public static void main(String[] args){
        Parte4 p = new Parte4();
        p.printYoutubeLinks();
    }
}
