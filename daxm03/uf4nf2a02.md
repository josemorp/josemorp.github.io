# Polimorfisme

# Continguts

  - Classes abstractes i interficies.
  - Polimorfisme en temps de compilació i en temps d'execució.

# Tipus de polimorfisme

## Sobrecàrrega de mètodes

La sobrecàrrega (***overload***) és un tipus de polimorfisme que té lloc amb mètodes que pertanyen al mateix àmbit. Per tant, es troben a la mateixa classe. Els mètodes tenen el mateix nom, però difereixen en el tipus i nombre de paràmetres.

```java
/**
 *
 * @author Jose
 */
public class Sobrecarrega {

    public static void main(String[] args) {
        Sobrecarrega ap = new Sobrecarrega();
        ap.run();
    }
    
    private void run() {
        //suma de int
        int a=2, b=3;
        int c = suma(a, b); 
        System.out.format("%d+%d=%d\n", a, b, c);
        //suma de double
        double d=1.5, e=2.3;
        double f = suma(d, e);
        System.out.format("%f+%f=%f\n", d, e, f);
        //suma de string
        String s1 = "Hola", s2 = "món";
        String s3 = suma(s1, s2);
        System.out.format("%s+%s=%s\n", s1, s2, s3);
        //suma de arrays
        int[] ar1 = {2, 4, 1, 8, 9};
        int[] ar2 = {7, 3, 0, 6};
        try {
            int[] ar3 = suma(ar1, ar2);
            System.out.format("%s+%s=%s\n", 
                    Arrays.toString(ar1), 
                    Arrays.toString(ar2), 
                    Arrays.toString(ar3));            
        } catch(RuntimeException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Els arrays no tenen la mateixa longitud");
        }

    }
    
    public int suma(int x, int y) {
        System.out.println("Suma de int");
        return x+y;
    }
    
    public double suma(double x, double y) {
        System.out.println("Suma de double");
        return x+y;
    }
    
    public String suma(String x, String y){
        return x+y;
    }
    
    public int[] suma(int[] x, int[] y) {
        if (x.length != y.length) {
            throw new RuntimeException("Arrays differ in length");
        }
        int length = x.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = x[i]+y[i];
        }
        return result;
    }
}
```

## Redefinició de mètodes

La redefinició de mètodes (***override***) es produeix quan un mètode definit o declarat en una classe o interface és redefinit, és a dir, es canvia el comportament, en una subclasse o classe que implementi l'interface. El mètode manté el mateix nom i signatura (paràmetres), només es modifica la implementació (el codi).

El polimorfisma assegura que el mètode que s'executarà serà sempre el de l'objecte del qual és membre. La identificació de la classe a la qual pertany es pot fer en temps de compilació o en temps d'execució.

## Polimorfisme d'objectes

El polimorfisme d'objectes es produeix quan objectes de subclasses apareixen amb referències de superclasses. La substitució contrària, no obstant, no és possible.

[Apunts de polimorfisme](assets/4.2/dax2_m03-a422-Polimorfisme.pdf)

[Apunts de classes abstractes i interfaces](assets/4.2/dax2_m03-a422-Classes_abstractes_i_interfaces.pdf)

[Exemples de polimorfisme](assets/4.2/polimorfisme-exemples.zip)
Els dos primers il·lustren el polimorfisme amb les classes Amfibi i Granota.
Els altres il·lustren les conversions entre superclasse i subclasses.

Exemple d'ús d'interface Parlador i herència independent (Animal i Rellotge) [parlador.zip](assets/4.2/parlador.zip)

Exemple de figures planes, herència i implementació de dos interfaces [figures2.zip](assets/4.2/figures2.zip)

Exemple d'herència amb classe base abstracta (Digimon i Pokemon) [pokedigi.zip](assets/4.2/pokedigi.zip)

[Exemple de formulari d'entrada d'objectes usant polimorfisme](assets/4.2/forms.zip)
Il·lustra un procediment per implementar mitjançant polimorfisme formularis d'entrada de dades de diferents tipus d'objecte de forma transparent al programador.
