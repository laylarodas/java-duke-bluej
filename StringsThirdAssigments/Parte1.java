
/**
 * Write a description of class Parte1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Parte1 {
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
    public StorageResource getAllGenes(String dna){
        StorageResource geneList = new StorageResource();
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
            geneList.add(gene);
            startIndex = stop +3; //busca el siguiente gene 
        }
        return geneList;
    }
    public double cgRatio(String dna){
        int count = 0;
        for(int i = 0; i < dna.length(); i++){
            char ch = dna.charAt(i);
            if(ch == 'C' || ch == 'G'){
                count++;
            }
        }
        return (double) count / dna.length();
    }
    public int countCTG(String dna){
        int count = 0;
        int index = dna.indexOf("CTG");
        while(index != -1){
            count++;
            index = dna.indexOf("CTG",index+3);
        }
        return count;
    }
    public void processGenes(StorageResource sr,int minLen){
        int countLong = 0; //guardaremos el gene con longitud mayor a minLen
        int countHighCG = 0;//guardaremos el cgRatio [CG>0.35]
        int maxLen = 0;//longitud maxima vista
        System.out.println("=== Genes con longitud > " + minLen + " ===");
        for(String gene : sr.data()){
            int len = gene.length();
            double ratio = cgRatio(gene);
            if(len > minLen){
                countLong++;
                System.out.println(gene);
            }
            if(ratio > 0.35){
                System.out.println("[CG>0.35]" + gene);
                countHighCG++;
            }
            if(len > maxLen) maxLen = len;
        }
        System.out.println("Total > " + minLen + ": " + countLong);
        System.out.println("Total CG>0.35: " + countHighCG);
        System.out.println("Longitud máxima: " + maxLen);
        System.out.println("---------------------------------");
    }
    // Versión de conveniencia: pide > 9 (como enunciado base)
    public void processGenes(StorageResource sr) {
        processGenes(sr, 9);
    }
    public void countGenesInFile(){
        // Ahora procesamos el archivo real
        System.out.println("===== Análisis de archivo real =====");
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        StorageResource genes = getAllGenes(dna);
        System.out.println("Cant total de genes encontrados: " + genes.size());
        processGenes(genes,60);
        int ctgCount = countCTG(dna);
        System.out.println("Cantidad de CTG en el ADN: " + ctgCount);
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
    }
    public void testGetAllGenes(){
        String dna = "AATGCTAACTAGCTGACTAAT";
        StorageResource genes = getAllGenes(dna);
        for(String gene : genes.data()){
            System.out.println(gene);
        }
    }
    public void testCGandCTG(){
        String dna = "ATGCCATAG";
        System.out.println("DNA: " + dna);
        System.out.println("cgRatio = " + cgRatio(dna));

        String dna2 = "CTGATGCTGCTG";
        System.out.println("DNA: " + dna2);
        System.out.println("countCTG = " + countCTG(dna2));
    }
    public void testProcessGenes(){
        // 5 cadenas de ADN diseñadas
        String dna1 = "AATGCCCTAACTAGATTAAGAAACC";     // varios genes; alguno > 9
        String dna2 = "ATGAAATGAATGCCCCTAATAG";         // genes cortos (ninguno > 9)
        String dna3 = "GGGATGCCCGGGTTTTAAATGAAATGA";    // múltiples genes, mezcla
        String dna4 = "ATGCCCCGCGCGCTAAATGAAATAG";      // algunos con CG>0.35
        String dna5 = "ATGTTTTTTAATGAAAAAATGA";         // CG bajo

        String[] dnas = { dna1, dna2, dna3, dna4, dna5 };
        System.out.println("===== Pruebas sintéticas (umbral > 9) =====");
        for (int i = 0; i < dnas.length; i++) {
            String dna = dnas[i].toUpperCase();
            System.out.println("### Caso " + (i + 1) + " ###");
            StorageResource genes = getAllGenes(dna);
            processGenes(genes); // usa > 9
        }
        countGenesInFile();
    }
    public static void main (String[] args){
        Parte1 p = new Parte1();
        p.testFindStopCodon();
        p.testFindGene();
        p.testGetAllGenes();
        p.testCGandCTG();
    }
}
