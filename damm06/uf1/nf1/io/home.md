# Biblioteca java.io

[Apunts de fluxos d'informació](/damm06/assets/1.1/1.1.io/dax2_m03-a513-Fluxos_informacio.pdf)

## Gestió del sistema de fitxers

En aquest apartat anem a treballar el sistema de fitxers, és a dir, la gestió de directoris, creació, esborrar i llistat del seu contingut.

La classe ***File*** representa objectes de tipus arxiu, que encapsulen el nom de l’arxiu i les seves propietats: dimensió, data de la darrera actualització, permisos de lectura i/o escriptura, etc.

Permet fer la representació abstracta d’arxius, crear-los, esborrar-los, canviar les seves propietats, però no llegir ni escriure en ells.

Podem invocar de diverses maneres al constructor de la classe amb un arxiu determinat, donada la seva ruta completa. Per exemple, si es tracta de l’arxiu “\docs\carta.doc”:

```java
File unArxiu = new File(“\docs\carta.doc”);
File unArxiu = new File(“\docs”, “carta.doc”);
File unDir = new File(“\docs”);
File unArxiu = new File(unDir, “carta.doc”);
```

Les taules següents reuneixen els principals mètodes de la classe *File*:

**Mètodes relatius als noms dels fitxers**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| String toString()          | Retorna la conversió a String |
| String getName()           | Retorna el nom                 |
| String getPath()           | Retorna la ruta                |
| String getAbsolutePath()   | Retorna la ruta absoluta       |
| String getParent()         | Retorna la ruta pare           |
| boolean renameTo(File nom) | Canvia el nom                  |

**Mètodes relatius a verificacions**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| boolean exists()          | Existència |
| boolean canWrite()          | Permís d’escriptura                 |
| boolean canRead()           | Permís de lectura                |
| boolean isFile()   | Correspon a un directori       |
| boolean isDirectory()         | Retorna la ruta pare           |
| boolean isAbsolute() | És una ruta absoluta                  |
| boolean isHidden() | És ocult                 |

**Mètodes relatius a propietats**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| long lastModified()          | Data i hora de la darrera modificació |
| boolean setLastModified(long time)          | Canvia la data i hora de la darrera modificació                 |
| long length()           | Tamany del fitxer                |
| boolean setReadOnly()   | Activa atribut de només lectura       |

**Mètodes relatius a directoris i altres**

| Mètode                    | Descripció                    |
| :--------------------------- | -------------------------------- |
| boolean mkdir()          | Crea el directori |
| boolean mkdirs()          | Crea el directori i els directoris pare necessaris                 |
| String[] list()          | Array de noms dels fitxers que conté el directori              |
| File[] listFiles()   | Array de File dels fitxers que conté el directori       |
| boolean delete()   | Esborra el fitxer o directori       |

### Exemples


**Mostrar informació d'un fitxer**

```java
import java.io.*;
	
public class FileAttributes {
	
	public static void main(String [] args) throws IOException
	{
	
		StringBuilder sb = new StringBuilder(); //output string
	
		if (args.length==1){
			File file = new File(args[0]); //new file
			sb.append("\nPath: ");
			sb.append(file.getPath());
			sb.append("\nName: ");
			sb.append(file.getName());
			sb.append("\nAbsolute path: ");
			sb.append(file.getAbsolutePath());
	
	
			if (file.exists()) {
				sb.append("\nAttributes: ");
				sb.append(file.isDirectory()?"+d":"-d");
				sb.append(file.isFile()?"+f":"-f");
				sb.append(file.isHidden()?"+h":"-h");
				sb.append(file.canRead()?"+r":"-r");
				sb.append(file.canWrite()?"+w":"-w");
				sb.append("\nSize: ");
				sb.append(file.length());
	
				java.util.Date data = new java.util.Date(file.lastModified());
				sb.append("\nLast modification: ");
				sb.append(data.toString());
			}
			else {
				sb.append("This file or directory does not exist.");
			}
		}
		else {
			sb.append("Usage: FileAttributes <path>");
		}
		//write output
		System.out.println(sb.toString());
	}	 //end of main()
}	 //end of class
```

**Mostrar informació d'un directory i el nom dels seus fitxers (i subdirectoris)**

```java
/**
 * listFilesInDir():
 * Shows directory information and filenames in directory.
 * @param str: is the name of the directory to list.
 * @return a string containing the list of directories.
 */
 
public String listFilesInDir ( String str ) {
	File          dir  =  new File (str);
	StringBuilder sb   =  new StringBuilder();
	if ( dir.exists() && dir.isDirectory() ) {
		sb.append("\nAbsolute path: ");
		sb.append(dir.getAbsolutePath());
		sb.append("\nRelative path: ");
		sb.append(dir.getPath());
		sb.append("\nDirectory Name: ");
		sb.append(dir.getName());
		sb.append("\nContents: ");
		sb.append("\n------------------");
		String [] fileList = dir.list();
		for (int i=0; i<fileList.length; i++) {
			sb.append("\n");
			sb.append(fileList[i]);
			}
		}
	return sb.toString();
}//end of listFilesInDir
```

**Obtenir i mostrar (en format array) els noms de les entrades d'un subdirectory donat**

```java
/**
 * example of how to display the names of all files in a directory
 * @param dirPath path to directory
 */
public void listDirFilenames(String dirPath) {
	File dir = new File(dirPath);
	String[] filenames = dir.list();
	System.out.println(Arrays.toString(filenames));
}

```

**Esborrar un directory i tots els seus fitxers**

Cal recordar que només es poden esborrar directoris buits.

```java
/**
 * example of how to delete a directory and all of its files
 * (remember that only an empty directory can be deleted)
 * @param dirPath path to directory
 */
public void deleteDirectoryAndFiles(String dirPath) {
	boolean success = false;
	File dir = new File(dirPath);
	//get files in directory
	File[] files = dir.listFiles();
	//delete files
	for (File f : files) {
		success = f.delete();
		System.out.format("File %s successfully deleted\n", f.getAbsolutePath());
	}
	//delete directory
	success = dir.delete();
	System.out.format("Directory %s successfully deleted\n", dir.getAbsolutePath());
}
```

## Fluxos d'informació

Els programes poden enviar fluxos de dades (**streams**) o rebre’ls cap a o des de diferents orígens i destinacions. Ens centrarem bàsicament en *streams* per llegir i escriure fitxers.

Els *streams* es categoritzen en quatre grans blocs segons dues propietats:

* Sentit: entrada o sortida
* Codificació: orientats a byte o orientats a caràcter

**Streams d'entrada orientats a bytes**

![](/damm06/assets/1.1/1.1.io/inputstream.png)

Mètodes útils proporcionats per la classe ***InputStream***:

| mètode | descripció |
| ------ | ------ |
| public abstract int read() throws IOException     | llegeix i retorna el següent byte o -1 si ha arribat al final del fitxer    |
| public int available() throws IOException     |  retorna el nombre de bytes que queden per llegir    | 
| public void close() throws IOException     |  tanca el recurs    |
| public long skip(long n) throws IOException     | salta i descarta un nombre de bytes sense llegir i retorna el nombre realment saltat    |
| public int read(byte[] b) throws IOException     | llegeix array de bytes, retorna el nombre de bytes llegits o -1 si s'arriba al final del fitxer   |
| public byte[] readAllBytes() throws IOException     | llegeix tots els bytes restants    |
| public byte[] readANBytes(int len) throws IOException     | llegeix el nombre de bytes especificat    |
| public long transferTo(OutputStream out) throws IOException     | llegeix tots els bytes del stream i els escriu a l'especificat; retorna el nombre de bytes transferits  |

```java
/**
 * reads a list of byte from file
 *
 * @param filename the name of the file to read from
 * @return list of bytes read
 * @throws FileNotFoundException if file not found
 * @throws IOException in case an input/output error occurs
 */
private static List<Byte> readBytesToFile(String filename) throws FileNotFoundException, IOException {
	File f = new File(filename);
	List<Byte> data = new ArrayList<>();
	try (FileInputStream fs = new FileInputStream(f)) {
		int x;  //variable to store each byte read
		while ((x = fs.read()) != -1) { //while not end of file,  keep reading
			data.add((byte)x);
		}
	}
	return data;
}
```

**Streams de sortida orientats a bytes**

![](/damm06/assets/1.1/1.1.io/outputstream.png)

Mètodes útils proporcionats per la classe ***OutputStream***:

| mètode | descripció |
| ------ | ------ |
| public void write(int b) throws IOException     | escriu un byte   |
| public void write(byte[] b) throws IOException     | escriu un array de bytes   | 
| public void write(byte[] b, int off, int len) throws IOException     | escriu un array de bytes des de la posició 'off' amb longitud 'len'  | 
| public void flush() throws IOException     | descarrega el buffer del stream   |
| public void close()throws IOException     |  tanca el recurs    |

```java
/**
 * writes a list of byte to file
 * @param filename the name of the file to write to
 * @param data the list of byte to write
 * @throws FileNotFoundException if file not found
 * @throws IOException in case an input/output error occurs
 */
public void writeBytesToFile(String filename, List<Byte> data) throws FileNotFoundException, IOException {
	File f = new File(filename);
	try (FileOutputStream fs = new FileOutputStream(f)) {
		for (Byte b : data) {
			fs.write(b);
		}
	}
}
```

Fragment de codi principal per provar els mètodes anteriors:

```java
//define filename
String filename = "bytes.txt";
//define data to be written
List<Byte> data = List.of((byte) 10, (byte) 15, (byte) 25, (byte) 30, (byte) 45);
try {
	System.out.println("Writing data");
	writeBytesToFile(filename, data);
	System.out.println("Reading data");
	List<Byte> data2 = readBytesToFile(filename);
	System.out.println("Data read");
	System.out.println(data2);
} catch (FileNotFoundException e) {
	System.out.println("File not found");;
} catch (IOException e) {
	System.out.println("Input or output problem related to this operation");;
}
```

**Streams d'entrada orientats a caràcters**

![](/damm06/assets/1.1/1.1.io/reader.png)

Mètodes útils proporcionats per la classe ***Reader***:

| mètode | descripció |
| ------ | ------ |
| public abstract int read() throws IOException     | llegeix i retorna el següent caràcter o -1 si ha arribat al final del fitxer    |
| public void close() throws IOException     |  tanca el recurs    |
| public long skip(long n) throws IOException     | salta i descasrta un nombre de bytes sense llegir i retorna el nombre realment saltat    |
| public int read(char[] b) throws IOException     | llegeix array de caràcters, retorna el nombre de caràcters llegits o -1 si s'arriba al final del fitxer   |
| public long transferTo(Writer out) throws IOException     | llegeix tots els caràcters del stream i els escriu a l'especificat; retorna el nombre de caràcters transferits  |

**Streams de sortida orientats a caràcters**

![](/damm06/assets/1.1/1.1.io/writer.png)

Mètodes útils proporcionats per la classe ***Writer***:

| mètode | descripció |
| ------ | ------ |
| public void write(int b) throws IOException     | escriu un byte   |
| public void write(byte[] b) throws IOException     | escriu un array de bytes   | 
| public void write(byte[] b, int off, int len) throws IOException     | escriu un array de bytes des de la posició 'off' amb longitud 'len'  | 
| public void flush() throws IOException     | descarrega el buffer del stream   |
| public void close()throws IOException     |  tanca el recurs    |

