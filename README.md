# mutant-ms
[![Java CI](https://github.com/olsmca/mutant-ms/actions/workflows/ci.yml/badge.svg)](https://github.com/olsmca/mutant-ms/actions/workflows/ci.yml)
[![Coverage](https://github.com/olsmca/mutant-ms/blob/main/.github/badges/jacoco.svg)](https://github.com/olsmca/mutant-ms/actions/workflows/ci.yml)

Aplicacion Spring Boot responde peticiones REST como prueba tecnica de MercadoLibre.
Para conocer todos los detalles puedes revisar el archivo [Documento Prueba Tecnica]

##How the App Work
La App expone 2 endpoint  ```/mutant``` y ```/stats```

#### POST /mutant
recibe peticiones POST con un arreglo de Strings que representan una matriz a ser analizada, el body de la peticion es la siguiente:
```json
{
"dna": ["TGCAAAGA"
        ,"AATATGCG"
        ,"GGATATGG"
        ,"AAAATGAT"
        ,"ATGACAGG"
        ,"TCGGACGG"
        ,"ATAAGAAA"
        ,"ATAGGAAA"]
}
```
La aplicacion analiza si el ADN es mutante, si esto es correcto retorna un **200 OK**, caso contrario retorna un **403 Forbidden**.
La matriz es mutante si tiene mas de una secuencia de cuatro letras igales horizonta, vertical o oblicua.

Al finalizar la comprobacion se almacena una unica vez en una base de datos Postgres.

#### GET /stats
Este endpoint retorna un JSON con la estadistica de las peticiones almacenadas.
```json
{
    "count_mutant_dna":40, 
    "count_human_dna":100: 
    "ratio":0.4
}
```

##Run Application
* Prerequisitos:
  * Java 11 
  * Docker
  * Git
  

Para ejecutar la aplicacion localmente sin compilar ejecute:

```./gradlew```

>La aplicacion estara disponible en [URL App Local] y [Base de datos H2]

Puede construir la app mediante el siguiente comando:

```./gradlew bootJar```

Para ejecutar el artefacto generado, ejecute

```java -jar build/lib/mutant-ms-0.0.1-SNAPSHOT.jar```

## Integration Test
Las pruebas de integracion esta creadas con Postman, estas se encuentran en el directorio
```/test_data/```

Dentro de este directorio se encuentra la coleccion de postman que contiene la peticiones ```stats/```  y ```mutant/```

Para poder ejecutar las pruebas es necesario instalar las dependencias de npm
####Install Newman
```npm install -g newman```
####Install Report
```npm install -g newman-reporter-htmlextra```

####Run Script
Para correr el script ejecute:

```newman run test_data/MutantDNA.postman_collection.json -d test_data/MutantDNA.postman_data.json -n 100 -r htmlextra,cli```

## Coverage Code
El proyecto incorpora una imagen de sonar para analizar la calidad de codigo. Puede iniciar el servidor local (accesible en http://localhost: 9001).
La autenticacion se encuentra desactivada para un inicio inmediato
#### Run Sonar
```docker-compose -f src/main/docker/sonar.yml up -d```

Luego de tener sonar corriendo en local ejecute el analisis de codigo con el siguiente comando:
#### Run Sonar Scanner
```./gradlew clean check jacocoTestReport sonarqube```

##Docker
El proyecto incorpora una imagen de postgresql para correr simulando el ambiente dev:  
#### Using Docker to simplify development (optional)
```
docker-compose -f src/main/docker/postgresql.yml up -d
```
Para detenerlo y quitar del contenedor, ejecute:
```
docker-compose -f src/main/docker/postgresql.yml down
```

Se puede dockerizar la aplicacion mediante una tarea de gradle basada en la libreria Jib de google, esto crea una imagen del proyecto, tambien es posible adicionar la configuracion de registry
#### Jib
```
./gradlew bootJar -Pprod jibDockerBuild
```
Si haz podido crear la imagen es posible ejecutar la app mediante docker-compose mediante el siguiente comando:
```
docker-compose -f src/main/docker/app.yml up -d
```
Mas informacion sobre el funcionamiento de Jib en la documentacion oficial - [Jib Authentication]

##Continuos Integration
Se creo un pipeline mediante github Actions, el cual compila, ejecuta pruebas y actualiza la insignia sobre porcentaje de covertura vinculado en el readme del proyecto en el siguiente enlace -
[Java CI]

### Entorno AWS:
La aplicación también ha sido desplegada en AWS de la siguiente manera:
* Postgresql mediante [ElephantSql] 
* mutant-ms desplegada en [Elastic Beanstalk](http://mutantms-env-1.eba-qwb2mtr6.us-east-1.elasticbeanstalk.com/)

[ElephantSql]: <https://www.elephantsql.com/>
[Jib Authentication]: <https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin#authentication-methods>
[Java CI]: <https://github.com/olsmca/mutant-ms/actions/workflows/ci.yml>
[Documento Prueba Tecnica]: <https://github.com/olsmca/mutant-ms/blob/main/ExamenMercadolibreMutantes.pdf>
[Base de datos H2]:<http://localhost:9090/h2-console/login.jsp>
[URL App Local]: <localhost:9090>