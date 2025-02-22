## I'm SHARK - 죠습니다

---

## 프로젝트 소개
### 서비스 목표
- **사용자 친화적인 배달 시스템 구축**  
  - 주문부터 결제, 배달 추적까지 원활한 사용자 경험을 제공하고자 합니다.
  - 마이크로서비스로 확장 가능한 유연한 모놀리식 서비스 아키텍처 설계로 설계하고자 합니다.

### 기술적 목표
- **백엔드와 프론트엔드 간 역할 분리 원칙 준수**
  - 백엔드에서 처리해야 할 로직을 프론트엔드로 넘기지 않고, API 설계 시 책임 분리(Separation of Concerns)를 구현하고자 합니다.
  - 클라이언트와 서버 간 명확한 데이터 흐름을 유지하여 API의 일관성과 확장성을 보장하였습니다.

- **코드 재사용성과 유지보수성 향상**  
  - 여러 명의 백엔드 개발자가 협업할 때, **공통 유틸리티 및 서비스 계층을 적극 활용하여 중복 코드 최소화** 에 초점을 두고자 합니다.
  - **OOP 원칙(SOLID)과 DRY(Don’t Repeat Yourself) 원칙 준수** 를 기반으로 진행하고자 합니다.
  - 기능별로 **모듈화**하여 특정 메서드가 하나의 역할만 수행하도록 설계 (Single Responsibility Principle) 하였습니다.
  - **코드 컨벤션 및 패턴을 통일**하여 일관성 있는 코드베이스를 유지하였습니다.

---

## 개발 환경 소개
프로젝트 개발을 위해 채택한 기술과 용도 및 선택 근거를 포함한 간략한 설명을 작성합니다.

| 분류           | 상세                                      |
|--------------|--------------------------------------|
| **IDE**       | IntelliJ                              |
| **Language**  | Java 17                              |
| **Framework** | Spring Boot 3.4.2                    |
| **Repository** | H2 In-memory, PostgreSQL 16.3       |
| **Build Tool** | Gradle 8.8                           |
| **DevOps - dev** | EC2, ECR, ALB, ACM, RDS(PostgreSQL), Docker, GitHub Actions |

---

## 포팅 매뉴얼
- Local 환경에서 필요한 `.env` 양식
- Docker Compose 등 실행에 필요한 환경 설정 방법

---

## 설계 산출물
- 테이블 설계서
- ERD
- API 명세서
- 인프라 설계서
- Conventions : 우리 조의 개발 규칙
	- Commit Message Conventions
	- Java Code Style
	- Git-flow
	- Package Structure
- GitHub Project URL

---

## 개발 산출물

### 트러블 슈팅 : 우리 팀의 기술적 고민과 해결 과정
- **트러블 슈팅 #1**
- **트러블 슈팅 #2**
- **트러블 슈팅 #3**
- **트러블 슈팅 #4**
- **트러블 슈팅 #5**
### 공통 관심 사항
- **공통 관심 사항 #1**
- **공통 관심 사항 #2**
- **공통 관심 사항 #3**
### 테스트 코드
- 전체 작성된 테스트 코드 수 : 
- 계층별(Controller, Service, Repository, Domain) 테스트 코드 작성 전략
### 설계 대비 API 구현률
### 개발 측면에서 우리 팀이 잘한 점

---

## 프로젝트 회고

### 시스템을 발전시키기 위해 더 해본다면?

### 협업 시 우리 팀이 잘한 점

### 협업 시 아쉽거나 부족했던 부분들

---

## 팀원 소개
