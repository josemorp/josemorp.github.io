# Classes, objectes i encapsulació

## Continguts

1.  Definició de classes.
2.  Atributs o propietats.
3.  Mètodes: constructors i destructors, accessors de lectura i escriptura.
4.  Propietats i mètodes de classe i d'instància.
5.  Modificadors d'accés i visibilitat.

[Presentació Introducció a la POO: Objectes](assets/4.1/dam-m03-uf4nf1-introd_poo_objectes-presentacio.pdf)

[Presentació Introducció a la POO: classes](assets/4.1/dam-m03-uf4nf1-introd_poo_classes-presentacio.pdf)

[Apunts de classes, objectes i encapsulació](assets/4.1/dax2_m03-a412-Classes_i_encapsulacio.pdf)


## Videos d'introducció a la POO

* [Introducció a POO en Java](https://youtu.be/XmUz5WJmJVU)
* [Classes i objectes](https://youtu.be/ZY5pwm92cWQ)
* [Modularització i encapsulació](https://youtu.be/RZOSJ2zuxIs)
* [Getters i setters](https://youtu.be/7ALMZymOs_s)
* [Getters i setters: pas de paràmetres](https://youtu.be/YQinPQVpSd4)
* [Modificador static: variables i constants](https://youtu.be/QIV7FfXa-zY)
* [Modificador static: mètodes](https://youtu.be/V0wIZ-OglsY)
* [Sobrecàrrega de constructors](https://youtu.be/_ZWcobe9afw)

[Exemple de classe per representar un punt del pla](assets/4.1/Point.zip)

[Exemple de classe per representar un rectangle](assets/4.1/Rectangle.zip)

[Exemple de classe per representar una esfera](assets/4.1/Sphere.zip)

[Exemple de classe per representar un ordinador](assets/4.1/Computer.zip)

[Exemple de classe principal genèrica per a aplicacions senzilles amb menú](assets/4.1/AppTemplate.java)

## Els objectes

Un programa estarà compost per un conjunt d'objectes que interactuen entre ells, cadascun dels quals té una identitat pròpia, caracteritzada per les seves propietats (**atributs**) i el seu comportament (**mètodes**).

Un objecte ha de pertànyer a un tipus concret: la seva **classe**, i pot contenir altres objectes.

Una classe és l'especificació dels **atributs** i **mètodes** que són **membres** de la classe, els quals són compartits per tots els objectes que tenen aquesta classe. Per tant, tots els objectes d'una classe concreta tenen els mateixos atributs (tot i que amb valors, en principi, diferents) i els mateixos mètodes.

Els objectes són tipus de dades **referencials**. Això implica que la declaració només indica el tipus i el valor és una referència (apuntador a memòria) que indica la posició de l'objecte en memòria. Per inicialitzar la referència, cal crear l'objecte en memòria i assignar la referència a la seva posició en memòria a la variable de la seva declaració. Aquest procés de creació es denomina **instanciació**.

Quan un objecte deixa d'estar referenciat, és a dir, cap variable apunta a ell, és eliminat de la memòria. Aquesta operació és realitzada de forma automàtica pel reciclador de memòria (***garbage collector***) de la màquina virtual de Java.

## Les classes

La definició d'una classe té el següent format:

```java
public class LaMevaClasse {
    //atributs
    //...
    //constructors
    //...
    //accessors de lectura i escriptura
    //...
    //altres mètodes membres de la classe
    //...
}
```

El modificador ***public*** indica que la definició de la classe és visible fora del fitxer de codi font en què s'està definint. En absència d'aquest modificador, la classe només seria visible dintre del fitxer en què es troba.

A cada fitxer de codi font només hi pot haver una classe pública, el nom de la qual ha de coincidir amb el del fitxer que la conté.

A la declaració de la classe es poden utilitzar diversos **modificadors**:

* **public**: accessible arreu
* **final**: no pot tenir classes derivades (subclasses), és a dir, no es pot estendre.
* **abstract**: no se'n poden instanciar objectes, ja que és una plantilla per derivar
(estendre) altres classes.


Per instanciar un objecte, cal utilitzar l'operador **new** seguit del mètode constructor de la classe. Si no se n'ha definit cap, almenys disposarà del constructor per defecte, el qual no rep cap argument i crea un objecte sense cap inicialització.

```java
LaMevaClasse obj1 = new LaMevaClasse();
```

### Els atributs

Els atributs contenen informació (dades), i poden ser tipus primitius o altres tipus relacionals (String, arrays, objectes).

Exemples de declaració d'atributs:

```java
private int numberOfPlayers;
protected String name;
public double width;
```

Els modificadors d'accés indiquen si es pot accedir des d'altres classes a aquests atributs.

* **private**: accés restringit a la classe en què està declarat
* **protected**: accés restringit a la classe en què està declarat, a les subclasses d'aquesta i a les que pertanyen al mateix paquet (package).
* **public**: accés no restringit. És accessible des de qualsevol classe

També es pot aplicar el modificador **final**, el qual indica que l'atribut no pot ser modificat un cop inicialitzat (és una constant).

```java
private final int MAX_PLAYERS = 10;
```

Aquests atributs són diferents per a cada instància. És a dir, cada objecte creat d'aquesta classe té una còpia diferent dels atributs amb valors eventualment diferents. S'anomenen per aquest motiu **atributs d'instància**.

Es poden definir atributs de classe, dels quals només existeix una còpia en memòria per a tots els objectes de la classe. Per tant, tots els objectes d'aquesta classe tindran el mateix valor d'aquest atribut. Es declaren amb el modificador **static**.

```java
public static int NUMBER_OF_ELEMENTS;
```

Els atributs es poden inicialitzar utilitzant constructors segons convingui.

## Els mètodes

Les classes també contenen mètodes (funcions) que realitzen accions.

Els mètodes es defineixen especificant el modificador d'accés, el tipus de retorn, el nom del mètode i, finalment, entre parèntesis, la relació de paràmetres (tipus i nom) que rebrà el mètode en ser invocat.

```java
public int sum(int x, int y) {
    int r = x+y;
    return r;
}
```

```java
public void greet(String name) {
    System.out.println("hello "+name);
}
```

## Primer exemple de classe

Anem a definir una classe per representar un quadrat.

```java
public class Quadrat {
    //atribut 'costat'
    public double costat;
    //constructor per inicialitzar l'atribut
    public Quadrat(double costat) {
        this.costat = costat;
    }
    //mètode per obtenir l'àrea del quadrat
    public double area() {
        return costat*costat;
    }
}
```
Com veiem, el mètode constructor té el mateix nom que la classe i no té tipus de retorn. Rep un valor, el qual copiem en l'atribut 'costat'. Com que li hem donat el mateix nom al paràmetre del constructor que a l'atribut, i les declaracions locals tenen prioritat sobre les generals, si escrivim 'costat' dintre del mètode ens estem referint al paràmetre. Per accedir a l'atribut disposem de la referència **this**, la qual apunta a l'objecte dintre del qual estem.

Al mètode *area()* no ens cal usar ***this***, ja que la visibilitat de l'atribut 'costat' no es veu afectada per cap variable local ni paràmetre del mètode.

Ara podem crear objectes quadrat des d'una altra classe.

```java
Quadrat q1 = new Quadrat(3.0);   //instanciem un objecte quadrat amb costat=3.0
double areaQ1 = q1.area();  //demanem a l'objecte q1 que calculi la seva àrea
//podem llegir el valor del costat, ja que està declarat public
double costatQ1 = q1.costat;
//i podem canviar el valor del costat (és públic)
q1.costat = 5.0;
```

L'operador **'.'** permet accedir als membres (atributs i mètodes) accessibles de l'objecte.

## Encapsulació de dades

La definició d'atributs amb el modificador ***public*** no és la solució més convenient. És millor encapsular la informació dintre dels objectes i només publicar (donar accés a altres classes) aquelles propietats que sigui necessari, mantenint privada la resta de la informació i també els mètodes que siguin només per a ús intern de la classe.

Per tant, el que farem generalment és declarar els atributs amb el modificador d'accés ***private***. D'aquesta manera, no seran accessibles des d'altres classes.

```java
public class Quadrat {
    //atribut 'costat'
    private double costat;
    //constructor per inicialitzar l'atribut
    public Quadrat(double costat) {
        this.costat = costat;
    }
    //mètode per obtenir l'àrea del quadrat
    public double area() {
        return costat*costat;
    }
}
```

Amb aquesta declaració, ja no serà possible accedir de manera directa des d'altres classes.

```java
double costatQ1 = q1.costat;  //error: l'atribut costat és privat
q1.costat = 5.0;   //error: l'atribut costat és privat
```

Els mètodes, en canvi, els declararem com a norma general públics per poder usar-los des d'altres classes.

```java
double areaQ1 = q1.area();
```

Si volem donar accés de lectura o escriptura d'algun atribut des d'altres classes, ho farem definint mètodes d'accés públics:

```java
public double getCostat() {
    return costat;
}
public void setCostat(double costat) {
    this.costat = costat;
}
``` 

Els mètodes d'accés reben noms **getXX** i **setXX**, on XX és el nom de l'atribut.

El mètode *get* retorna el valor de l'atribut. El mètode *set* no retorna res, rep com a paràmetre un valor del mateix tipus que l'atribut i copia aquest valor a l'atribut.

La definició de la classe quedaria així:

```java
public class Quadrat {
    //atribut 'costat'
    private double costat;
    //constructor per inicialitzar l'atribut
    public Quadrat(double costat) {
        this.costat = costat;
    }
    //accessors
    public double getCostat() {
        return costat;
    }
    public void setCostat(double costat) {
        this.costat = costat;
    }
    //mètode per obtenir l'àrea del quadrat
    public double area() {
        return costat*costat;
    }
}
```

Ara podem accedir a l'atribut des d'altres classes:

```java
int costatQ1 = q1.getCostat();  //llegim el valor del costat de l'objecte quadrat q1
q1.setCostat(5.0);  //canviem el valor del costat de l'objecte quadrat q1
```

L'avantatge d'aquest plantejament és que ara podem controlar l'accés als atributs a través dels mètodes d'accés. Per exemple, podem validar els valors abans d'assignar-los als atributs.

```java
public void setCostat(double costat) {
    if (costat > 0.0) this.costat = costat;  //evitem assignar valors negatius
}
```

## Còpia d'objectes

Com que les variables de tipus objecte són referències, assignar el seu valor a una altra no realitza una còpia completa (***deep copy***) de l'objecte, sinó que només assigna el valor de la referència (l'adreça en memòria de l'objecte) (***shallow copy***).

Amb tipus primitius:

```java
int a = 3;
int b = a;  //tenim dues variables 'int' en memòria, amb el mateix valor
if (a==b) {  //compara els valors de les dues variables (resultat: true)
    //...
}
```

En canvi, amb tipus referencials:

```java
Quadrat q1 = new Quadrat(3.0);
Quadrat q2 = q1;  //dues referències que apunten a un únic objecte en memòria
if (q1==q2) {  //compara el valor de les referències (resultat: true, són el mateix objecte)
    //...
}
Quadrat q3 = new Quadrat(3.0);  //nou objecte (instància) en memòria, amb el mateix valor que 'q1'
if (q1==q3) {  //compara el valor de les referències (resultat: false, no són el mateix objecte)
    //...
}
if (q1.equals(q3)) {  //compara el contingut d'acord amb el que s'ha definit al mètode equals (resultat: true, si es equals els compara amb el valor del costat)
    //...
}
```

Perquè funcioni la comparació, cal redefinir el mètode **equals()** que totes les classes hereten de la classe [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html).

```java
public boolean equals(Object obj) {
    boolean result = false;
    if (obj == null) {  //null object
        result = false;
    } else {
        if (this == obj) {  //same object
            result = true;
        } else {
            if (obj instanceOf Quadrat other) {  //same class of subclass
                //compare 'costat'
                result = (this.costat == other.costat);
            } else {  //not same class
                result = false;
            }
        }
    }
    return result;
}
```

Per facilitar la còpia completa d'un objecte convé definir un constructor de còpia, el qual crea un nou objecte i li copia la informació del que rep com a paràmetre:

```java
public Quadrat(Quadrat other) {
    this.costat = other.costat;
}
```

```java
Quadrat q1 = new Quadrat(3.0);
Quadrat q2 = new Quadrat(q1);  //construir una còpia del quadrat 'q1'
if (q1==q2) {  //false, no és el mateix objecte

}
if (q1.equals(q2)) {  //true, les dues instàncies tenen la mateixa informació

}
```
## Els tipus Enum

Els ***Enum*** són un tipus especial de classe que serveixen per declarar un conjunt de constants per a ús del programador.

Tots estenen [java.lang.Enum](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html).

```java
enum Size { 
   SMALL, MEDIUM, LARGE, EXTRALARGE 
}
```

```java
Size s = Size.MEDIUM;
```

Els valors enum es poden usar en estructures switch-case.

Exemple per definir constants per als dies de la setmana.

```java
public enum WeekDay {
    MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
```

```java
WeekDay today = WeekDay.SATURDAY;
if (today.equals(WeekDay.SATURDAY))  System.out.println("Hoorray!");
```

Mètodes útils dels Enum:

  * final String name() : retorna el nom de la constant, tal com va ser declarada
  * final int ordinal() : retorna la posició de la declaració (el primer és 0)
  * final int compareTo(E o): retorna -, 0, + segons l'ordenació respecte del paràmetre
  * static T valueOf(String name): retorna la constant del *enum* que coincideix amb el paràmetre
  * static T[] values(): retorna un array amb totes les constants del *enum*

Els tipus **Enum** també són classes i poden tenir atributs i constructors.

```java
package enums;
/**
 *
 * @author Jose
 */
public enum Grade {
    
    MD("Very deficient", 0.0, 2.0),
    DEF("Deficient", 2.0, 4.0),
    INS("Poor", 4.0, 5.0),
    SUF("Sufficient", 5.0, 6.0),
    BE("Good", 6.0, 7.0),
    NOT("Notable", 7.0, 9.0),
    EXC("Excellent", 9.0, 10.0);    
    
    private final String text;
    private final double lower;
    private final double upper;
    
    Grade(String text, double lower, double upper) {
        this.text = text;
        this.lower = lower;
        this.upper = upper;
    }

    public String getText() {
        return text;
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public static Grade getGrade(double value) {
        Grade result = Grade.MD;
        if (value == 10.0) {
            result = Grade.EXC;
        } else {
            for (Grade g : values()) {
                if ( (g.lower <= value) && (value < g.upper) ) {
                    result = g;
                    break;
                }
            }            
        }
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grade{");
        sb.append("text=").append(text);
        sb.append(", lower=").append(lower);
        sb.append(", upper=").append(upper);
        sb.append('}');
        return sb.toString();
    }
    
}
```

```java
package enums;

import java.util.Arrays;

/**
 *
 * @author Jose
 */
public class GradeTest {
    public static void main(String[] args) {
        GradeTest ap = new GradeTest();
        ap.run();
    }
    private void run() {
        Grade grade1 = Grade.NOT;
        System.out.println("grade1.text:"+grade1.getText());
        System.out.println("grade1.lower:"+grade1.getLower());
        System.out.println("grade1.upper:"+grade1.getUpper());
        System.out.println("grade1.toString:"+grade1.toString());
        //get Grade values
        Grade[] gradeValues = Grade.values();
        System.out.println("Grade values: "+Arrays.toString(gradeValues));
        if (grade1.equals(Grade.NOT)) {
            System.out.println("Congratulations: Your grade is 'Notable'");
        }
        //use of enum in a switch-case structure
        String message;
        switch (grade1) {
            case BE:
            case EXC:
            case NOT:
            case SUF:
                message = "You passed";
                break;
            default:
                message = "You failed";
        }
        System.out.println(message);
        //creation of Grade given its name
        Grade grade2 = Grade.valueOf("SUF");
        System.out.println("grade2:"+ grade2.toString());
        //get grade by numeric value
        Grade grade3 = Grade.getGrade(4.5);
        System.out.println("grade3:"+ grade3.toString());
    }
    
}
```