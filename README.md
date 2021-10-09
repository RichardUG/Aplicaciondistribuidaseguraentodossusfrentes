# Aplicacion distribuida segura en todos sus frentes

## Descripción

Este taller esta enfocado en el manejo de certificados ```http + ssl = https```  en nuestras aplicaciones deistribuidas, es decir vamos  a aplicar estos certificados a cada una de nuestras aplicaciones y las vamos a conectar entre ellas.

## Arquitectura

La arquitectura la podemos observar de dos formas, una desplegada en el localhost y otra en el aws

### Arquitectura localhost

![](/img/arqlocalhost.PNG)

Acá podemos observar que la arquitectura se conforma de dos servicios diferentes que se comunican entre ell a través de protocolo ```https```  y que el host principal, es decir el que contiene el login es consumido desde el browser por el usuario también por protocolos ```https```, en cada servicio se encuentara una aplicación ```java``` construida con ```spark/jetty``` que ejecuta el servicio, en uno el ```LoginService``` y en el otro ```OtherService``` 

### Arquitectura AWS

![](/img/arqaws.PNG)

Esta arquitectura es muy similar a la del localho0st, pero difiere en que los servicios estan contenidos dentro de la maquina EC2 de AWS

## Prerrequisitos

Para  elaborar este proyecto requeimos las siguientes tecnologias:
* [Maven](https://es.wikipedia.org/wiki/Maven): Herramienta la cual permite realizar la construción de proyectos, realizarles pruebas y otras funciones.
* [Git](https://es.wikipedia.org/wiki/Git): Software de control de versionamiento centralizado.
 
También necesitamos tener instalado y habilitado el protocolo SSH en nuestro computador 
* [SSH](https://es.wikipedia.org/wiki/Secure_Shell): Es el nombre de un protocolo y del programa que lo implementa cuya principal función es el acceso remoto a un servidor por medio de un canal seguro en el que toda la información está cifrada

Para asegurar que el usuario cumple con todos los prerrequisitos para poder ejecutar el programa, es necesario disponer de un Shell o Símbolo del Sistema para ejecutar los siguientes comandos para comprobar que todos los programas están instalados correctamente, para así compilar y ejecutar tanto las pruebas como el programa correctamente.

```
mvn --version
git --version
java --version
```

## Instalación

Para realizar la instalcaión de nuestro programa debemos ir a la linea de comandos de nuestro sistema operativo y hacer uso del siguiente comando

```
git clone https://github.com/RichardUG/Aplicaciondistribuidaseguraentodossusfrentes.git
```

## Despliegue en localhost

### Creación llaves y certificados de seguridad

> Antes de poder desplegar y utilizar nuestros servicios debemos crear nuesta llave de confianza, nuestra llave de segguridad y nuestro certificado, asi que estando ubicados en la carpeta keystores del servicio SegureLogin ingresaremos en orden los siguientes comandos
>
> Crear llave de seguridad
```
keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048-storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
```
> E ingresamos los datos de la siguiente manera, realmente no hay mucho importancia por los datos que digitaremos, excepto por el primero ya que ese indica el nombre de la maquina en la cul se estara corriendo el servicio, en este caso localhost
> 
> ![](/img/keystorelocalhost.PNG)

> Crear certificado

```
keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -file ecicert.cer
```

> Crear llave de confianza

```
keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
```

> Despues de haber creado los 3 archivos los vamos a copiar y pegar en la carpeta keystores de  el servicio "SecureOther" sea de manera manual o con comandos, como se prefiera

### Construcción

> Para construir el proyecto ejecutaremos los siguientes comandos, estando ubicados en la raiz del proyecto

```
cd SegureLogin
```

```
mvn clean build
```

```
mvn package
```

```
cd ..
```

```
cd SecureOther
```

```
mvn clean build
```

```
mvn package
```

### Despliegue

> Vamos a ejecutar cada uno de nuestros dos proyectos Java, sea corriendolos desde nues IDE o por linea de comandos de la siguiente manera
>
> Nos ubicaremos con una terminal en la carpeta SegureLogin e introduciremos el siguiente comando

```
java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.App
```

> Y en otra terminal difernte nos ubicaremos en la carpeta SecureOther e introduciremos el mismo comando

```
java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.App
```

### Prueba

> Tras haber ejecutado ambos, podemos dirigirnos a nuestro browser y consultar la dirección 

```
https://localhost:4567
```

> Por lo cual nos saldra la siguiente pestaña informandonos que es un sitio no seguro, aceptamos el riesgo para proseguir
>
> ![](/img/amenazalocal.PNG)
> 
> Tras aceptar el riesgo llegaremos a la pagina del login e introduciremos como credenciales

```
User name: RichhardUG
Password: 123
```

> podemos probar con credenciales diferentes y observaremos que on se puede ingresar
> 
> ![](/img/loginlocal.PNG)
>
> Ahora tras haber accedido nos aparecera la siguiente pestaña
> 
> ![](/img/noresultadolocal.PNG)
> 
> tras esperar un momento mientras consulta el servicio ahora nos cargara en la pestaña un mensaje de aprobación y la fecha con hora exacta en que se accedio
> 
> ![](/img/siresultadolocal.PNG)






## Despliegue en AWS

### Adquirir Servicios

> Debemos pasar de nuestra maquina host a nuestra maquina EC2 un archivo comprimido con ambos servicios, para hacer esto debemos asegurarnos de haber realizado el package a anuestros pryectos, después comprimimos la carpeta que contiene ambos proyecto y ese archivo comprimido lo llevamos a la carpeta donde se enetran nuestras claves de conexión  a nuestra maquina EC2
> 
> ![](/img/zip.PNG)
> 
> Después en esta carpeta accederemos a una linea de comandos y pgamos la dirección de conexión via ssh que nos brinda AWS para acceder a nuestra maquina EC2, reemplazando ```ssh``` por ```sftp```
> 
> ![](/img/sftp.PNG)
> 
> ahora transferimos el archivo hacia nuestra maquina con el comando ```put``` y el nombre de nuestro archivo
> 
> ![](/img/put.PNG)
> 
> salimos escribiendo el comando ```exit``` y ahora ingresamos a nuestra maquina con el protocolo ```ssh```
> 
> ![](/img/ssh.PNG)
> 
> Descomprimimos nuestro archivo que fue transferido con el comando ```unzip``` y el nombre del archivo
> 
> ![](/img/unzip.PNG)

### Creación llaves y certificados de seguridad

> En este caso ya teniamos creadas nuestras llaves de seguuridad pero bajo el dominio de localhost, ahora debemos crearlas bajo el dominio de nuestra maquina amazon, por lo cual vamos a ingresar los siguientes comandos para eliminar las llaves que ya teniamos

```
cd Aplicaciondistribuidaseguraentodossusfrentes
```

```
cd SegureLogin
```

```
rm -rf keystores
```

```
cd ..
```

```
cd SecureOther
```

```
rm -rf keystores
```

```
cd ..
```

> Ahora vamos a crear unas nuevas por lo cual vamos a acceder a la ubicación SegureLogin

```
cd SegureLogin
```

> crearemos un nuevo directorio keystores

```
mkdir keystores
```

> accederemos a él

```
cd keystores
```

> y crearemos llave de seguridad
```
keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048-storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
```
> E ingresamos los datos de la siguiente manera, realmente no hay mucho importancia por los datos que digitaremos, excepto por el primero ya que ese indica el nombre de la maquina en la cul se estara corriendo el servicio, en este caso debe ser el dominio de la maquina EC2
> 
> ![](/img/keystoreaws.PNG)

> Crear certificado

```
keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -file ecicert.cer
```

> Crear llave de confianza

```
keytool -import -file ./ecicert.cer -alias firstCA -keystore myTrustStore
```

> Despues de haber creado los 3 archivos debemos copiarlos y pegarlos en la carpeta SecureOther, de la siguiente manera
> 
> retrocederemos a la carpeta de SegureLogin

```
cd ..
```

> e introduciremos el siguiente comando para copiar toda la carpeta a nuestro destino

```
cp -r keystores/ ~/Aplicaciondistribuidaseguraentodossusfrentes/SecureOther/
```

### Despliegue

>Tras haber realizado los pasos anteriores ahora podemos desplegar el proyecto, para ello necesitamos dos terminales abiertas y conectadas a través de ssh a nuestra maquina EC2, con una nos ubicaremos en la carpeta SecureOther y con otra en la carpeta SegureLogin, en ambas terminales vamos a ingresar el siguiente comando

```
java -cp "target/classes:target/dependency/*" edu.escuelaing.arep.App
```

### Prueba

> Tras haber ejecutado ambos, podemos dirigirnos a nuestro browser y consultar la dirección 

```
https://+ruta del servicio amazon+:4567
```

> Por lo cual nos saldra la siguiente pestaña informandonos que es un sitio no seguro, aceptamos el riesgo para proseguir
>
> ![](/img/amenazaaws.PNG)
> 
> Tras aceptar el riesgo llegaremos a la pagina del login e introduciremos como credenciales

```
User name: RichhardUG
Password: 123
```

> podemos probar con credenciales diferentes y observaremos que no se puede ingresar
> 
> ![](/img/loginaws.PNG)
>
> Ahora tras haber accedido nos aparecera la siguiente pestaña
> 
> ![](/img/noresultadoaws.PNG)
> 
> tras esperar un momento mientras consulta el servicio ahora nos cargara en la pestaña un mensaje de aprobación y la fecha con hora exacta en que se accedio
> 
> ![](/img/siresultadoaws.PNG)

## Video explicación despliegue en AWS

Podemos abrir este enlace y descargarlo

[Descargar cideo en github](https://github.com/RichardUG/Aplicaciondistribuidaseguraentodossusfrentes/raw/master/Video%20explicacion%20amazon%20AWS/Explicaci%C3%B3n%20despliegue%20AWS.mp4)

[Ver en recordings](https://pruebacorreoescuelaingeduco.sharepoint.com/sites/yo871/Shared%20Documents/Forms/AllItems.aspx?id=%2Fsites%2Fyo871%2FShared%20Documents%2FGeneral%2FRecordings%2FReuni%C3%B3n%20en%20%5FGeneral%5F%2D20211007%5F234611%2DGrabaci%C3%B3n%20de%20la%20reuni%C3%B3n%2Emp4&parent=%2Fsites%2Fyo871%2FShared%20Documents%2FGeneral%2FRecordings&p=true&originalPath=aHR0cHM6Ly9wcnVlYmFjb3JyZW9lc2N1ZWxhaW5nZWR1Y28uc2hhcmVwb2ludC5jb20vOnY6L3MveW84NzEvRWFwUmRndDBWX2xLa2FBUTlucU1WTzRCVHdPaWJLcWFvWURZdVBRTGo1TUpaZz9ydGltZT1KR1VxZEw2SzJVZw)



## Construido con

* [Maven](https://es.wikipedia.org/wiki/Maven): Herramienta la cual permite realizar la construción de proyectos, realizarles pruebas y otras funciones.
* [Git](https://es.wikipedia.org/wiki/Git): Software de control de versionamiento centralizado.
* [Intelij](https://es.wikipedia.org/wiki/IntelliJ_IDEA): es un entorno de desarrollo integrado (IDE) para el desarrollo de programas informáticos. Es desarrollado por JetBrains, y está disponible en dos ediciones: edición para la comunidad1 y edición comercial.
* [Java](https://www.oracle.com/java/): Lenguaje de programación de propósito general, es decir, que sirve para muchas cosas, para web, servidores, aplicaciones móviles, entre otros. Java también es un lenguaje orientado a objetos, y con un fuerte tipado de variables.
* [SSH](https://es.wikipedia.org/wiki/Secure_Shell): Es el nombre de un protocolo y del programa que lo implementa cuya principal función es el acceso remoto a un servidor por medio de un canal seguro en el que toda la información está cifrada
* [AWS](https://aws.amazon.com/es/): Conjunto de herramientas y servicios de cloud computing de Amazon, que engloba una gran cantidad de servicios para poder realizar distintos tipos de actividades en la nube. Desde almacenamiento a la gestión de instancias, imágenes virtuales, desarrollo de aplicaciones móviles, etc

## Autor
[Richard Santiago Urrea Garcia](https://github.com/RichardUG)

## Licencia & Derechos de Autor
**©** Richard Santiago Urrea Garcia, Estudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito

Licencia bajo la [GNU General Public License](https://github.com/RichardUG/RoundRobinyLogsService-DockeryAWS/blob/master/LICENSE).

