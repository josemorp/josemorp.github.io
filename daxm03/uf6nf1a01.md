# Connexió amb bases de dades relacionals en Java

## Continguts

* Connexió, consulta i modificació sobre bases de dades
* Models connectat i desconnectat

## Persistència i seriació

La **persistència** és el mecanisme mitjançant el qual s’aconsegueix desar dades perquè no es perdin en finalitzar l’aplicació i posteriorment recuperar-les.

Si l’aplicació gestiona petites quantitats de dades, pot ser suficient emmagatzemar-les en un fitxer de text en disc. Si, en canvi, és necessari manejar grans quantitats de dades, l’opció serà emmagatzemar-les en una base de dades.

Podem convertir un objecte en bytes per poder enviar-lo a través de xarxa, desar-lo en un arxiu, reconstruir-lo a l’altra banda d’una xarxa, llegir-lo de fitxer, etc. Aquest procés de conversió a bytes s’anomena **seriació**. Per fer que un objecte es pugui seriar en Java només cal que implementi la interface **Serializable**, la qual no té mètodes. Els atributs de les classes que implementin Serializable han de ser dades primitives o bé classes que també implementin la interface.

Si necessitem que la seriació es faci d’una manera especial, hem de definir els mètodes que java cridarà per convertir l’objecte a bytes i per recuperar-lo:

```java
private void readObject(java.io.ObjectInputStream stream)
throws IOException, ClassNotFoundException
private void writeObject(java.io.ObjectOutputStream stream)
throws IOException
```
Quan enviem objectes a través d’una xarxa, és possible que les versions de l’arxiu _.class_ corresponents siguin diferents, impossibilitant la recuperació. Java pot detectar si les versions són iguals o no si la classe té un atribut privat de versió
definit de la següent manera:

`private static final long serialVersionUID = 6678793847896547166L;`

El mecanisme de persistència que utilitzarem en aquest capítol és la base de dades relacional.

Una **base de dades relacional** està formada per un conjunt de **taules** o **entitats**. Cada instància d’una entitat constitueix una **fila** o **registre** de la taula. Els atributs de l’entitat són les **columnes** o **camps** de la taula.

Les taules s’associen mitjançant **relacions** entre camps. Cada taula pot tenir una **clau principal** o **clau primària**, l la qual identifica de forma unívoca cada registre de la taula, i que està formada per un camp o un grup de camps. Alguns camps poden ser **clau forana**, que fa referència a un camp d’una altra taula i serveix per relacionar registres de dues taules

## API JDBC de connexió a bases de dades

Java proporciona la biblioteca **JDBC** per a abstreure els detalls de la connexió a diferents bases de dades i permetre una programació independent del sistema gestor de bases de dades que estem utilizant.

Disposa d'una interfície **Driver** que encapsula els detalls de la connexió entre el codi java i la base de dades. El driver a incloure en el projecte és específic de la base de dades que utilitzem.

Les principals classes de JDBC són:

|Nom  | Utilitat |
|-- | --- |
|**DriverManager** | Carrega els drivers i gestiona les connexions |
|**Driver** | Tradueix les invocacions de l’API a les de la base de dades concreta |
|**Connection**| Encapsula una sessió entre l’aplicació i la base de dades |
|**Statement**| Classe per a sentència SQL |
|**Metadata**| Informació sobre la base de dades |
|**ResultSet**| Cursor amb les files i columnes resultat d’una consulta |

L'esquema bàsic d'operacions a realitzar per treballar amb bases de dades és el següent:
1. Carregar el driver de la base de dades i registrar-lo al DriverManager. Aquesta operació pot ser automàtica en molts casos.
2. Obtenir una connexió (classe **Connection**) amb el mètode **getConnection()** de
**DriverManager**.
3. Crear una sentència (classes **Statement**, **PreparedStatement**, **CallableStatement**)
4. Executar la sentència i, si escau, obtenir els resultats (classe **ResultSet**, entre altres)

## Exemple de connexió a base de dades amb una única taula

### Creació de la base de dades

Anem a crear una base de dades, un usuari per accedir-hi des de la nostra aplicació i li assignarem els permisos necessaris.

Després, haurem de crear una taula amb l'estructura adequada a la informació a desar i inserirem dades de prova.

Convé desar i tenir actualitzar un scrip sql que contingui totes les ordres per realitzar les accions anteriorment descrites, de manera que es pugui suprimir la base de dades (o la taula o les dades) i recuperar-la a l'estat inicial en qualsevol moment. El següent script és el que es requereix per a construir la base de dades de l'exemple:

```sql
-- Create user for local access.
CREATE USER 'usrcountry'@'localhost' IDENTIFIED BY 'pswcountry';
-- Create database.
CREATE DATABASE dbcountry
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
-- Grant permissions.
GRANT SELECT, INSERT, UPDATE, DELETE ON dbcountry.* TO 'usrcountry'@'localhost';
-- Use database.
USE dbcountry;
-- Create table 'countries'
CREATE TABLE `countries` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL,
`capital` VARCHAR(40) DEFAULT NULL,
`surface` DOUBLE DEFAULT 0.0,
`inhabitants` INT DEFAULT 0,
`pib` DOUBLE DEFAULT 0.0,
`lifeexpectancy` INT DEFAULT 0,
PRIMARY KEY (`id`)
) ENGINE=InnoDB;
-- Data insertion.
INSERT INTO `countries` (`id`, `name`, `capital`, `surface`, `inhabitants`, `pib`, `lifeexpectancy`) 
VALUES
(1, 'Albania', 'Tirana', 28748, 3100000, 3189, 73),
(2, 'Alemania Federal', 'Berlín', 357021, 82000000, 23742, 77),
(3, 'Andorra', 'AndorraLaVieja', 453, 75000, 17420, 78),
(4, 'Austria', 'Viena', 83859, 8100000, 25089, 77),
(5, 'Belarús', 'Minsk', 207600, 10100000, 6876, 68),
(6, 'Bélgica', 'Bruselas', 30528, 10300000, 25443, 78),
(7, 'BosniayHerzegovina', 'Sarajevo', 51129, 4100000, 1086, 56),
(8, 'Bulgaria', 'Sofia', 110994, 7900000, 5071, 70),
(9, 'Croacia', 'Zagreb', 56610, 4700000, 7387, 73),
(10, 'Dinamarca', 'Copenhague', 44493, 5300000, 25869, 76),
(11, 'Eslovaquia', 'Bratislava', 49035, 5400000, 10591, 73),
(12, 'Eslovenia', 'Lubiana', 20258, 2000000, 15977, 75),
(13, 'España', 'Madrid', 505989, 39900000, 18079, 78),
(14, 'Estonia', 'Tallin', 45227, 1400000, 8355, 70),
(15, 'FederacióndeRusia', 'Moscú', 17075400, 144700000, 7473, 66),
(16, 'Finlandia', 'Helsinki', 338145, 5200000, 23, 77),
(17, 'Francia', 'París', 543965, 59500000, 22897, 78),
(18, 'Grecia', 'Atenas', 131957, 10600000, 15414, 78),
(19, 'Hungría', 'Budapest', 93030, 9900000, 11430, 71),
(20, 'Irlanda', 'Dublin', 70285, 3800000, 25918, 76),
(21, 'Islandia', 'Reykjavik', 102819, 281000, 27835, 79),
(22, 'Italia', 'Roma', 301308, 57500000, 22172, 78),
(23, 'Letonia', 'Riga', 64610, 2400000, 6264, 70),
(24, 'Liechtenstein', 'Vaduz', 160, 32015, 37000, 72),
(25, 'Lituania', 'Vilnius', 65300, 3700000, 6656, 71),
(26, 'Luxemburgo', 'Luxemburgo', 2586, 442000, 42769, 77),
(27, 'Macedonia', 'Skopje', 25713, 2000000, 4651, 73),
(28, 'Malta', 'La Valletta', 316, 392000, 15189, 77),
(29, 'Moldova, Rep. de', 'Kisinev', 33700, 4300000, 2037, 66),
(30, 'Mónaco', 'Mónaco', 2, 33000, 26470, 78),
(31, 'Noruega', 'Oslo', 323758, 4500000, 28433, 78),
(32, 'Países Bajos', 'Amsterdam', 41526, 15900000, 24215, 78),
(33, 'Polonia', 'Varsovia', 312685, 38600000, 8450, 73),
(34, 'Portugal', 'Lisboa', 91831, 10000000, 16064, 75),
(35, 'Reino Unido de Gran Bretaña', 'Londres', 244110, 59500000, 22093, 77),
(36, 'República Checa', 'Praga', 78866, 10300000, 13018, 74),
(37, 'Rumania', 'Bucarest', 238391, 22400000, 6041, 69),
(38, 'Suecia', 'Estocolmo', 449964, 8800000, 22636, 79),
(39, 'Suiza', 'Berna', 41285, 7200000, 27171, 78),
(40, 'Ucrania', 'Kiev', 603700, 49100000, 3458, 68);
```

### Creació del model de dades de l'aplicació

En primer lloc, hem de descarregar el driver específic per a java del sistema gestor de bases de dades que anem a utilitzar. En el nostre cas, es tracta de MariaDb (o Mysql). 

Descarreguem el [driver MySql Connector/J](https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-8.0.31.zip) de MySql per a Java i l'incorporem com a biblioteca del nostre projecte.

Ara haurem de crear les classes que defineixen els tipus de dades que volem recuperar i desar a la base de dades.

En el nostre cas, definim la classe **Country**, amb els mateixos atributs que els camps de la taula.

```java
/**
 * ADT for country.
 * @author Jose
 */
public class Country {
    private int id;
    private String name;
    private String capital;
    private double surface;
    private int inhabitants;
    private double pib;
    private int lifeexpectancy;

    public Country(int id, String name, String capital, double surface, int inhabitants, double pib, int lifeexpectancy) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.surface = surface;
        this.inhabitants = inhabitants;
        this.pib = pib;
        this.lifeexpectancy = lifeexpectancy;
    }

    public Country() {
    }

    public Country(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(int inhabitants) {
        this.inhabitants = inhabitants;
    }

    public double getPib() {
        return pib;
    }

    public void setPib(double pib) {
        this.pib = pib;
    }

    public int getLifeexpectancy() {
        return lifeexpectancy;
    }

    public void setLifeexpectancy(int lifeexpectancy) {
        this.lifeexpectancy = lifeexpectancy;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Country other = (Country) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", capital=").append(capital);
        sb.append(", surface=").append(surface);
        sb.append(", inhabitants=").append(inhabitants);
        sb.append(", pib=").append(pib);
        sb.append(", lifeexpectancy=").append(lifeexpectancy);
        sb.append('}');
        return sb.toString();
    }

}
```

Convé encapsular dintre d'una classe els paràmetres de connexió a la base de dades i el mecanisme de connexió. Creemm la classe **DbConnect**:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
    
/**
    * encapsulates data for database connection.
    *
    * @author Jose
    */
public final class DbConnect {
    
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String PROTOCOL = "jdbc:mysql:";
    static final String HOST = "127.0.0.1";
    static final String BD_NAME = "dbcountry";
    static final String USER = "usrcountry";
    static final String PASSWORD = "pswcountry";
    static final String PARAMS = "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER);
    }
    
    /**
        * gets and returns a connection to database
        *
        * @return connection
        * @throws java.sql.SQLException
        */
    public static Connection getConnection() throws SQLException {
        final String BD_URL = String.format("%s//%s/%s?%s", PROTOCOL, HOST, BD_NAME, PARAMS);
        Connection conn = null;
        conn = DriverManager.getConnection(BD_URL, USER, PASSWORD);
        return conn;
    }
}
```
Per continuar cal repassar les consultes SQL apreses al mòdul de bases de dades.

![](/images/sql-join-table.jpg)

Ja només ens queda programar una classe principal **ShowCountriesJdbcMySql** que provi de fer les operacions habituals (CRUD) de consulta i modificació de dades.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example of connection to MySql database using Java JDBC bridge.
 *
 * @author Jose
 */
public class ShowCountriesJdbcMySql {

    public static void main(String[] args) throws Exception {
        ShowCountriesJdbcMySql ap = new ShowCountriesJdbcMySql();
        ap.run();
    }

    private void run() {
        boolean exit = false;
        try {
            //load the driver (only once).
            DbConnect.loadDriver();
            do {
                int optNumber = promptMenu();
                switch (optNumber) {
                    case 0 -> exit = true;
                    case 1 -> printAllCountries();
                    case 2 -> printAllCountriesAndMetadata();
                    case 3 -> insertANewCountry();
                    case 4 -> modifyACountry();
                    case 5 -> deleteACountry();
                    default -> System.out.println("Bad option!");
                }
            } while (!exit);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Displays menu and reads option from user.
     *
     * @return the option selected by user or -1 in case of error.
     */
    private static int promptMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] options = {
            "Quit", "Print all countries", "Print all countries and metadata",
            "Insert a new country", "Modify a country", "Delete a country",};
        for (int i = 0; i < options.length; i++) {
            System.out.format("[%d]. %s\n", i, options[i]);
        }
        System.out.print("Choose a test to execute: ");
        int op;
        try {
            op = Integer.parseInt((reader.readLine()));
        } catch (NumberFormatException | IOException e) {
            op = -1;
        }
        return op;
    }

    private String inputString(String message) {
        String answer = "";
        try {
            System.out.print(message);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            answer = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }
    
    private void printAllCountriesAndMetadata() {
        System.out.println("Executing: " + (new Exception()).getStackTrace()[0].getMethodName());
        try ( Connection conn = DbConnect.getConnection() ) { //get a connection to database.
            if (conn != null) {
                //create a statement to perform queries.
                Statement stmt = conn.createStatement();
                //execute the query and get a resultset.
                String query = "SELECT * FROM countries WHERE lifeexpectancy > 77";
                ResultSet res = stmt.executeQuery(query);
                //get and write metadata.
                ResultSetMetaData rsmd = res.getMetaData();
                int numCol = rsmd.getColumnCount();
                System.out.println("Number of columns: " + rsmd.getColumnCount());
                System.out.println("Label \t Name \t Type \t Type name \t Auto increment \t Null \t Precision");
                for (int i = 0; i < numCol; i++) {
                    System.out.print(rsmd.getColumnLabel(i + 1) + " \t ");
                    System.out.print(rsmd.getColumnName(i + 1) + " \t ");
                    System.out.print(rsmd.getColumnType(i + 1) + " \t ");
                    System.out.print(rsmd.getColumnTypeName(i + 1) + " \t ");
                    System.out.print(rsmd.isAutoIncrement(i + 1) + " \t ");
                    System.out.print(rsmd.isNullable(i + 1) + " \t ");
                    System.out.print(rsmd.getPrecision(i + 1) + " \t ");
                    System.out.println("");
                }
                //write headers.
                System.out.println("");
                for (int i = 0; i < numCol; i++) {
                    System.out.print(rsmd.getColumnLabel(i + 1) + "\t");
                }
                System.out.println("");
                //iterate the resultset and write the rows.
                while (res.next()) {
                    //convert data to java format.
                    int id = res.getInt("id");
                    String name = res.getString("name");
                    String capital = res.getString("capital");
                    double surface = res.getDouble("surface");
                    int inhabitants = res.getInt("inhabitants");
                    double pib = res.getDouble("pib");
                    int lifeexpectancy = res.getInt("lifeexpectancy");
                    //instantiate a country object.
                    Country country = new Country(id, name, capital, surface, inhabitants, pib, lifeexpectancy);
                    //write data.
                    System.out.format("%d\t%-15s\t%-15s\t%8.0f\t%10d\t%8.2f\t%d\n",
                            country.getId(),
                            country.getName(), country.getCapital(),
                            country.getSurface(), country.getInhabitants(),
                            country.getPib(), country.getLifeexpectancy());
                }
                System.out.println("");
                //close resources.
                res.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printAllCountries() {
        System.out.println("Executing: " + (new Exception()).getStackTrace()[0].getMethodName());
        try ( Connection conn = DbConnect.getConnection() ) { //get a connection to database.
            if (conn != null) {
                //create a statement to perform queries.
                Statement stmt = conn.createStatement();
                //execute the query and get a resultset.
                String query = "SELECT * FROM countries WHERE lifeexpectancy > 77";
                ResultSet res = stmt.executeQuery(query);
                //write headers.
                System.out.println("");
                System.out.format("%s\t%-15s\t%-15s\t%-10s\t%-10s\t%-10s\t%s\n",
                            "id",
                            "name", "capital",
                            "surface", "inhabitants",
                            "pib", "life expectancy");
                System.out.println("");
                //iterate the resultset and write the rows.
                while (res.next()) {
                    //convert data to java format.
                    int id = res.getInt("id");
                    String name = res.getString("name");
                    String capital = res.getString("capital");
                    double surface = res.getDouble("surface");
                    int inhabitants = res.getInt("inhabitants");
                    double pib = res.getDouble("pib");
                    int lifeexpectancy = res.getInt("lifeexpectancy");
                    //instantiate a country object.
                    Country country = new Country(id, name, capital, surface, inhabitants, pib, lifeexpectancy);
                    //write data.
                    //System.out.println(country.toString());
                    System.out.format("%d\t%-15s\t%-15s\t%8.0f\t%10d\t%8.2f\t%d\n",
                            country.getId(),
                            country.getName(), country.getCapital(),
                            country.getSurface(), country.getInhabitants(),
                            country.getPib(), country.getLifeexpectancy());
                }
                System.out.println("");
                //close resources.
                res.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertANewCountry() {
        System.out.println("Executing: " + (new Exception()).getStackTrace()[0].getMethodName());
        try ( Connection conn = DbConnect.getConnection() ) { //get a connection to database.
            if (conn != null) {
                //create a statement to perform queries.
                Statement stmt = conn.createStatement();
                //execute the query.
                String query
                        = "insert into countries values "
                        + "(null, 'Wonderland', 'Capital', 1000.0, 100000, 2500.0, 90)";
                int numRowsAffected = stmt.executeUpdate(query);
                System.out.format("%d rows created\n", numRowsAffected);
                //close resources.
                stmt.close();
            }
            System.out.println("");
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modifyACountry() {
        System.out.println("Executing: " + (new Exception()).getStackTrace()[0].getMethodName());
        int id = Integer.parseInt(inputString("Input id: "));
        try ( Connection conn = DbConnect.getConnection() ) { //get a connection to database.
            if (conn != null) {
                //create a statement to perform queries.
                Statement stmt = conn.createStatement();
                //execute the query.
                String query
                        = String.format("update countries set pib = 50000.0 where id=%d", id);
                int numRowsAffected = stmt.executeUpdate(query);
                System.out.format("%d rows updated\n", numRowsAffected);
                //close resources.
                stmt.close();
            }
            System.out.println("");
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteACountry() {
        System.out.println("Executing: " + (new Exception()).getStackTrace()[0].getMethodName());
        int id = Integer.parseInt(inputString("Input id: "));
        try ( Connection conn = DbConnect.getConnection() ) { //get a connection to database.
            if (conn != null) {
                //execute the query.
                //create a statement to perform queries.
                Statement stmt = conn.createStatement();
                //execute the query.
                String query
                        = String.format("delete from countries where id=%d", id);
                int numRowsAffected = stmt.executeUpdate(query);
                System.out.format("%d rows deleted\n", numRowsAffected);
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

}
```

I ja podem compilar i provar l'aplicació.

Sempre hem de vigilar les entrades de l'usuari i netejar-les abans d'enviar-les a una consulta sobre la base de dades, per tal d'evitar la [injecció de codi](https://es.wikipedia.org/wiki/Inyecci%C3%B3n_SQL).

![](/images/sql_injection_son_name.png)
