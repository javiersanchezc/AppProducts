# AppProducts


docker run --name mysql-docker-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=mydatabase -d mysql

docker pull mysql:latest

Se ajusta para ejecutar app atravez de docker compose 

ejecutar el siguiente comando para generar el jar del projecto.

mvn clean install -DskipTests 

ejecutar el siguiente comando y validar que se generen los contenedores :

docker-compose up -d


![img_4.png](img_4.png)

![img_5.png](img_5.png)



Documentacion swagger

![img_2.png](img_2.png)


http://localhost:8080/swagger-ui/index.html




Diagrama de la base de datos.

![img.png](img.png)

collection de postmam 

![img_1.png](img_1.png)


ejecucion test cases

![img_3.png](img_3.png)

coverage de los test cases 

![img_6.png](img_6.png)

