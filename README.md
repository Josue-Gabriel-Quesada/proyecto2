
#Proyecto ORM

Bienvenidos a mi proyecto sobre mapeo de clases para la bases de datos(Oracle,MySQL,Mongo) en el lenguaje de java.

####Objetivos
El objetivo de este proyecto es desarrollar un sistema flexible que permita mapear clases de objetos en Java, en diferentes bases de datos relacionales y NoSQL, como Oracle, MySQL y MongoDB. Este sistema facilitará la interacción  con las bases de datos, mediante el uso de tecnicas de mapeo objeto-relacional(ORM), permite a los usuarios trabjar con base de datos, sin tener que preocuparse por las complejidades del manejo directo de las bases de datos subyacentes.

##Característica

<ul>
<li>Conexiónes.
Integré una clase para las conexiones a la base de datos, a través de variables de entorno para mayor flexibilidad.
</li>
<li>Abstracción de la base de datos.
Proporciona una capa de abstracción  que permite a los usuarios realizar operaciones CRUD(crear,leer,actualizar,eliminar) sin necesidad de hacer consultas o comandos a la base de datos.
</li>
<li>Configuración y personalización sencilla.
Permite configuraciones como personalizacion facil de las entidades y las relaciones entre ellas, proporcinonando una sintaxis intuitiva y facil para el usuario.
</li>

<li>Eficiencia y rendimiento.
Optimiza el rendimiento del sistema para manejar grandes cantidades de datos y transacciones de manera eficiente.
</li>

<li>Documentación y ejemplos prácticos.
Incluye una documentación clara con ejemplos practios, facilitando al usuario la comprensión y el uso del sistema.
</li>
</ul>

##Estructura del Proyecto

```
DatabaseConnection.java
```
Clase que gestiona las conexiónes de las tres bases de datos.

```
Worker.java
```
Clase que representa un Objeto con atributos, paras ser insertado en una base de datos.

```
MappingMongo.java
MappingMySQL.java
MappingOracle.java
```
Clases  que implementa las operaciones CRUD para esa base de datos en especifico.

```
TestMongo.java
TestMySQL.java
TestOracle.java
```
Clases que se encargan de hacer las pruebas unitarias para validar las clases de los Mapping de cada base de datos.

```
pom.xml
```
Se encarga de las dependencias de cada base de datos, para hacer una conexión estable.
##Requisitos

<ul>
<li>**Agregar dependencias de cada base de datos**: Incluir las dependencias necesarias en el archivo `pom.xml` (para Maven) o `build.gradle` (para Gradle) del proyecto. Estas dependencias deben corresponder a los drivers de las bases de datos que se utilizarán (Oracle, MySQL, MongoDB). A continuación se mostraran las dependencias específicas:
  <ul>
    <li>Oracle: `ojdbc8`</li>
    <li>MySQL: `mysql-connector-java`</li>
    <li>MongoDB: `mongodb-driver-sync`</li>
  </ul>
</li>

<li>**Instalación de las bases de datos correspondientes**: Asegurarse de tener instaladas y configuradas las bases de datos que se van a utilizar.
</li>

<li>**Configuración de las bases de datos**: Crear las bases de datos y usuarios necesarios en cada uno de los sistemas de gestión de bases de datos, asignando los permisos adecuados para que la aplicación pueda conectarse y realizar operaciones CRUD.</li>
	
</ul>

##Uso

<ol>
  <li>Crear una clase Objeto con atributos.</li>
  <li>Asegurarse que los usuarios esten bien escritos en la clase `DatabaseConnection.java` </li>
  <li>Pasarle el Objeto creado a la clase Test de la base de datos que desea utilizar.</li>
  <li>Llamar a las funciones de la clase Test que esta utilizando y que accion del CRUD desea ejecutar.</li>
</ol>

##Nota!
Agradecemos enormemente tu interés en nuestro proyecto. Este sistema ha sido creado para simplificar el mapeo de clases en Java a diversas bases de datos. Valoramos mucho cualquier tipo de retroalimentación que nos ayude a mejorar y a hacer de esta herramienta una opción más útil y eficiente para la comunidad de programadores, esperamos que este proyecto sea de gran utilidad en sus futuros proyectos.❤️

######-remember-
######While(!success){  tryAgain(); }
