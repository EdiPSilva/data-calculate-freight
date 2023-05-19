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