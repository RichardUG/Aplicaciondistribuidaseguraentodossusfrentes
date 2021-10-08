# Aplicacion distribuida segura en todos sus frentes

## Descripción

En este taller trabajaremos el depliegue de instancias de codigo ```java``` como 5 imagenes que estaran distribuidoras en contenedores ```docker``` entre estas 5 imagenes la primera es  ```RoundRobin``` el cual es un balanceador de cargas que se encuentra en el puerto ```35000```  y que distribuye las solicitudes en tres intancias de ```LogService``` que se llaman ```LogService1``` qeu se encuentra en el puerto ```35001```, ```LogService2``` que se encuentra en el puerto ```35002``` y ```LogService3``` que se encuentra en el puerto ```35003``` y estos se comunican una imagen que se instancio de mongoDB el cual contendra la instancia para poder hacer uso de nuestra base de datos; después todos estos contenedores se alojaran en un repositorio de ```Docker``` para que almacene todas las imagenes y posteriormente este contenedor se despliega en una maquina virtual creada en ```ÀWS```; cada petición realizada a ```RoundRobin``` es un mensaje que se envia a las diferentes instancias de ```LogService``` una petición para guardar en la base de datos ```MongoDB``` y después envia otra para obtener los ultimos 10 mensajes registrados; cada petición se envia a una instancia de ```LogService``` diferente

## Prerrequisitos

Para  elaborar este proyecto requeimos las siguientes tecnologias:
* [Maven](https://es.wikipedia.org/wiki/Maven): Herramienta la cual permite realizar la construción de proyectos, realizarles pruebas y otras funciones.
* [Git](https://es.wikipedia.org/wiki/Git): Software de control de versionamiento centralizado.
* [Docker](https://es.wikipedia.org/wiki/Docker_(software)): Es un proyecto de código abierto que automatiza el despliegue de aplicaciones dentro de contenedores de software, proporcionando una capa adicional de abstracción y automatización de virtualización de aplicaciones en múltiples sistemas operativos
 
También necesitamos tener instalado y habilitado el protocolo SSH en nuestro computador 
* [SSH](https://es.wikipedia.org/wiki/Secure_Shell): Es el nombre de un protocolo y del programa que lo implementa cuya principal función es el acceso remoto a un servidor por medio de un canal seguro en el que toda la información está cifrada

Para asegurar que el usuario cumple con todos los prerrequisitos para poder ejecutar el programa, es necesario disponer de un Shell o Símbolo del Sistema para ejecutar los siguientes comandos para comprobar que todos los programas están instalados correctamente, para así compilar y ejecutar tanto las pruebas como el programa correctamente.

```
docker --version
mvn --version
git --version
java --version
```

## Instalación

Para realizar la instalcaión de nuestro programa debemos ir a la linea de comandos de nuestro sistema operativo y hacer uso del siguiente comando

```
git clone https://github.com/RichardUG/RoundRobinyLogsService-DockeryAWS.git
```

## Ejecución










## Construido con

* [Maven](https://es.wikipedia.org/wiki/Maven): Herramienta la cual permite realizar la construción de proyectos, realizarles pruebas y otras funciones.
* [Git](https://es.wikipedia.org/wiki/Git): Software de control de versionamiento centralizado.
* [Intelij](https://es.wikipedia.org/wiki/IntelliJ_IDEA): es un entorno de desarrollo integrado (IDE) para el desarrollo de programas informáticos. Es desarrollado por JetBrains, y está disponible en dos ediciones: edición para la comunidad1 y edición comercial.
* [Java](https://www.oracle.com/java/): Lenguaje de programación de propósito general, es decir, que sirve para muchas cosas, para web, servidores, aplicaciones móviles, entre otros. Java también es un lenguaje orientado a objetos, y con un fuerte tipado de variables.
* [Docker](https://es.wikipedia.org/wiki/Docker_(software)): Es un proyecto de código abierto que automatiza el despliegue de aplicaciones dentro de contenedores de software, proporcionando una capa adicional de abstracción y automatización de virtualización de aplicaciones en múltiples sistemas operativos
* [SSH](https://es.wikipedia.org/wiki/Secure_Shell): Es el nombre de un protocolo y del programa que lo implementa cuya principal función es el acceso remoto a un servidor por medio de un canal seguro en el que toda la información está cifrada
* [AWS](https://aws.amazon.com/es/): Conjunto de herramientas y servicios de cloud computing de Amazon, que engloba una gran cantidad de servicios para poder realizar distintos tipos de actividades en la nube. Desde almacenamiento a la gestión de instancias, imágenes virtuales, desarrollo de aplicaciones móviles, etc

## Autor
[Richard Santiago Urrea Garcia](https://github.com/RichardUG)

## Licencia & Derechos de Autor
**©** Richard Santiago Urrea Garcia, Estudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito

Licencia bajo la [GNU General Public License](https://github.com/RichardUG/RoundRobinyLogsService-DockeryAWS/blob/master/LICENSE).

