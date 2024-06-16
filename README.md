# Proyecto ORM

¡Bienvenidos a mi proyecto sobre mapeo de clases para bases de datos (Oracle, MySQL, MongoDB) en el lenguaje Java!

## Objetivos

El objetivo de este proyecto es desarrollar un sistema flexible que permita mapear clases de objetos en Java, en diferentes bases de datos relacionales y NoSQL, como Oracle, MySQL y MongoDB. Este sistema facilitará la interacción con las bases de datos, mediante el uso de técnicas de mapeo objeto-relacional (ORM), permitiendo a los usuarios trabajar con bases de datos sin tener que preocuparse por las complejidades del manejo directo de las bases de datos subyacentes.

## Características

- **Conexiones**: Integré una clase para las conexiones a la base de datos, a través de variables de entorno para mayor flexibilidad.
- **Abstracción de la base de datos**: Proporciona una capa de abstracción que permite a los usuarios realizar operaciones CRUD (crear, leer, actualizar, eliminar) sin necesidad de hacer consultas o comandos a la base de datos.
- **Configuración y personalización sencilla**: Permite configuraciones como personalización fácil de las entidades y las relaciones entre ellas, proporcionando una sintaxis intuitiva y fácil para el usuario.
- **Eficiencia y rendimiento**: Optimiza el rendimiento del sistema para manejar grandes cantidades de datos y transacciones de manera eficiente.
- **Documentación y ejemplos prácticos**: Incluye una documentación clara con ejemplos prácticos, facilitando al usuario la comprensión y el uso del sistema.

## Estructura del Proyecto

- `DatabaseConnection.java`: Clase que gestiona las conexiones de las tres bases de datos.
- `Worker.java`: Clase que representa un objeto con atributos, para ser insertado en una base de datos.
- `MappingMongo.java`, `MappingMySQL.java`, `MappingOracle.java`: Clases que implementan las operaciones CRUD para esa base de datos en específico.
- `TestMongo.java`, `TestMySQL.java`, `TestOracle.java`: Clases que se encargan de hacer las pruebas unitarias para validar las clases de los mapeos de cada base de datos.
- `pom.xml`: Se encarga de las dependencias de cada base de datos, para hacer una conexión estable.

## Requisitos

1. **Agregar dependencias de cada base de datos**: Incluir las dependencias necesarias en el archivo `pom.xml` (para Maven) o `build.gradle` (para Gradle) del proyecto. Estas dependencias deben corresponder a los drivers de las bases de datos que se utilizarán (Oracle, MySQL, MongoDB). A continuación se mostrarán las dependencias específicas:
    - Oracle: `ojdbc8`
    - MySQL: `mysql-connector-java`
    - MongoDB: `mongodb-driver-sync`
2. **Instalación de las bases de datos correspondientes**: Asegurarse de tener instaladas y configuradas las bases de datos que se van a utilizar.
3. **Configuración de las bases de datos**: Crear las bases de datos y usuarios necesarios en cada uno de los sistemas de gestión de bases de datos, asignando los permisos adecuados para que la aplicación pueda conectarse y realizar operaciones CRUD.

## Uso

1. Crear una clase Objeto con atributos.
2. Asegurarse de que los usuarios estén bien escritos en la clase `DatabaseConnection.java`.
3. Pasar el objeto creado a la clase Test de la base de datos que desea utilizar.
4. Llamar a las funciones de la clase Test que está utilizando y qué acción del CRUD desea ejecutar en el Main.

## Nota

¡Agradecemos enormemente tu interés en nuestro proyecto! Este sistema ha sido creado para simplificar el mapeo de clases en Java a diversas bases de datos. Valoramos mucho cualquier tipo de retroalimentación que nos ayude a mejorar y a hacer de esta herramienta una opción más útil y eficiente para la comunidad de programadores. Esperamos que este proyecto sea de gran utilidad en sus futuros proyectos. ❤️

**-remember-**


While(!success){
  tryAgain();
}

