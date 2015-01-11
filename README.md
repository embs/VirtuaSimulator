# VirtuaSimulator

Setup básico:
* Git;
* Java;
* Maven;

### Estrutura do Código

Três módulos:

1. Generator;
2. Graphicator;
3. Simulator;

#### Generator
Responsável pela geração de entradas para simulações (e.g.: redes substrato e
redes físicas).

#### Graphicator
Responsável pela criação de arquivos em formatos apropriados para visualização
gráfica (através de ferramentas como o LibreOffice, por exemplo) gerados a partir
da leitura dos arquivos de saída das simulações.

#### Simulator
Responsável pela execução das simulações de mapeamento de redes virtuais numa
rede infraestrutural física. Também é o modulo em que residem implementações de
abordagens para alocação de recursos em ambientes de virtualização de redes.

Obs.: cada módulo possui uma pasta `tasks` em que residem arquivos que podem
ser executados através do Maven. As tasks servem de interface para pesquisadores
e curiosos que querem utilizar os serviços providos pelo VirtuaSimulator.

### Executando as tasks
Com o maven:
```bash
$ mvn clean compile exec:java -Dexec.mainClass=generator.tasks.CreateVirtuaVNMPs
```
Esse comando:

1. Remove todos os arquivos previamente compilados, se existirem (`clean`);
2. Compila o projeto (`compile`);
3. Executa a classe passada como parâmetro através da flag `-Dexec.mainClass`;

### Rodando os testes
Com o maven:
```bash
$ mvn test
```
É possível executar os testes de uma única classe:
```bash
$ mvn test -Dtest=GraphTest.java
```
Em que `GraphTest.java` é o nome do arquivo em que se encontra a suíte de testes
a ser executada.

Obs.: os testes possuem muitas dependências de arquivos. Para que todos sejam
executados com sucesso (e sem falhas), é necessário fazer o setup de todo o sistema
de arquivos de acordo com as demandas de cada teste.

### Contribuindo

* Fork;
* Novo branch;
* Pull request;
