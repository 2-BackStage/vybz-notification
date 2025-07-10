# VYBZ Notification Service

VYBZ 플랫폼의 알림 기능을 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [아키텍처](#아키텍처)
-   [개발 가이드](#개발-가이드)

## 🎯 개요

VYBZ Notification Service는 다음과 같은 기능을 제공합니다:

-   **알림 관리**: 다양한 타입의 알림 생성, 조회, 읽음 처리, 삭제
-   **FCM 토큰 관리**: 사용자별 FCM 토큰 저장 및 관리
-   **푸시 알림**: Firebase Cloud Messaging을 통한 실시간 푸시 알림
-   **이벤트 기반 알림**: Kafka를 통한 비동기 이벤트 처리
-   **API 제공**: 알림 관련 REST API 제공
-   **서비스 디스커버리**: Eureka Client를 통한 서비스 등록
-   **API 문서화**: Swagger를 통한 API 문서 제공
-   **외부 서비스 연동**: Feign Client를 통한 다른 서비스와의 통신

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database

-   **MongoDB**: 알림 데이터 저장 (Read Model)
-   **MySQL**: 알림 데이터 저장 (Write Model)
-   **Redis**: 캐싱 및 세션 관리

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 처리 및 서비스 간 통신

### Push Notification

-   **Firebase Cloud Messaging (FCM)**: 실시간 푸시 알림 전송

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 알림 관리

-   **알림 생성**: 다양한 타입의 알림 생성 (좋아요, 댓글, 피드, 팔로우, 멘션, 시스템, 채팅, 후원)
-   **알림 조회**: 수신자별 알림 목록 조회 (커서 기반 페이징)
-   **알림 읽음 처리**: 개별 알림 읽음 상태 변경
-   **알림 삭제**: 개별 알림 삭제

### 2. FCM 토큰 관리

-   **토큰 저장/업데이트**: 사용자별 FCM 토큰 저장 및 업데이트
-   **토큰 조회**: 수신자 UUID로 FCM 토큰 조회
-   **푸시 알림 전송**: FCM을 통한 실시간 푸시 알림

### 3. 이벤트 기반 알림

-   **Kafka Consumer**: 다른 서비스의 이벤트 수신 및 알림 생성
-   **비동기 처리**: 이벤트 기반 비동기 알림 처리
-   **이벤트 타입**:
    -   ChatNotificationEvent (채팅 메시지 알림)
    -   FollowNotificationEvent (팔로우 알림)
    -   NotificationEvent (일반 알림)

### 4. 외부 서비스 연동

-   **Feign Client**: 다른 마이크로서비스와의 HTTP 통신
-   **User Info Service**: 사용자 정보 조회
-   **Busker Info Service**: 버스커 정보 조회

### 5. API 제공

-   **RESTful API**: 표준 REST API 제공
-   **응답 표준화**: 통일된 응답 형식 제공
-   **예외 처리**: 체계적인 예외 처리

### 6. 서비스 디스커버리

-   **Eureka Client**: 마이크로서비스 디스커버리에 등록
-   **서비스 등록**: 자동 서비스 등록 및 헬스체크

## 📁 프로젝트 구조

```
src/main/java/back/vybz/notification_service/
├── notification/               # 알림 도메인
│   ├── application/            # 알림 서비스 로직
│   │   ├── NotificationService.java
│   │   └── NotificationServiceImpl.java
│   ├── domain/                 # 알림 도메인 모델
│   │   ├── Notification.java
│   │   └── NotificationType.java
│   ├── dto/                    # 알림 DTO
│   │   ├── request/
│   │   │   └── RequestCreateNotificationDto.java
│   │   └── response/
│   │       └── ResponseNotificationDto.java
│   ├── infrastructure/         # 알림 리포지토리
│   │   ├── NotificationRepository.java
│   │   ├── NotificationRepositoryCustom.java
│   │   └── NotificationRepositoryCustomImpl.java
│   ├── presentation/           # 알림 컨트롤러
│   │   └── NotificationController.java
│   └── vo/                     # 알림 VO
│       ├── request/
│       │   └── RequestCreateNotificationVo.java
│       └── response/
│           └── ResponseNotificationVo.java
├── fcm/                        # FCM 도메인
│   ├── application/            # FCM 서비스 로직
│   │   ├── FcmService.java
│   │   └── FcmServiceImpl.java
│   ├── domain/                 # FCM 도메인 모델
│   │   └── FcmToken.java
│   ├── dto/                    # FCM DTO
│   │   ├── request/
│   │   │   └── RequestFcmTokenDto.java
│   │   └── response/
│   │       └── ResponseFcmTokenDto.java
│   ├── infrastructure/         # FCM 리포지토리
│   │   └── FcmTokenRepository.java
│   ├── presentation/           # FCM 컨트롤러
│   │   └── FcmTokenController.java
│   └── vo/                     # FCM VO
│       └── response/
│           └── ResponseFcmTokenVo.java
├── client/                     # 외부 서비스 클라이언트
│   ├── BuskerInfoClient.java
│   ├── UserInfoClient.java
│   └── dto/
│       └── UserSummary.java
├── common/                     # 공통 모듈
│   ├── config/                 # 설정 클래스들
│   │   ├── FirebaseConfig.java
│   │   ├── MongoConfig.java
│   │   └── SwaggerConfig.java
│   ├── entity/                 # 공통 엔티티
│   │   ├── BaseResponseEntity.java
│   │   └── BaseResponseStatus.java
│   ├── exception/              # 예외 처리
│   │   ├── AsyncExceptionHandler.java
│   │   ├── BaseException.java
│   │   ├── BaseExceptionHandler.java
│   │   └── BaseExceptionHandlerFilter.java
│   └── util/                   # 유틸리티
│       ├── CursorPageUtil.java
│       ├── FcmSenderUtil.java
│       ├── FcmUrlResolver.java
│       ├── MongoCursorPageHelper.java
│       └── NotificationContentFormatter.java
├── kafka/                      # Kafka 관련 모듈
│   ├── config/                 # Kafka 설정
│   │   ├── CommonKafkaConfig.java
│   │   └── NotificationKafkaConfig.java
│   ├── consumer/               # Kafka Consumer
│   │   ├── ChatNotificationEventConsumer.java
│   │   └── FollowNotificationEventConsumer.java
│   └── event/                  # Kafka 이벤트
│       ├── ChatNotificationEvent.java
│       ├── FollowNotificationEvent.java
│       └── NotificationEvent.java
└── NotificationServiceApplication.java
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/notification-service/swagger-ui.html`
-   **API 그룹**: Notification-Service, FCM-Service

### 주요 API 엔드포인트

#### 알림 관리 API

-   `POST /api/v1/notification` - 알림 생성
-   `GET /api/v1/notification/search` - 수신자별 알림 목록 조회
-   `PUT /api/v1/notification/{notificationId}` - 알림 읽음 처리
-   `DELETE /api/v1/notification/{notificationId}` - 알림 삭제

#### FCM 토큰 관리 API

-   `POST /api/v1/fcm-token` - FCM 토큰 저장/업데이트
-   `GET /api/v1/fcm-token/{receiverUuid}` - FCM 토큰 조회

### API 요청/응답 예시

#### 알림 생성 요청

```json
{
    "senderUuid": "user-123",
    "receiverUuid": "user-456",
    "notificationType": "FOLLOW",
    "content": "님이 회원님을 팔로우했습니다.",
    "targetId": "user-123"
}
```

#### 알림 목록 조회 응답

```json
{
    "status": "SUCCESS",
    "message": "요청이 성공적으로 처리되었습니다.",
    "data": {
        "content": [
            {
                "notificationId": "notif-123",
                "senderUuid": "user-123",
                "receiverUuid": "user-456",
                "notificationType": "FOLLOW",
                "content": "님이 회원님을 팔로우했습니다.",
                "targetId": "user-123",
                "read": false,
                "deleted": false,
                "createdAt": "2024-01-01T12:00:00Z"
            }
        ],
        "hasNext": true,
        "lastId": "last-cursor-id"
    }
}
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   MongoDB
-   MySQL
-   Redis
-   Apache Kafka
-   Firebase Admin SDK

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd vybz-notification-service

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-notification-service .

# Docker 컨테이너 실행
docker run -p 8000:8000 vybz-notification-service
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정
-   `application-dev.yml`: 개발 환경 설정
-   `application-db.yml`: 데이터베이스 설정
-   `firebase-adminsdk.json`: Firebase Admin SDK 설정

### 환경 변수

```yaml
# MongoDB 설정
spring:
  data:
    mongodb:
      uri: mongodb://${MONGO_HOST}:${MONGO_PORT}/${MONGO_DATABASE}

# MySQL 설정
spring:
  datasource:
    url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}
    username: ${MYSQL_USERNAME}
    password: ${MYSQL_PASSWORD}

# Redis 설정
spring:
  data:
    redis:
      host: ${REDIS_HOST}
      port: ${REDIS_PORT}
      password: ${REDIS_PASSWORD}

# Kafka 설정
spring:
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}

# Firebase 설정
firebase:
  config-path: classpath:firebase-adminsdk.json

# FCM 설정
fcm:
  base-url: ${FCM_BASE_URL}
```

## 🏗️ 아키텍처

### Hexagonal Architecture (Clean Architecture)

-   **Domain Layer**: 알림, FCM 도메인 모델과 비즈니스 로직
-   **Application Layer**: 알림, FCM 서비스 로직과 유스케이스
-   **Infrastructure Layer**: MongoDB/MySQL 접근과 외부 시스템 연동
-   **Presentation Layer**: REST API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Stateless**: 상태 없는 서비스 설계
-   **API Gateway**: 통합 API 게이트웨이 연동
-   **Feign Client**: 다른 서비스와의 HTTP 통신

### 이벤트 기반 아키텍처

-   **Kafka Consumer**: 다른 서비스의 이벤트 수신 및 알림 생성
-   **비동기 통신**: 서비스 간 느슨한 결합
-   **이벤트 타입**:
    -   ChatNotificationEvent (채팅 메시지 알림)
    -   FollowNotificationEvent (팔로우 알림)
    -   NotificationEvent (일반 알림)

### 데이터베이스 설계

-   **MongoDB**: 알림 데이터 Read Model 저장
-   **MySQL**: 알림 데이터 Write Model 저장
-   **Redis**: 캐싱 및 세션 관리
-   **Auditing**: 생성/수정 시간 자동 관리
-   **인덱스**: 성능 최적화를 위한 인덱스 설정

### 푸시 알림 아키텍처

-   **Firebase Cloud Messaging**: 실시간 푸시 알림 전송
-   **FCM 토큰 관리**: 사용자별 FCM 토큰 저장 및 관리
-   **알림 타입별 처리**: 다양한 알림 타입에 따른 차별화된 처리

### 공통 모듈

-   **BaseResponseEntity**: 통일된 응답 형식
-   **BaseException**: 체계적인 예외 처리
-   **SwaggerConfig**: API 문서화 설정
-   **MongoConfig**: MongoDB 설정
-   **FirebaseConfig**: Firebase 설정
-   **CursorPageUtil**: 커서 기반 페이징 유틸리티
-   **FcmSenderUtil**: FCM 전송 유틸리티
-   **NotificationContentFormatter**: 알림 내용 포맷팅 유틸리티

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### DTO/VO 패턴

-   **DTO**: 내부 서비스 간 데이터 전송
-   **VO**: 외부 API 요청/응답 데이터
-   **변환 메서드**: DTO ↔ VO 변환 메서드 제공

### Kafka 이벤트 처리

-   **Consumer**: 다른 서비스의 이벤트 수신 및 알림 생성
-   **이벤트 타입**: 다양한 알림 관련 이벤트
-   **비동기 처리**: 이벤트 기반 비동기 알림 처리
-   **Bulk 처리**: 대량 이벤트 처리 최적화

### 페이징 처리

-   **커서 기반 페이징**: 효율적인 대용량 데이터 처리
-   **CursorPageUtil**: 커서 기반 페이징 유틸리티
-   **MongoCursorPageHelper**: MongoDB 커서 페이징 헬퍼

### FCM 푸시 알림

-   **토큰 관리**: 사용자별 FCM 토큰 저장 및 업데이트
-   **알림 전송**: Firebase Cloud Messaging을 통한 실시간 푸시 알림
-   **알림 타입별 처리**: 다양한 알림 타입에 따른 차별화된 처리

### 외부 서비스 연동

-   **Feign Client**: 다른 마이크로서비스와의 HTTP 통신
-   **User Info Service**: 사용자 정보 조회
-   **Busker Info Service**: 버스커 정보 조회
-   **Bulk 조회**: 대량 데이터 조회 최적화

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 빌드

```bash
# Gradle 빌드
./gradlew clean build

# JAR 파일 생성
./gradlew bootJar
```

빌드된 JAR 파일은 `build/libs/` 디렉토리에 생성됩니다.

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Notification Service** - 실시간 알림 및 푸시 메시지 관리 서비스
