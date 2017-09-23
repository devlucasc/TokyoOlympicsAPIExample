# TokyoOlympicsAPIExample
Tokyo Olympics API Example - Spring boot + MySQL + Jetty

Esta API foi programada em Java com Spring boot, sua persistência é feita sobre banco de dados MySQL e utiliza do servidor embedded Jetty.

Esta API foi desenvolvida motivado pelo desafio de se desenvolver uma API Rest em um curto prazo de tempo, mantendo a qualidade do software e realizando todos os testes e validações necessárias.

Resolver desafios é o que me motiva, é o que me leva pra frente é o que me faz sentir vivo. Resolver desafios me ajuda a aprender novas coisas, novas tecnologias, me ajuda a desafiar a mim mesmo e muitas vezes conseguir fazer o impossível acontecer.

> **Eu sou parte de uma equipe. Então, quando venço, não sou eu apenas quem vence. De certa forma, termino o trabalho de um grupo enorme de pessoas.**
> -- Ayrton Senna

--------------

Inicialmente instalar o servidor MySQL na máquina.
Para instalação do MySQL no Ubuntu, utilizar:
```
    sudo apt update
    sudo apt install mysql-server
    sudo mysql_secure_installation
```

Necessário alterar a configuração do MySQL para não obrigar utilização de valor default para os campos do tipo timestamp.
Para Ubuntu alterar o arquivo: 
```
    sudo su
    cd /etc/mysql/mysql.conf.d
    vi mysqld.cnf
```
Adicionar a linha:
```sql-mode        = "NO_AUTO_VALUE_ON_ZERO"```
na sessão mysqld, ficando assim, por exemplo:
```
[mysqld]
port            = 3306
sql-mode        = "NO_AUTO_VALUE_ON_ZERO"
```

Após isso reiniciar o servidor MySQL:
```
    sudo su
    service mysql restart
```

Conectar ao servidor MySQL e executar o script init.sql do diretório scripts para criar o banco de dados e a tabela necessária.

Para compilar: ```mvn clean package```
Para executar: ```mvn spring-boot:run```
------------------
Exemplo de utilização da API:
Inserir nova competição:
```
curl -H "Content-Type: application/json" -X POST --data-binary @- http://127.0.0.1:8080/api/tournament/ <<EOF
{
    "modality": "futebol",
    "location": "tokyo",
    "startDate": "2017-09-22 18:00:00",
    "endDate": "2017-09-22 18:30:00",
    "country1": "Alemanha",
    "country2": "Brasil",
    "stage": "FINAL"
}
EOF
```

Listar todas as partidas:
```
curl -X GET http://127.0.0.1:8080/api/tournaments/ -s | jq
```

Listar todas as partidas de futebol:
```
curl -X GET http://127.0.0.1:8080/api/tournaments/futebol -s | jq
```
