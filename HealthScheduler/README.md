# HealthScheduler API

Sistema de agendamento de consultas médicas desenvolvido com Spring Boot, oferecendo uma API RESTful completa para gerenciamento de médicos, pacientes e consultas.

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.3.3
- Spring Data JPA
- Spring HATEOAS
- PostgreSQL / H2 Database
- Maven
- Swagger/OpenAPI 3.0
- Lombok
- ModelMapper

## 📋 Pré-requisitos

- JDK 17 ou superior
- Maven 3.6+
- PostgreSQL (ou usar H2 em modo desenvolvimento)

## ⚙️ Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/KaykMurphy/HealthScheduler.git
cd HealthScheduler
```

### 2. Configure o banco de dados

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.application.name=HealthScheduler

# PostgreSQL (Produção)
spring.datasource.url=jdbc:postgresql://localhost:5432/healthscheduler
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

# H2 (Desenvolvimento) - Descomente para usar
# spring.datasource.url=jdbc:h2:mem:testdb
# spring.h2.console.enabled=true
```

### 3. Execute o projeto

```bash
./mvnw spring-boot:run
```

## 📚 Documentação da API

A documentação interativa da API está disponível via Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## 🔗 Endpoints Principais

### 👨‍⚕️ Doctors (Médicos)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/doctors` | Cadastrar novo médico |
| GET | `/api/v1/doctors` | Listar médicos ativos (paginado) |
| GET | `/api/v1/doctors/{id}` | Buscar médico por ID |
| GET | `/api/v1/doctors/specialization/{specialization}` | Buscar por especialização |
| PUT | `/api/v1/doctors/{id}` | Atualizar dados do médico |
| PUT | `/api/v1/doctors/{id}/activate` | Ativar médico |
| DELETE | `/api/v1/doctors/{id}/deactivate` | Desativar médico |

#### Exemplo de Cadastro de Médico

```json
POST /api/v1/doctors
{
  "name": "Dr. João Silva",
  "crm": "123456",
  "specialization": "CARDIOLOGY",
  "phone": "11999999999",
  "email": "joao.silva@hospital.com"
}
```

#### Especializações Disponíveis
- `CARDIOLOGY` - Cardiologia
- `DERMATOLOGY` - Dermatologia
- `PEDIATRICS` - Pediatria
- `ORTHOPEDICS` - Ortopedia
- `GYNECOLOGY` - Ginecologia
- `GENERAL_PRACTICE` - Clínico Geral

### 👥 Patients (Pacientes)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/patients` | Cadastrar novo paciente |
| GET | `/api/v1/patients` | Listar pacientes (paginado) |
| GET | `/api/v1/patients/{id}` | Buscar paciente por ID |
| PUT | `/api/v1/patients/{id}` | Atualizar dados do paciente |
| DELETE | `/api/v1/patients/{id}` | Deletar paciente |

#### Exemplo de Cadastro de Paciente

```json
POST /api/v1/patients
{
  "name": "Maria Santos",
  "cpf": "12345678901",
  "birthDate": "1990-05-15",
  "phone": "11988888888",
  "email": "maria.santos@email.com"
}
```

### 📅 Appointments (Consultas)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/appointments/doctor/{doctorId}` | Agendar consulta |
| GET | `/api/v1/appointments` | Listar todas consultas (paginado) |
| GET | `/api/v1/appointments/{id}` | Buscar consulta por ID |
| GET | `/api/v1/appointments/patient/{patientId}` | Consultas de um paciente |
| GET | `/api/v1/appointments/doctor/{doctorId}` | Consultas de um médico |
| GET | `/api/v1/appointments/status/{status}` | Consultas por status |
| PUT | `/api/v1/appointments/{id}/confirm` | Confirmar consulta |

#### Exemplo de Agendamento

```json
POST /api/v1/appointments/doctor/1
{
  "doctorId": 1,
  "patientId": 1,
  "appointmentDate": "2024-12-15T14:30:00",
  "durationMinutes": 30
}
```

#### Status de Consulta
- `SCHEDULED` - Agendada
- `CONFIRMED` - Confirmada
- `COMPLETED` - Concluída
- `CANCELLED` - Cancelada

#### Regras de Agendamento

- ✅ Consultas apenas em dias úteis (segunda a sexta)
- ✅ Horário de funcionamento: 08:00 às 18:00
- ✅ Duração: múltiplos de 15 minutos (15, 30, 45, 60, etc.)
- ✅ Mínimo: 15 minutos | Máximo: 120 minutos
- ✅ Não permite agendamentos em horários já ocupados

### 🕐 Doctor Schedules (Agenda do Médico)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| PUT | `/api/v1/doctors/{doctorId}/schedules/weekly` | Definir agenda semanal |
| GET | `/api/v1/doctors/{doctorId}/schedules/weekly` | Ver agenda semanal |

#### Exemplo de Configuração de Agenda

```json
PUT /api/v1/doctors/1/schedules/weekly
[
  {
    "dayOfWeek": "MONDAY",
    "startTime": "08:00:00",
    "endTime": "12:00:00"
  },
  {
    "dayOfWeek": "MONDAY",
    "startTime": "14:00:00",
    "endTime": "18:00:00"
  },
  {
    "dayOfWeek": "WEDNESDAY",
    "startTime": "09:00:00",
    "endTime": "17:00:00"
  }
]
```

## 🔄 HATEOAS

A API implementa HATEOAS, fornecendo links hipermídia nas respostas. Exemplo:

```json
{
  "id": 1,
  "name": "Dr. João Silva",
  "crm": "123456",
  "specialization": "CARDIOLOGY",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/doctors/1"
    },
    "doctors": {
      "href": "http://localhost:8080/api/v1/doctors"
    },
    "update": {
      "href": "http://localhost:8080/api/v1/doctors/1",
      "type": "PUT"
    }
  }
}
```

## 🛡️ Validações

### Doctor
- Nome: 3-100 caracteres
- CRM: 4-20 dígitos numéricos
- Telefone: 10-11 dígitos
- Email: formato válido

### Patient
- Nome: 3-100 caracteres
- CPF: 11 dígitos
- Data de nascimento: deve ser no passado
- Telefone: 10-11 dígitos
- Email: formato válido

### Appointment
- Data: deve ser futura
- Duração: 15-120 minutos (múltiplo de 15)
- Horário: 08:00-18:00
- Dia: segunda a sexta

## ⚠️ Tratamento de Erros

A API retorna respostas estruturadas para erros:

```json
{
  "timestamp": "2024-12-03T10:30:00Z",
  "status": 404,
  "error": "Médico não encontrado",
  "message": "Nenhum médico encontrado com o ID: 999",
  "path": "/api/v1/doctors/999"
}
```

### Códigos HTTP
- `200` - Sucesso
- `201` - Criado
- `204` - Sem conteúdo (sucesso em deleção)
- `400` - Requisição inválida
- `404` - Recurso não encontrado
- `409` - Conflito (ex: horário já ocupado)
- `500` - Erro interno do servidor

## 🧪 Testes

Execute os testes com:

```bash
./mvnw test
```

## 📦 Build

Gerar o JAR do projeto:

```bash
./mvnw clean package
```

O arquivo será gerado em `target/HealthScheduler-0.0.1-SNAPSHOT.jar`

## 🚀 Deploy

Execute o JAR gerado:

```bash
java -jar target/HealthScheduler-0.0.1-SNAPSHOT.jar
```

## 📝 Licença

Este projeto está sob a licença MIT.

## 👥 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📧 Contato

Kayk E.

Link do Projeto: [https://github.com/KaykMurphy/HealthScheduler](https://github.com/KaykMurphy/HealthScheduler)

---

⭐ Se este projeto te ajudou, considere dar uma estrela!
