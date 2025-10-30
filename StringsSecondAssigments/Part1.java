
/**
 * Write a description of class Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int curr = dna.indexOf(stopCodon, startIndex + 3);
        while(curr != -1){
            if((curr - startIndex) % 3 == 0){
                return curr;
            }
            curr = dna.indexOf(stopCodon, curr + 1);// busca el siguiente stopCodon
        }
        return dna.length();
    }
    public String findGene(String dna){
        int start = dna.indexOf("ATG");
        if(start == -1) return "";
        int taa = findStopCodon(dna, start, "TAA");
        int tag = findStopCodon(dna, start, "TAG");
        int tga = findStopCodon(dna, start, "TGA");
        //minimo entre los stopCodons que no sean dna.length()
        int min = Math.min(taa, Math.min(tag,tga));
        if(min == dna.length()) return "";
        return dna.substring(start, min+3);
    }
    public void printAllGenes(String dna){
        int startIndex = 0;
        while(true){
            int start = dna.indexOf("ATG", startIndex);
            if (start == -1) break;
            int taa = findStopCodon(dna, start, "TAA");
            int tag = findStopCodon(dna, start, "TAG");
            int tga = findStopCodon(dna, start, "TGA");
            int stop = Math.min(taa, Math.min(tag, tga));
            if (stop == dna.length()){
                startIndex = start + 3;//buscar a partir del siguiente ATG
                continue;
            }
            String gene = dna.substring(start, stop+3);
            System.out.println(gene);
            startIndex = stop +3; //busca el siguiente gene 
        }
    }
    public void testFindStopCodon() {
        String dna1 = "AATGCCCTAACTAGATTAAAGAAACC"; // TAA en frame
        String dna2 = "ATGCGATAA";                    // TAA no en frame (ATG(0) → TAA(6) => 6%3==0 sí en frame en este caso)
        String dna3 = "ATGAAAAATAAGTAA";              // primero no en frame, luego sí
        String dna4 = "ATGCCCCCCCC";                  // sin stop

        System.out.println(findStopCodon(dna1, dna1.indexOf("ATG"), "TAA")); // índice válido
        System.out.println(findStopCodon(dna2, dna2.indexOf("ATG"), "TAA")); // índice o length según frame
        System.out.println(findStopCodon(dna3, dna3.indexOf("ATG"), "TAA")); // debería encontrar el segundo TAA correcto
        System.out.println(findStopCodon(dna4, dna4.indexOf("ATG"), "TAA")); // = dna4.length()
    }
    public void testFindGene() {
        String[] dnas = new String[] {
            "CCCGGGTTT",                          // 1) sin ATG → ""
            "AATGCCCTAACTAGATTAAAGAAACC",         // 2) ATG + stops → ATGCCCTAA
            "ATGAAAATAAATGTAGATGTGA",             // 3) varios stops, el más cercano gana
            "ATGAAAAACCC",                         // 4) ATG + sin stop válido → ""
            "GGGATGCCCGGGTTTTAAATGAAATGA"         // 5) ruido y múltiples genes
        };

        for (String dna : dnas) {
            String gene = findGene(dna);
            System.out.println("DNA: " + dna);
            System.out.println("Gen: " + (gene.isEmpty() ? "\"\"" : gene));
            System.out.println("----------------------");
        }

        // Probamos printAllGenes con el último
        System.out.println("Todos los genes en el #5:");
        printAllGenes(dnas[4]);
    }
    public static void main (String[] args){
        Part1 p = new Part1();
        p.testFindStopCodon();
        p.testFindGene();
    }
}

