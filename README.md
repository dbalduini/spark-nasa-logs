## spark-nasa-logs

Análise de logs de requests HTTP para o servidor WWW NASA Kennedy Space Center.

[Fonte Original](http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html)

# Datasets

**NASA_access_log_Jul95**

Download: ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz

**NASA_access_log_Aug95**

Download: ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz

# Especificação Técnica

- Java 8 [Link](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
- Scala 2.12.5 [Link](https://www.scala-lang.org/)
- Spark 2.3 [Link](https://www.apache.org/dyn/closer.lua/spark/spark-2.3.0/spark-2.3.0-bin-hadoop2.7.tgz)
- SBT 1.1.1 [Link](https://www.scala-sbt.org/)

# Executando a aplicação

O script `spark_submit.sh` lança a aplicação localmente.

Deve-se passar como paramentro o número de cores para serem usados pelo cluster.

O exemplo abaixo irá rodar a aplicação em 4 cores.

```shell
./spark_submit.sh 4
```

# Desenvolvedores

Executar o comando abaixo irá gerar os arquivos necessários para importar o projeto no Eclipse.

```shell
sbt eclipse
```

## Gerando uma nova distribuição

```shell
sbt package
```
