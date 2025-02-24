## 주문관리플랫폼 - I/O

## 프로젝트 소개
### 서비스 목표
- **사용자 친화적인 주문 시스템 구축**  
  - 주문부터 결제, 리뷰까지 원활한 사용자 경험을 제공하고자 합니다.
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

## 개발 환경 소개
프로젝트 개발을 위해 채택한 기술과 용도 및 선택 근거를 포함한 간략한 설명을 작성합니다.

| 분류           | 상세                                      |
|--------------|--------------------------------------|
| **IDE**       | IntelliJ                              |
| **Language**  | Java 17                              |
| **Framework** | Spring Boot 3.4.2                    |
| **Repository** | H2 In-memory, PostgreSQL 16.3       |
| **Build Tool** | Gradle 8.8                           |
| **DevOps - dev** | EC2, ECR, RDS(PostgreSQL), Docker, GitHub Actions, Nginx |


## 프로젝트 실행 방법
- [프로젝트 실행 방법](https://github.com/I-Oteam/order-management-platform/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8B%A4%ED%96%89-%EB%B0%A9%EB%B2%95)


## 설계 산출물
- [테이블 설계서](https://github.com/I-Oteam/order-management-platform/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EA%B3%84#%ED%85%8C%EC%9D%B4%EB%B8%94-%EB%AA%85%EC%84%B8%EC%84%9C)
- [ERD](https://github.com/I-Oteam/order-management-platform/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EA%B3%84#erd-%EC%84%A4%EA%B3%84%EB%8F%84)
- [API 명세서](https://github.com/I-Oteam/order-management-platform/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EA%B3%84#api-%EB%AA%85%EC%84%B8%EC%84%9C)
- [인프라 설계서](https://github.com/I-Oteam/order-management-platform/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EA%B3%84#%EC%9D%B8%ED%94%84%EB%9D%BC-%EC%84%A4%EA%B3%84%EC%84%9C)
- [Conventions : 우리 조의 개발 규칙](https://github.com/I-Oteam/order-management-platform/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EA%B3%84#-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%BB%A8%EB%B2%A4%EC%85%98)
- [GitHub Project URL](https://github.com/I-Oteam/order-management-platform)


## 개발 산출물

### 트러블 슈팅 : 우리 팀의 기술적 고민과 해결 과정
 1. [UserAuditorAware](https://github.com/I-Oteam/order-management-platform/wiki/UserAuditorAware)
 2. [Entity와 DTO](https://github.com/I-Oteam/order-management-platform/wiki/Entity%EC%99%80-DTO)
 3. [PageableArgumentResolver를 활용한 @Pageable 페이지 사이즈 제한](https://github.com/I-Oteam/order-management-platform/wiki/PageableArgumentResolver%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-@Pageable-%ED%8E%98%EC%9D%B4%EC%A7%80-%EC%82%AC%EC%9D%B4%EC%A6%88-%EC%A0%9C%ED%95%9C)
 
 ### 개발 측면에서 우리 팀이 잘한 점 
 1. [공통 예외처리 정책](https://github.com/I-Oteam/order-management-platform/wiki/%EA%B3%B5%ED%86%B5-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC-%EC%A0%95%EC%B1%85)
 2. [QueryDsl의 Projections 활용](https://github.com/I-Oteam/order-management-platform/wiki/QueryDsl%EC%9D%98-Projections-%ED%99%9C%EC%9A%A9)
 3. [WebClient와 RestTemplate 그리고 RestClient](https://github.com/I-Oteam/order-management-platform/wiki/WebClient%EC%99%80-RestTemplate-%EA%B7%B8%EB%A6%AC%EA%B3%A0-RestClient)

### 공통 관심 사항
1. [QueryDSL](https://github.com/I-Oteam/order-management-platform/wiki/QueryDSL)
2. [Logging 정책](https://github.com/I-Oteam/order-management-platform/wiki/Logging-%EC%A0%95%EC%B1%85)

### 설계 대비 API 구현률
- [API 구현율](https://github.com/I-Oteam/order-management-platform/wiki/%EC%84%A4%EA%B3%84-%EB%8C%80%EB%B9%84-API-%EA%B5%AC%ED%98%84%EC%9C%A8#%EC%84%A4%EA%B3%84-%EB%8C%80%EB%B9%84-api-%EA%B5%AC%ED%98%84%EC%9C%A8)

## 프로젝트 회고

### 시스템을 발전시키기 위해 더 해본다면?
- 테스트 코드를 추가해 시스템의 안정성과 신뢰성을 높이고, 다양한 예외 상황에 대한 처리 로직을 검증할 예정입니다.
- 가게와 리뷰 데이터가 많아지는 상황을 고려하여, 가게별 평점 평균 구현 로직을 배치를 활용하는 방법으로 개선 및 보완할 수 있을 것입니다.

### 협업 시 우리 팀이 잘한 점
- 문제나 궁금한 점을 바로 공유하고 함께 해결 방법을 고민하며 협업을 강화했습니다.
- 코드 리뷰와 토론을 통해 코드 품질을 지속적으로 개선하고 더 나은 방향을 모색했습니다.
- PR 규칙을 정하고 지식과 설계를 적극적으로 공유하여 팀의 개발 역량을 향상시켰습니다.

### 협업 시 아쉽거나 부족했던 부분들
- 트러블 슈팅 기록과 오류 정리를 제때 하지 않아 유사한 문제가 발생했을 때 원인 파악과 해결이 지연되었습니다.
- API 구현 및 학습 과정에서 시간이 예상보다 많이 소요되어 테스트 코드 작성이나 개발 진행이 지연되었습니다.

## 팀원 소개

| 김재현 | 류지윤 | 이예본 | 주연지 | 황하온 |
|--------|--------|--------|--------|--------|
| ![김재현](https://avatars.githubusercontent.com/u/94097685?v=4&size=150) | ![류지윤](https://avatars.githubusercontent.com/u/63836145?v=4&size=150) | ![이예본](https://avatars.githubusercontent.com/u/133661980?v=4&size=150) | ![주연지](https://avatars.githubusercontent.com/u/125468560?u=bed2a2c6cd8f101da322a1a4d76bd284648a5d00&v=4&size=150) | ![황하온](https://avatars.githubusercontent.com/u/62924471?u=3834f05ecd13e00470ac03f1474bbe1ecc452d37&v=4&size=150) |
| (가게, 카테고리) | (메뉴, AI 연동) | (사용자, 결제) | (주문) | (리뷰, 인프라, 주문 일부) |
| [GitHub 링크](https://github.com/iconew123) | [GitHub 링크](https://github.com/Ryujy) | [GitHub 링크](https://github.com/ybon1107) | [GitHub 링크](https://github.com/yeonzee) | [GitHub 링크](https://github.com/HanaHww2) |


