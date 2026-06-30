
<h1>Tela de login</h1>

<p>
Projeto com finalidade de estudo. 
<br> Implementa interfaces de login, com roles e CRUD (Criar, Ler, Atualizar e Deletar).
</p>

<h2>Tecnologias utilizadas:</h2>

- Java
- Maven
- Tomcat 9
- MySQL
- JSF (JavaServer Faces)
- PrimeFaces
- CDI (Contexts and Dependency Injection)
- JPA (Java Persistence API)


<h2>Páginas implementadas:</h2>

- **Login**: autenticação por e-mail e senha; validação das credenciais cadastradas no banco de dados.
- **Criar usuário**: cadastro de novos usuários; validação para impedir e-mails duplicados.
- **Gestão de usuários**: implementa operações de CRUD; também permite pesquisar usuários pelo nome.
- **Gestão de pets**: implementa operações de CRUD; também permite pesquisar pelo nome; cada pet é associado a um usuário.

<h2>Roles:</h2>

- **ADMIN**: gerenciamento de todos os usuários e pets.
- **USER**: gerenciamento apenas de si mesmo e dos seus pets vinculados.


<h2>Estrutura:</h2>

```
src/main/java
├── controller     → Managed Beans responsáveis pela comunicação com as páginas JSF
├── model          → Entidades JPA
├── repository     → Camada de acesso aos dados
├── service        → Regras de negócio
└── util           → Classes utilitárias, transações e EntityManager

src/main/webapp
├── Login.xhtml
├── CriarUsuario.xhtml
├── GestaoUsuarios.xhtml
├── GestaoPets.xhtml
└── WEB-INF
    ├── Layout.xhtml
    ├── web.xml
    └── faces-config.xml
```


```
JSF / PrimeFaces
        │
        ▼
Managed Beans (Controller)
        │
        ▼
Services
        │
        ▼
Repositories
        │
        ▼
JPA (EntityManager)
        │
        ▼
MySQL
```


