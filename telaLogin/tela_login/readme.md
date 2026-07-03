<h1>Tela de login</h1>

<p>
Projeto com finalidade de estudo. 
<br> Implementa interfaces de login, com roles e CRUD (Criar, Ler, Atualizar e Deletar).
<br> Além das telas JSF, expõe uma API REST (Spring MVC) sobre a mesma base, com autenticação por token e permissões por role.
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
- Spring MVC (API REST)


<h2>Páginas implementadas:</h2>

- **Login**: autenticação por e-mail e senha; validação das credenciais cadastradas no banco de dados.
- **Criar usuário**: cadastro de novos usuários; validação para impedir e-mails duplicados.
- **Gestão de usuários**: implementa operações de CRUD; também permite pesquisar usuários pelo nome.
- **Gestão de pets**: implementa operações de CRUD; também permite pesquisar pelo nome; cada pet é associado a um usuário.

<h2>Roles:</h2>

- **ADMIN**: gerenciamento de todos os usuários e pets.
- **USER**: gerenciamento apenas de si mesmo e dos seus pets vinculados.

<h2>API REST:</h2>

<p>
Roda em paralelo às telas JSF, sob o path <code>/api/*</code>, sem interferir no FacesServlet.
<br> Autenticação por token (Bearer), gerado no login e validado em cada requisição protegida.
<br> As mesmas regras de role do JSF valem aqui: USER só acessa a si mesmo e seus pets; ADMIN acessa tudo.
</p>

- **POST /api/auth/login**: autentica e retorna um token.
- **POST /api/auth/logout**: invalida o token.
- **GET /api/auth/me**: retorna o usuário autenticado.
- **/api/usuario**: CRUD de usuários (cadastro é público; resto exige token).
- **/api/pet**: CRUD de pets (exige token; USER só vê/edita os próprios).

<p>
Coleção do Postman com os testes de autenticação e permissões disponível em casos-de-teste/api.json.
</p>


<h2>Estrutura:</h2>

```
src/main/java
├── controller     → Managed Beans (JSF) e Controllers REST (Spring)
├── model          → Entidades JPA
├── repository     → Camada de acesso aos dados
├── security       → Autenticação por token e controle de acesso (@Secured)
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

```
Cliente HTTP (Postman, etc)
        │
        ▼
DispatcherServlet (/api/*)
        │
        ▼
AuthInterceptor (@Secured)
        │
        ▼
Controllers REST
        │
        ▼
Repositories
        │
        ▼
JPA (EntityManager, transação manual)
        │
        ▼
MySQL
```