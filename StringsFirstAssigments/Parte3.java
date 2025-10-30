
/**
 * Write a description of class Parte3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Parte3 {
    public boolean twoOccurrences(String stringa, String stringb){
        if (stringa == null || stringa.isEmpty()) {
            // Para este ejercicio, tratamos stringa vacío como caso no válido.
            return false;
        }
        String aLower = stringa.toLowerCase();
        String bLower = stringb.toLowerCase();
        int first = bLower.indexOf(aLower);
        if (first == -1) return false;
        //Si existe stringa en stringb, vuelve a buscar desde después de esa primera aparición.
        int second = bLower.indexOf(aLower, first + aLower.length());
        return second != -1;
    }
    
    public String lastPart(String stringa, String stringb){
        int idx = stringb.indexOf(stringa);
        if (idx == -1) {
            return stringb; //si stringa no esta en stringb, devuelve stringb
        }
        return stringb.substring(idx + stringa.length());//devuelve stringb justo despues de la aparicion de stringa
    }
    public void testing(){
        // Pruebas twoOccurrences
        String[][] paresTwo = new String[][]{
            {"por", "Una historia por Abby Long"}, // true (dos "por")
            {"a", "banana"},                       // true (tres "a")
            {"atg", "ctgtatgta"},                  // false (solo una)
            {"la", "palabra"},                     // true ("la" dos veces: pa**la**b**ra**)
            {"xyz", "abcdef"}                      // false
        };

        System.out.println("== Pruebas twoOccurrences ==");
        for (String[] par : paresTwo) {
            String a = par[0], b = par[1];
            boolean res = twoOccurrences(a, b);
            System.out.println("stringa: \"" + a + "\" | stringb: \"" + b + "\" -> " + res);
        }
        System.out.println("--------------------------------");

        // Pruebas lastPart
        String[][] paresLast = new String[][]{
            {"an", "banana"},   // "ana"
            {"zoo", "bosque"},  // "bosque"
            {"por", "Una historia por Abby Long"}, // " Abby Long"
            {"na", "banana"},    // "na" (porque toma lo que sigue a la primera "na")
            {"x", "xxyz"}        // "yz"
        };

        System.out.println("== Pruebas lastPart ==");
        for (String[] par : paresLast) {
            String a = par[0], b = par[1];
            String res = lastPart(a, b);
            System.out.println("La parte de la cadena después de \"" + a + "\" en \"" + b + "\" es \"" + res + "\".");
        }
        System.out.println("--------------------------------");
    }
    public static void main(String[] args){
        Parte3 p = new Parte3();
        p.testing();
    }
}
