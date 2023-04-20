import java.util.Scanner;

/**
 * Programa per xifrar una frase amb algorisme de desplaçament (César)
 * @author Jose
 */
public class XifratCesar {

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        //lector.useDelimiter(System.lineSeparator());
        lector.useDelimiter("\n");
        //llegir la frase a xifrar
        System.out.print("Entra la frase a xifrar: ");
        String fraseOriginal = lector.next();
        //llegir desplaçament
        System.out.print("Entra el desplaçament: ");
        int desp = lector.nextInt();
        //xifrar
        String fraseXifrada = xifrarCesar(fraseOriginal, desp);
        //mostrar frase xifrada
        System.out.print("Frase xifrada: ");
        System.out.println(fraseXifrada);
        //desxifrar
        String fraseDesxifrada = desxifrarCesar(fraseXifrada, desp);
        //mostrar frase desxifrada
        System.out.print("Frase desxifrada: ");
        System.out.println(fraseDesxifrada);
    }
    
    /**
     * xifra el missatge amb l'algorisme de desplaçament
     * @param missatge el missatge a xifrar
     * @param desp el desplaçament a aplicar
     * @return el missatge xifrat
     */
    public static String xifrarCesar(String missatge, int desp) {
        missatge = missatge.toLowerCase();
        final String lletres = "abcdefghijklmnopqrstuvwxyz1234567890 ,;:";
        String result="";
        for (int i=0; i<missatge.length(); i++) { //recórrer el missatge
            int index1 = lletres.indexOf(missatge.charAt(i));
            int index2 = (index1 + desp)%lletres.length();
            char carTraduit = lletres.charAt(index2);
            result += carTraduit;
        }
        return result;
    }

    /**
     * desxifra el missatge amb l'algorisme de desplaçament
     * @param missatge el missatge a desxifrar
     * @param desp el desplaçament a aplicar
     * @return el missatge desxifrat
     */    
    public static String desxifrarCesar(String missatge, int desp) {
        missatge = missatge.toLowerCase();
        final String lletres = "abcdefghijklmnopqrstuvwxyz1234567890 ,;:";
        String result="";
        for (int i=0; i<missatge.length(); i++) { //recórrer el missatge
            int index1 = lletres.indexOf(missatge.charAt(i));
            int index2 = index1 - desp;
            if (index2<0) {
                index2 += lletres.length();
            }
            char carTraduit = lletres.charAt(index2);
            result += carTraduit;
        }
        return result;        
    }
    
}
