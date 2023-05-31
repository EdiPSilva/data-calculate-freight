# Data Calculate Shipping
Este projeto é uma API RestFull que possui o objetivo de realizar o cadastramento dos parâmetros necessário para a realização do cálculo de frete.

|Técnologia|Versão            |
|----------|------------------|
|Java      |`17`              |
|Spring    |`2.7.10-SNAPSHOT` |
|Postgres  |`15.2-1.pgdg110+1`|

### Executar local
Para conseguir executar a aplicação no seu ambiente local, é necessário ter o Java 17 instalado e configurado e também o docker.

Assim que tudo configurado, acesse a pasta da raiz do projeto `database-docker` e execute o docker compose com o comando:
``` 
docker-compose up -d
```
Após o banco postgres subir corretamente configure a IDE de sua preferência para executar a classe principal.
```
src.main.java.br.com.java.datacalculatefreight.DataCalculateFreightApplication
```
A aplicação roda na porta `8080`, então para acessar a documentação do **Swagger** basta utilizar da url
```
http://localhost:8080/swagger-ui.html
```
### Para criar um container da aplicação
* Primeiro é necessário obter o ip do container do postgres
```
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container_id>
```
* Depois acesse o arquivo application.yml na pasta resources do projeto e atualize o host da url jdbc
```
url: jdbc:postgresql://<container_ip>:5432/data_calculate_freight
```
* Execute o clean e build da aplicação
* Crie a imagem da aplicação com base no arquivo Dockerfile
```
docker build -t dcf-img .
```
* Crie o containe da aplicação apontando a mesma rede que o container do postgres
``` 
docker run -p 8080:8080 --name=dcf-app --network=database-docker_postgres-compose-network dcf-img
```