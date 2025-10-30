
/**
 * Write a description of class Parte2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Parte2 {
    public String findSimpleGene(String dna, String startCodon, String stopCodon){
        boolean isLowerCase = dna.equals(dna.toLowerCase());
        //Normalizar - siempre en mayúsculas
        String dnaUpper = dna.toUpperCase();
        String startUpper = startCodon.toUpperCase();
        String stopUpper  = stopCodon.toUpperCase();
        
        int startIndex = dnaUpper.indexOf(startUpper);
        if(startIndex == -1){
            return "";
        }
        int stopIndex = dnaUpper.indexOf(stopUpper, startIndex + 3);// +3 para no captar el inicio sino despues
        if (stopIndex == -1) {
            return "";
        }
        //es un gen valido si la diferencia da multiplo de 3
        if((stopIndex - startIndex)% 3 != 0){
            return "";
        }
        String geneUpper = dna.substring(startIndex,stopIndex + 3);
        if(isLowerCase){
            return geneUpper.toLowerCase();
        } else {
            return geneUpper;
        }
    }
    public void testSimpleGene() {
        String[] dnas = new String[] {
            "ATGGGTTAAGTC",      // mayúsculas → espera "ATGGGTTAA"
            "gatgctataat",       // minúsculas → espera "atgctataa"
            "gatgctataatg",      // sin TAA válido
            "ATGCCCTAACTAGATTAAGAAACC", // mismo de antes → "ATGCCCTAA"
            "cccatgggcccataaattaa", // probablemente no múltiplo de 3 → ""
            "AAATGCCCTAACTAGATTAAGAAACC"
        };
        for (String dna : dnas) {
            String gene = findSimpleGene(dna, "ATG", "TAA");
            System.out.println("DNA: " + dna);
            System.out.println("Gen encontrado: " + (gene.isEmpty() ? "\"\"" : gene));
            System.out.println("--------------------------------");
        }
    }
    public static void main(String[] args) {
        Parte2 p = new Parte2();
        p.testSimpleGene();
    }
}
