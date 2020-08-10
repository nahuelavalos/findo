# Challenge Técnico Findo

El Challenge consiste en desarrollar una **API REST** con **Spring Boot** para que un **Colegio** pueda registrar a sus Alumnos y sus Cursos, procesar Inscripciones y obtener Estadísticas.

## API REST

- ### Alumno 🧏‍♂️

Devuelve **200-OK** si registra el alumno.\
Caso contrario devuelve **403-Forbidden**.\
*El campo **id** es opcional*.

**POST** --> [http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/alumno](http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/alumno)

>###### Request
>```sh
>{
>   "id": "1",
>   "nombre": "Nahuel",
>   "apellido": "Avalos",
>   "libreta": "84623847",
>   "fechaNacimiento": "1992-05-04"
>}
>```

>###### Response
>```sh
>200-OK
>```

- ### Curso 📚

Devuelve **200-OK** si registra el curso.\
Caso contrario devuelve **403-Forbidden**.\
*El campo **id** es opcional*.

**POST** --> [http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/inscripcion](http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/inscripcion)

>###### Request
>```sh
>{
>   "id":"1",
>   "nombre": "Matematica",
>   "fechaInicio": "2020-09-01",
>   "fechaFin": "2020-09-02",
>   "horasSemanales": "2"
>}
>```

>###### Response
>```sh
>200-Ok
>```

- ### Inscripción 📝

Devuelve **200-OK** si procesa la inscripción.\
Si no existe el alumno o el curso devuelve **404-NotFund**.\
Si excede las 20 horas semanales de cursada para el alumno devuelve **400-BadRequest**.\

**POST** --> [http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/curso](http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/curso)

>###### Request
>```sh
>{
>   "id":"1",
>   "idAlumno": "26",
>   "idCurso": "12"
>}
>```

>###### Response
>```sh
>200-Ok
>```

- ### Jovenes 🙋🏻🙋‍♂️🙋🏻🙋‍♂️

Devuelve los alumnos inscriptos al curso.\
Si el curso tiene menos alumnos que la cantidad ingresada, devuelve todos los alumnos inscriptos.\
Si no existe el curso o el curso no tiene alumnos inscriptos devuelve **404-NotFund**.


**GET** --> [http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/jovenes](http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/jovenes)

>###### Request
>```sh
>{
>   "curso":"31",
>   "cantidad":"2"
>}
>```

>###### Response
>```sh
>200-Ok
>```
>```sh
>{
>    {
>      "id": "4",
>      "nombre": "Victoria",
>      "apellido": "Gonzalez",
>      "libreta": "78939354",
>      "fechaNacimiento": "1998-02-17",
>      "edad": "22"
>    },
>    {
>      "id": "2",
>      "nombre": "Barbara",
>      "apellido": "Mubarak",
>      "libreta": "53453215",
>      "fechaNacimiento": "1995-11-12",
>      "edad": "24"
>    }
>}
>```

- ### Fecha 📆

Devuelve información sobre los cursos activos durante la fecha indicada.

**GET** --> [http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/fecha](http://ec2-18-230-57-17.sa-east-1.compute.amazonaws.com:8080/fecha)

>###### Request
>```sh
>{
>   "fecha":"2020-09-06"
>}
>```

>###### Response
>```sh
>200-Ok
>```
>```sh
>{
>   "horasSemanalesTotales": "26",
>   "cantidadDeAlumnos": "4",
>   "edadDeAlumnosPromedio": "37"
>}
>```

## Tecnología

- [Java 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- [Spring Boot](https://start.spring.io/)
- [Maven](https://maven.apache.org/)
- [IntelliJ IDEA](https://https://www.jetbrains.com/es-es/idea/)
- [Git](http://https://git-scm.com/)
- [AWS](https://aws.amazon.com/)
- [MongoDB](https://www.mongodb.com/es)
- [Postman](https://www.postman.com/downloads/)

## Environment

Comandos para configurar el ambiente en Unix

```sh
## Java
$ sudo apt-get install default-jre
$ java -version

## MondoDB
$ sudo apt install -y mongodb
$ sudo systemctl status mongodb
$ mongo --eval 'db.runCommand({ connectionStatus: 1 })'

## API Mutantes
$ git clone https://github.com/nahuelavalos/findo.git
$ cd findo/
$ mvn clean package
$ mvn spring-boot:run
```

## Run

Comandos para correr la API REST. 

Por defecto queda levantada en  [http://localhost/8080/](http://localhost/8080/)

```sh
$ java -jar colegio-1.0.0.jar
```

## Host

La API REST está hosteada en una Maquina Virtual (**Ubuntu**) que ofrece el servicio de Cloud Computing (**EC2**) de **Amazon Web Services**.


## Base de Datos

**MongoDB** (NoSQL) Local.

Por defecto queda levantada en **mongodb://localhost:27017**
