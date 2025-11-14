# Sistema de Gestão Universitária

**Aluno:** Marceu Filho<br>
**Matrícula:** 202051772191

---

## Sobre o Projeto

Sistema de gestão universitária desenvolvido em Spring Boot que gerencia cursos, alunos, professores, disciplinas, turmas e inscrições. O sistema implementa três padrões de projeto GoF (Gang of Four) para demonstrar boas práticas de desenvolvimento orientado a objetos.

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **SQLite** (banco de dados)
- **Lombok** (redução de código boilerplate)
- **Maven** (gerenciamento de dependências)

---

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

### Pacotes Principais

- **`entity/`** - Entidades do domínio (Aluno, Curso, Disciplina, Professor, Turma, Inscricao)
- **`dto/`** - Objetos de transferência de dados para a API
- **`dao/`** - Repositórios JPA para acesso ao banco de dados
- **`service/`** - Lógica de negócio e validações
- **`resource/`** - Controllers REST (endpoints da API)
- **`exception/`** - Exceções customizadas

### Pacotes de Padrões de Projeto

- **`strategy/`** - Implementação do padrão Strategy
- **`factory/`** - Implementação do padrão Factory Method
- **`template/`** - Implementação do padrão Template Method

---

## Padrões de Projeto GoF Implementados

### 1. Strategy Pattern (Comportamental)

**Localização:** `br.edu.ibmec.strategy`

**Propósito:** Cálculo de mensalidade com diferentes estratégias de cobrança

**Classes:**
- `CalculoMensalidadeStrategy` - Interface que define a estratégia
- `CalculoPadrao` - Cobrança de 100% do valor (padrão)
- `CalculoComDesconto` - Cobrança de 50% do valor (desconto)
- `CalculoBolsista` - Cobrança de 0% do valor (gratuito/bolsa)

**Uso:** O serviço `MensalidadeService` seleciona a estratégia apropriada baseada na categoria de cobrança do aluno (`categoriaCobranca`).

---

### 2. Factory Method Pattern (Criacional)

**Localização:** `br.edu.ibmec.factory`

**Propósito:** Criação de objetos Inscricao (matrículas de alunos em turmas)

**Classe:**
- `InscricaoFactory` - Factory responsável por criar objetos Inscricao e InscricaoId

**Métodos:**
- `criarInscricao(InscricaoDTO)` - Cria uma inscrição a partir de um DTO
- `criarInscricaoId(matricula, codigo)` - Cria a chave composta

**Benefícios:** Encapsula a lógica complexa de buscar entidades relacionadas (Aluno e Turma) e criar a chave composta, simplificando o código do serviço.

---

### 3. Template Method Pattern (Comportamental)

**Localização:** `br.edu.ibmec.template`

**Propósito:** Definir o esqueleto do algoritmo de registro de alunos, permitindo que subclasses alterem etapas específicas

**Classes:**
- `AlunoOperationTemplate` - Classe abstrata que define o template method
- `CadastrarAlunoOperation` - Implementação concreta para criação de alunos
- `AlterarAlunoOperation` - Implementação concreta para atualização de alunos

**Algoritmo (Template Method):**
1. Validar matrícula
2. Validar nome
3. Verificar pré-condições (varia entre criar/atualizar)
4. Buscar curso
5. Construir objeto Aluno
6. Salvar no banco
7. Executar pós-salvamento (varia entre criar/atualizar)

**Benefícios:** Elimina duplicação de código entre operações de criação e atualização, mantendo o algoritmo consistente.



## Observações

- Os padrões seguem as definições clássicas do livro "Design Patterns: Elements of Reusable Object-Oriented Software"
