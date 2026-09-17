# Encurtador de URL

Um serviço backend para encurtar URLs usando Spring Boot 4.1.0 com Java 17.

##  Sobre

Este projeto implementa um API REST para criar, gerenciar e redirecionar URLs encurtadas. Fornece funcionalidades de autenticação, persistência de dados e migrations automáticas.

*O projeto não está e não será finalizado, o intuito dele era aprendizagem, no momento em que eu aprendi e prátiquei o que queria, encerrei o desenvolvimento dele. 
Se você parar para analisar as funcionalidades do mesmo, vera que o projeto só conta com alguns testes essenciais já que não foi feito com TDD, mas não o suficiente, um post sem get e um armazenamento
de imagens local o que impede crescimento horizontal, entre outras coisas. Virou postagem do linkedin porque conta pro meu desenvolvimento!*


## ️ Stack Técnico

- **Framework**: Spring Boot 4.1.0
- **Linguagem**: Java 17
- **Build Tool**: Maven
- **Banco de Dados**: H2 (desenvolvimento/testes) e Mysql
- **ORM**: Spring Data JPA
- **Autenticação**: Spring Security
- **Migrations**: Flyway
- **Container**: Docker
- **Testes**: jUnit

## Como Começar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker (opcional, para usar Docker Compose)

### Instalação Local

1. Clone o repositório:
```bash
git clone https://github.com/Bruno-D-Fernandes/encutador-de-url.git
cd encurtaUrl
```

2. Configure as variáveis de ambiente (se necessário):
```bash
export DB_URL=jdbc:h2:mem:testdb
export DB_USER=sa
export DB_PASSWORD=
```

3. Compile e execute com Maven:
```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Usando Docker Compose

1. Certifique-se de estar no diretório raiz do projeto:
```bash
docker-compose up -d
```

2. Aguarde os serviços iniciarem (cerca de 10-15 segundos)

### H2 Console (Desenvolvimento)

Quando usando o perfil de desenvolvimento, acesse o console H2:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: (deixe em branco)

## Autenticação

O projeto utiliza **Spring Security** para autenticação e autorização. Consulte a documentação do código para detalhes dos endpoints protegidos.

##  Banco de Dados

- Migrations automáticas via **Flyway**
- Schema inicializado automaticamente na startup
- H2 para desenvolvimento


