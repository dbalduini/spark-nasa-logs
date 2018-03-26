## spark-nasa-logs

Análise de logs de requests HTTP para o servidor WWW NASA Kennedy Space Center.

[Fonte Original](http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html)

O arquivo `.jar` da aplicação está disponível para download neste [Link](https://github.com/dbalduini/spark-nasa-logs/raw/master/dist/spark-nasa-logs_2.11-1.0.jar). 

Este arquivo pode ser usado para lançar a app no Spark com o **spark-submit**. Para mais informações [clique aqui](https://spark.apache.org/docs/latest/submitting-applications.html#launching-applications-with-spark-submit).

# Datasets

É necessário fazer o download dos dois arquivos abaixo e move-los para a pasta raíz do projeto.

**NASA_access_log_Jul95**

Download: ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz

**NASA_access_log_Aug95**

Download: ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz

# Dependências

### Executar a aplicação

- Spark 2.3 [Link](https://www.apache.org/dyn/closer.lua/spark/spark-2.3.0/spark-2.3.0-bin-hadoop2.7.tgz)
- Java 8 [Link](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)

### Apenas desenvolvimento

- Scala 2.11 [Link](https://www.scala-lang.org/)
- SBT 1.1.1 [Link](https://www.scala-sbt.org/)

# Executando a aplicação

O script `spark_submit.sh` lança a aplicação localmente.

Deve-se passar como paramentro o número de cores (workers) para serem usados pelo cluster.

O exemplo abaixo irá rodar a aplicação em 4 cores.

```shell
./spark_submit.sh 4
```

*A variavél de ambiente `$SPARK_HOME` deve estar configurada corretamente para que o script funcione.*

# Desenvolvedores

Executar o comando abaixo irá gerar todos os arquivos necessários para importar o projeto no Eclipse.

```shell
sbt eclipse
```

### Gerando uma nova distribuição

```shell
sbt package
```
