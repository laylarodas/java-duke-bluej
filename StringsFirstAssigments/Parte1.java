
/**
 * Write a description of class Parte1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Parte1 {
    public String findSimpleGene(String dna){
        int startIndex = dna.indexOf("ATG");
        if(startIndex == -1){
            return "";
        }
        int stopIndex = dna.indexOf("TAA", startIndex + 3);
        if (stopIndex == -1) {
            return "";
        }
        if ((stopIndex - startIndex) % 3 == 0) {
            return dna.substring(startIndex, stopIndex + 3);
        } else {
            return "";
        }
    }
    public void testSimpleGene(){
        String[] dnas = new String[]{
            // 1) ADN sin "ATG"
            "CCCTAACTACGTT",
            // 2) ADN con "ATG" pero sin "TAA"
            "CCCATGGGCCCTT",
            // 3) ADN sin "ATG" ni "TAA"
            "CCCGGGCCCTTT",
            // 4) ADN con ATG y TAA y longitud múltiplo de 3 (gen válido)
            "AATGCCCTAACTAGATTAAGAAACC",
            // 5) ADN con ATG y TAA pero longitud NO múltiplo de 3
            "ATGCGTAA"
        };
        for(String dna : dnas){
            String gene = findSimpleGene(dna);
            System.out.println("DNA: " + dna);
            System.out.println("Gen encontrado: " + (gene.isEmpty() ? "\"\"" : gene));
            System.out.println("--------------------------------");
        }
    }
    public static void main(String[] args){
        Parte1 p = new Parte1();
        p.testSimpleGene();
    }
}
