# HealthScheduler API

[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Status](https://img.shields.io/badge/Status-In%20Development-yellow.svg)]()

## Índice

- [Visão Geral](#visão-geral)
- [Status do Projeto](#status-do-projeto)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Modelo de Dados](#modelo-de-dados)
- [Regras de Negócio](#regras-de-negócio)
- [Configuração e Execução](#configuração-e-execução)
- [Testes](#testes)
- [Roadmap](#roadmap)
- [Contribuição](#contribuição)

## Visão Geral

HealthScheduler é uma solução empresarial para gestão de agendamentos médicos, desenvolvida seguindo princípios de Clean Architecture e boas práticas do ecossistema Spring. O sistema oferece funcionalidades completas para gerenciamento de médicos, pacientes, horários de atendimento e agendamento de consultas.

### Principais Funcionalidades

- 📋 Gestão completa de médicos e especialidades
- 👥 Cadastro e gerenciamento de pacientes
- 📅 Sistema de agendamento com validações inteligentes
- ⏰ Configuração de horários de atendimento por médico
- 🔍 Consultas e filtros avançados
- ✅ Gerenciamento de status de consultas
- 🔐 Validações de regras de negócio em múltiplas camadas

## Status do Projeto

> ⚠️ **PROJETO EM DESENVOLVIMENTO ATIVO**
> 
> Este repositório contém uma aplicação em fase de implementação. Os componentes de infraestrutura estão parcialmente concluídos e a camada de apresentação está em desenvolvimento.

### Componentes Finalizados

| Componente | Status | Descrição |
|------------|--------|-----------|
| Domain Layer | ✅ Completo | Entidades, enums e regras de domínio |
| Data Layer | ✅ Completo | Repositories e configuração JPA |
| Service Layer | ✅ Completo | Lógica de negócio e validações |
| Configuration | ✅ Completo | ModelMapper e beans do Spring |
| Exception Handling | ✅ Completo | Exceções de negócio customizadas |

### Componentes em Desenvolvimento

| Componente | Status | Prioridade |
|------------|--------|-----------|
| REST Controllers | 🚧 Em progresso | Alta |
| HATEOAS Assemblers | 🚧 Em progresso | Alta |
| Unit Tests | 🚧 Parcial | Alta |
| Integration Tests | ⏳ Pendente | Média |
| Global Exception Handler | ⏳ Pendente | Alta |
| API Documentation | ⏳ Pendente | Média |
| Security Layer | ⏳ Pendente | Média |
| Docker Compose | ⏳ Pendente | Baixa |

## Arquitetura

### Estrutura do Projeto

```
com.example.HealthScheduler
├── config/                    # Configurações Spring
│   └── ModelMapperConfig
├── controller/               # REST Controllers (em desenvolvimento)
├── dto/                      # Data Transfer Objects
│   ├── appointment/
│   ├── doctor/
│   ├── patient/
│   └── schedule/
├── entity/                   # Entidades JPA
│   ├── Appointment
│   ├── Doctor
│   ├── DoctorSchedule
│   └── Patient
├── enums/                    # Enumerações
│   ├── AppointmentStatus
│   ├── DayOfWeek
│   └── Specialization
├── exception/               # Exceções customizadas
│   ├── BusinessException
│   ├── DoctorNotFoundException
│   └── PatientNotFoundException
├── repository/              # Interfaces JPA Repository
│   ├── AppointmentRepository
│   ├── DoctorRepository
│   ├── DoctorScheduleRepository
│   └── PatientRepository
└── service/                 # Camada de serviço
    ├── AppointmentService
    ├── DoctorScheduleService
    ├── DoctorService
    └── PatientService
```

### Padrões Arquiteturais

- **Layered Architecture**: Separação clara entre camadas de apresentação, negócio e persistência
- **DTO Pattern**: Isolamento entre entidades de domínio e contratos de API
- **Repository Pattern**: Abstração da camada de acesso a dados
- **Service Layer**: Encapsulamento da lógica de negócio
- **HATEOAS**: Hipermídia para navegabilidade da API (em implementação)

## Tecnologias

### Core Framework

| Tecnologia | Versão | Propósito |
|-----------|--------|-----------|
| Java | 17 | Linguagem base |
| Spring Boot | 3.x | Framework principal |
| Maven | 4.0.0 | Build e gerenciamento de dependências |

### Spring Ecosystem

```xml
<!-- Web & REST -->
spring-boot-starter-web
spring-boot-starter-hateoas

<!-- Persistence -->
spring-boot-starter-data-jpa

<!-- Validation -->
spring-boot-starter-validation

<!-- Testing -->
spring-boot-starter-test
mockito-core
```

### Libraries & Tools

- **Lombok 1.18.32**: Redução de boilerplate
- **ModelMapper 3.2.6**: Mapeamento objeto-objeto
- **SpringDoc OpenAPI 2.3.0**: Documentação automática da API
- **H2 Database**: Banco em memória para testes
- **PostgreSQL**: Banco de dados para produção

## Modelo de Dados

### Diagrama de Entidades (Simplificado)

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│   Doctor    │────────<│ Appointment  │>────────│   Patient   │
└─────────────┘         └──────────────┘         └─────────────┘
      │                        │
      │                        │
      │                        │
      ▼                        ▼
┌─────────────────┐    ┌──────────────┐
│ DoctorSchedule  │    │    Status    │
└─────────────────┘    └──────────────┘
```

### Entidades Principais

#### Doctor
```java
- id: Long (PK)
- name: String (100)
- crm: String (100, unique)
- specialization: Enum
- phone: String (20)
- email: String (100, unique)
- active: Boolean
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
```

#### Patient
```java
- id: Long (PK)
- name: String (100)
- cpf: String (11, unique)
- birthDate: LocalDate
- phone: String (20, unique)
- email: String (100, unique)
- address: Embedded
- createdAt: LocalDateTime
```

#### Appointment
```java
- id: Long (PK)
- doctor: Doctor (FK)
- patient: Patient (FK)
- appointmentDate: LocalDateTime
- startTime: LocalDateTime
- endTime: LocalDateTime
- durationMinutes: Integer (default: 30)
- status: Enum
- cancellationReason: Text
- version: Integer (optimistic locking)
```

#### DoctorSchedule
```java
- id: Long (PK)
- doctor: Doctor (FK)
- dayOfWeek: Enum
- startTime: LocalTime
- endTime: LocalTime
- unique(doctor_id, day_of_week)
```

### Enumerações

**Specialization**
- CARDIOLOGY, DERMATOLOGY, PEDIATRICS, ORTHOPEDICS, GYNECOLOGY, GENERAL_PRACTICE

**AppointmentStatus**
- SCHEDULED, CONFIRMED, COMPLETED, CANCELLED

**DayOfWeek**
- MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY

## Regras de Negócio

### Gestão de Médicos

1. **Registro**
   - CRM deve ser único no sistema
   - Médico é criado com status ativo por padrão
   - Email opcional, mas deve ser único se fornecido

2. **Desativação**
   - Não é permitido desativar médico com consultas futuras agendadas
   - Sistema valida existência de appointments posteriores à data atual

3. **Atualização**
   - CRM e especialização são imutáveis após registro
   - Apenas nome, email e telefone podem ser alterados

### Gestão de Pacientes

1. **Registro**
   - CPF deve ser único (validação de formato pendente)
   - Email e telefone devem ser únicos quando fornecidos
   - Endereço completo opcional

2. **Validações de Unicidade**
   - CPF: obrigatório e único
   - Email: opcional e único
   - Telefone: opcional e único

### Sistema de Agendamentos

1. **Criação de Consulta**
   - Médico deve estar ativo no sistema
   - Horário deve respeitar horário comercial (até 18:00)
   - Duração padrão: 30 minutos (configurável)
   - Status inicial: SCHEDULED

2. **Validações de Horário**
   - Consulta não pode terminar após 18:00
   - Sistema calcula automaticamente endTime baseado em startTime + durationMinutes

3. **Confirmação de Consulta**
   - Apenas consultas com status SCHEDULED podem ser confirmadas
   - Não é possível confirmar consultas passadas
   - Transição: SCHEDULED → CONFIRMED

4. **Controle de Concorrência**
   - Versionamento otimista implementado (@Version)
   - Previne double-booking em cenários concorrentes

### Horários de Atendimento

1. **Configuração Semanal**
   - Um horário por dia da semana por médico
   - Restrição única em (doctor_id, day_of_week)
   - StartTime deve ser anterior a endTime

2. **Atualização de Horários**
   - Substituição completa: todos os horários anteriores são removidos
   - Operação transacional

## Configuração e Execução

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- PostgreSQL 12+ (para ambiente de produção)

### Configuração do Banco de Dados

#### H2 (Desenvolvimento/Testes)
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:healthscheduler
    driver-class-name: org.h2.Driver
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
```

#### PostgreSQL (Produção)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/healthscheduler
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
```

### Compilação e Execução

```bash
# Clone o repositório
git clone <repository-url>
cd HealthScheduler

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run

# Execute com perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Build para Produção

```bash
# Gera o JAR executável
mvn clean package -DskipTests

# Executa o JAR
java -jar target/HealthScheduler-0.0.1-SNAPSHOT.jar
```

## Testes

### Estrutura de Testes

```
src/test/java
└── com.example.HealthScheduler
    ├── service/
    │   └── DoctorServiceTest    # Testes unitários (parcial)
    ├── controller/              # Testes de controller (pendente)
    └── integration/             # Testes de integração (pendente)
```

### Testes Implementados

#### DoctorServiceTest

**Cenários Cobertos:**
- ✅ Validação de duplicidade de CRM
- ✅ Registro bem-sucedido de médico

**Técnicas Utilizadas:**
- Mockito para mocking de dependências
- JUnit 5 para estrutura de testes
- ModelMapper real (não mockado) para garantir integridade

**Exemplo:**
```java
@Test
void shouldThrowExceptionWhenCRMExists() {
    var dto = new DoctorRegistrationDTO(
        "Elon", "213451", Specialization.CARDIOLOGY, 
        "2131111", "email@test.com"
    );
    
    when(doctorRepository.existsByCrm("213451"))
        .thenReturn(true);
    
    assertThrows(BusinessException.class, 
        () -> doctorService.register(dto));
    
    verify(doctorRepository, times(1))
        .existsByCrm("213451");
}
```

### Executando os Testes

```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=DoctorServiceTest

# Com relatório de cobertura
mvn test jacoco:report
```

### Cobertura de Testes (Meta)

| Camada | Meta | Atual |
|--------|------|-------|
| Services | 80% | ~5% |
| Controllers | 70% | 0% |
| Repositories | 60% | 0% |
| Integration | 60% | 0% |

## Roadmap

### Fase 1: API Layer (Em Andamento)

- [ ] Implementar DoctorController com endpoints CRUD
- [ ] Implementar PatientController com endpoints CRUD
- [ ] Implementar AppointmentController
- [ ] Implementar DoctorScheduleController
- [ ] Configurar Global Exception Handler (@ControllerAdvice)

### Fase 2: HATEOAS & Documentation

- [ ] Criar DoctorModelAssembler
- [ ] Criar PatientModelAssembler
- [ ] Criar AppointmentModelAssembler
- [ ] Configurar SpringDoc OpenAPI
- [ ] Gerar documentação interativa (Swagger UI)

### Fase 3: Testing

- [ ] Completar testes unitários de todos os Services
- [ ] Implementar testes de Controllers (@WebMvcTest)
- [ ] Implementar testes de integração (@SpringBootTest)
- [ ] Configurar Jacoco para relatórios de cobertura
- [ ] Atingir 80%+ de cobertura nos Services

## Estrutura de Branches

```
main
├── develop (branch de desenvolvimento ativa)
├── feature/controllers
├── feature/hateoas
├── feature/tests
└── feature/security
```

## Contribuição

### Workflow

1. Fork o projeto
2. Crie uma feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Convenções de Código

- Seguir Google Java Style Guide
- Usar Lombok para reduzir boilerplate
- DTOs imutáveis (records)
- Services transacionais (@Transactional)
- Métodos de repository descritivos
- Testes para toda nova funcionalidade

### Commit Messages

Seguir Conventional Commits:
```
feat: add appointment cancellation endpoint
fix: resolve double booking issue
test: add unit tests for PatientService
docs: update API documentation
refactor: simplify appointment validation logic
```

## Contato e Suporte

- **Autor**: [Kayk E.]

## Licença

Este projeto está sob a licença [MIT/Apache/Outra] - veja o arquivo LICENSE para detalhes.

---

**Nota**: Esta documentação reflete o estado atual do projeto em desenvolvimento. Informações podem estar sujeitas a alterações conforme a implementação progride.
