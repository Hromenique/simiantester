# Simian Tester

**Simian Tester** é uma aplicação (Webservice Rest) para verificar se DNAs pertencem ou não a símios. 
Atualmente ela se encontra disponível em: https://simian-tester.cfapps.io.

Possuindo dois endpoints:

#### POST /simian (https://simian-tester.cfapps.io/simian)
Usado para consultar se um DNA pertence a um símio. Recebe um body no formato json:
```
{
    "dna": ["ATGC", "ATGC", "ATGC", "ATGC"]
}
```
Respostas:

* **200 OK**: DNA pertence a um símio

* **403 FORBIDDEN**: DNA não pertence a símio (pertence a um humano)

* **404 BAD REQUEST**: Requisição em formato inválido

#### GET /stats (https://simian-tester.cfapps.io/stats)
Retorna as quantidades e proporções dos DNAs no formato:

```
{"count_mutant_dna": 40, "count_human_dna": 100, "ratio": 0.4}
```

## Execução da aplicação localmente

### Pré-requisitos

É necessário ter as seguintes ferramentas instaladas para executar a aplicação localmente:

* Git
* Maven
* Docker/Docker Compose

### Passos para excecução

#### Clonagem  do repositório

Clone o repositório com o Git:


via SSH: `git clone git@github.com:Hromenique/simiantester.git`


ou


via HTTP: `git clone https://github.com/Hromenique/simiantester.git`

Após clonado você verá que foi criado o diretório <b>simiantester</b> contendo os fontes do projeto. 

```
hromenique@hromenique-linux:~/sources$ ls
simiantester
```

**Importante**: não mude de branch durante o processo, a branch master contém a versão funcional e final do projeto.

#### Build do projeto Java

Faça o build do projeto para um `.jar` (fat jar) com o Maven. 

Primeiro acesse o diretório **simiantester**:

`cd simiantester`

Depois execute o build do projeto com o maven, usando o comando:

`mvn clean package -DskipTests`

**Observação**: A flag `-DskipTest` está sendo usada para suprimir a etapa de testes. Contudo você pode retirá-la e deixar que os testes ocorram normalmente.

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 9.798 s
[INFO] Finished at: 2019-03-04T21:08:06-03:00
[INFO] Final Memory: 40M/376M
[INFO] ------------------------------------------------------------------------
```
Após o build, a pasta **target** contedo o jar do projeto estará disponível na raiz do projeto.

```
hromenique@hromenique-linux:~/sources/simian_temp/simiantester$ ls
docker-compose.yml  Dockerfile  manifest.yml  pom.xml  src  target
```

#### Build de imagem do Docker e execução via Docker Compose

O projeto contém em sua raiz os arquivos `Dockerfile` e `docker-compose.yml` para a criação e execução local dos containers que compõem a aplicação:

Ainda dentro da pasta **simiantester**, faça o build da imagem Docker com:

`docker-compose build`

Após isso, a imagem Docker do projeto estará em seu repositório local de imagens:

```
hromenique@hromenique-linux:~/sources/simian_temp/simiantester$ docker images
REPOSITORY                                    TAG                 IMAGE ID            CREATED              SIZE
hromenique/simiantester                       latest              4a49f739a9e1       About a minute ago   750MB
```

Agora basta subir os containers que formam a aplicação, fazendo:

`docker-compose up`

Após todos esses passos, a aplicação simian-tester estará disponível em: `http://localhost:8080`








