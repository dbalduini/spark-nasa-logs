## spark-nasa-logs

Análise de logs de requests HTTP para o servidor WWW NASA Kennedy Space Center.

[Fonte](http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html)

# Datasets

* [NASA_access_log_Jul95](ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz)
* [NASA_access_log_Aug95](ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz)

# Especificação Técnica

- Java 8 [Link](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
- Scala 2.12.5 [Link](https://www.scala-lang.org/)
- Spark 2.3 [Link](https://www.apache.org/dyn/closer.lua/spark/spark-2.3.0/spark-2.3.0-bin-hadoop2.7.tgz)
- SBT 1.1.1 [Link](https://www.scala-sbt.org/)

# Compilando o código

```shell
sbt package
```

# Executando a aplicação

```shell
$SPARK_HOME/bin/spark-submit \
  --class "SimpleApp" \
  --master local[4] \
  target/scala-2.11/simple-project_2.11-1.0.jar
```

# Desenvolvedores

Executar o comando abaixo irá gerar os arquivos necessários para importar o projeto no Eclipse.

```shell
sbt eclipse
```
