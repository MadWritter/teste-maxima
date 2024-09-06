<h1>teste-máxima</h1>
Repositório de teste para a vaga de programador na máxima segurança

<h3>Recursos utilizados:</h3>
Wildfly 33.0.1 Final, JDK 17, Maven 3.8.7, e Docker 27.2.0
<br>
<h3>Requisitos:</h3>
JDK/OpenJDK 17, Docker e Maven
<br>
<h3>Como buildar:</h3>
1): Na raiz do projeto, onde se encontra o pom.xml e o docker-compose.yml, acesse o terminal e digite:<br>
<code>docker compose up</code> para subir o container com o servidor wildfly.
<br>
2): Em seguida, execute <code>mvn wildfly:deploy</code> para fazer o deploy do war para o container, 
o maven se encarrega de buidar e enviar para o container
<br>
<h3>Como acessar:</h3>
Acesse os recursos com <code>localhost:8080/api/{endpoint-requerido}</code>
