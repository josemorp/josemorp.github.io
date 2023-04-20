# Recursivitat

## Introducció

Un mètode recursiu és aquell que es crida a sí mateix, bé directament o bé a través d’un altre mètode. En **recursió directa**, el codi del mètode f() conté una sentència que invoca a f(). En canvi, en **recursió indirecta**, el mètode f() invoca un mètode g(), el qual invoca al mètode h(), i així successivament fins que s’invoca novament el mètode f().

En la implementació d’un mètode recursiu, és necessari establir una **condició de sortida o finalització** i assegurar-se del seu compliment. Cas contrari, l’algorisme continuaria executant-se indefinidament a través de les invocacions recursives.

## Iteració versus recursió

La iteració utilitza estructures repetitives i la recursió utilitza estructures de selecció. La repetició s’aconsegueix en la iteració mitjançant l’ús explícit d’estructures repetitives, mentre que en la recursió s’aconsegueix mitjançant la repetició de crides a mètodes. En ambdós casos es necessita una condició de sortida.

En general, la recursió és més lenta, atès que cal implementar repetidament el mecanisme de invocació als subprogrames o mètodes, fent una còpia dels arguments de la invocació, la qual cosa consumeix també més memòria.

Qualsevol problema que es pot resoldre recursivament, també té una solució iterativa. Utilitzarem un enfocament recursiu quan produeixi un algorisme més fàcil de comprendre i depurar i quan la solució iterativa sigui molt complexa.

Els algorismes recursius permeten resoldre problemes complexos d’una manera elegant i senzilla.

## Flux de control en un mètode recursiu

Cal tenir molt present la condició de sortida. Si aquesta condició no es compleix mai, tenim recursió infinita, i les crides recursives es repeteixen fins que s’esgota la memòria disponible o el programa pateix alguna terminació anormal per algun altre error.

El flux habitual d’un mètode recursiu té el següent esquema:
  - Comprovació de la condició de sortida (**cas base**)
  - Invocació recursiva
  - **Cas final** per finalitzar la recursió

## Procediments recursius

Quan s’escriu un algorisme per realitzar una tasca, una tècnica bàsica és dividir la tasca general en subtasques més petites. Quan alguna de les subtasques és una simplificació de la mateixa tasca general, és fàcil implementar un algorisme recursiu que invoqui al mateix procediment.

Exemple:
Escriure un programa que saludi amb Hip Hip Hurra, amb tants Hip com indiqui l'usuari.

```java
import java.util.Scanner;
public class HipHurra {
	
	public static void main (String[] args) {
		int numHips;
		numHips = llegirEnter("Entra el nombre de hips: ");
		salut(numHips);
	}
	
	private static int llegirEnter(String missatge) {
		System.out.print(missatge);
		Scanner lector = new Scanner(System.in);
		return lector.nextInt();
	}
	
	private static void salut(int n) {
		if (n==0) {  //cas base
			System.out.println("Hurra");  //cas final
		} else {  //recursió
			System.out.print("Hip ");
			salut(n-1);
		}
	}
	
}
```

Exemple:
Escriure un programa que escrigui en vertical els dígits d’un nombre natural.

```java
import java.util.Scanner;

public class EscriureEnterVertical {
	
	public static void main (String[] args) {
		System.out.print("Entra un nombre natural: ");
		Scanner lector = new Scanner(System.in);
		int num = lector.nextInt();  // nombre a escriure, a entrar per l’usuari
		if (num>0)  {
			printVertical(num);
		}
		else {
			System.out.println("El nombre ha de ser positiu");
		}	
	}
	
	static void printVertical(int n) {
		if (n<10) { // cas base
			  System.out.println(n);   // cas final
		} else {  // n te dos o mes digits
			printVertical(n/10);
			System.out.println(n%10);
		}		
	}
}
```
Si n té una xifra (cas base) es fa el cas final (escriure n). Cas contrari, s’invoca al mateix procediment amb la part entera de la divisió de n entre 10 (el nombre original sense les unitats) i quan es retorni de les crides recursives, s’escriu el mòdul n%10 (residu de la divisió entre 10, és a dir, les unitats).

## Funcions recursives

La recursivitat també es pot utilitzar amb funcions que retornen un valor.

Exemple:
Escriure un programa que calculi la suma dels n primers nombres naturals.

```java
import java.util.Scanner;

public class SumaNEnters {
	
	public static void main (String[] args) {
		System.out.print("Sumar fins a quin número: ");
		Scanner lector = new Scanner(System.in);
		int num = lector.nextInt();  // Últim enter a sumar, a entrar per l'usuari
		if (num>0)  {
			int suma = sumaRecursiva(num);
			System.out.print("La suma és: "+suma);
		}
		else {
			System.out.println("El nombre ha de ser positiu");
		}	
	}
	
	static int sumaRecursiva(int n) {
		return ( (n==1) ? 1 : n+sumaRecursiva(n-1));
	}

}
```

La verificació de la condició de sortida es fa amb n==1. Aquest és el cas base, a partir del qual es construeix la recursió i es fan els càlculs. Si no es compleix, es fa la crida recursiva a la mateixa funció. Si es compleix la condició de sortida, s’aplica el cas final (retornar 1).

## Recursivitat indirecta

Es té recursivitat indirecta quan el mètode que invoca la recursivitat no és invocat directament per sí mateix, sinó després de les invocacions a altres mètodes.

Exemple:
Escriure un programa i els subprogrames per determinar si un nombre natural és parell o senar.

```java
import java.util.Scanner;

public class Paritat {
	
	public static void main (String[] args) {
		final int MAX_NUM = 4;
		final int [] nums = {4, 3, 13, 16};
		for (int i=0; i<MAX_NUM; i++) {
			int x = nums[i];
			if (senar(x)) {
				System.out.format("%d és senar\n", x);
			} else {
				System.out.format("%d no és senar\n", x);
			}
			if (parell(x)) {
				System.out.format("%d és parell\n", x);
			} else {
				System.out.format("%d no és parell\n", x);
			}	
		}
	}
	
	static boolean senar(int n) {
		return ( (n==0) ? false : parell(n-1));
	}

	static boolean parell(int n) {
		return ( (n==0) ? true : senar(n-1));
	}

}
```

En aquest algorisme, la paritat s’obté mitjançant invocacions recursives a les funcions parell() i senar(), amb el natural anterior. La condició de sortida s’obté quan s’arriba a 0.
