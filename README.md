# SpringBootToyPlaceKt

Kotlin으로 구현된 Spring Boot 예제 및 유틸리티 모음입니다. 이 프로젝트는 다양한 Spring Boot 기능 및 통합을 위한 실험 공간으로 사용됩니다.

## 프로젝트 개요

이 저장소는 여러 독립적인 모듈을 포함하고 있으며, 각 모듈은 다양한 Spring Boot 기능, 통합 또는 패턴을 보여줍니다. Kotlin과 함께 Spring Boot를 학습하기 위한 참조 자료로 설계되었습니다.

## 모듈

프로젝트에는 다음과 같은 모듈이 포함되어 있습니다:

- **AwsS3**: 파일 저장 및 관리를 위한 Amazon S3 통합
- **AwsSqs**: 메시지 큐잉을 위한 Amazon SQS 통합
- **DistributedLock**: 분산 잠금 패턴 구현
- **ExceptionStrategy**: Spring Boot 애플리케이션에서 예외 처리 전략
- **Fcm**: 푸시 알림을 위한 Firebase Cloud Messaging 통합
- **InheritanceJpaJoined**: JPA 상속 조인 테이블 전략 시연
- **MemberService**: 회원/사용자 서비스 구현 예제
- **OpenCsv**: OpenCSV 라이브러리를 사용한 CSV 파일 처리
- **SimpleKotest**: Kotest 프레임워크를 사용한 테스트 예제
- **SimpleValidator**: 사용자 정의 유효성 검사 구현
- **TransactionPropagation**: Spring 트랜잭션 전파 동작 테스트

## 요구사항

- JDK 17 이상
- Kotlin 1.9.23 이상
- Gradle

## 프로젝트 빌드

전체 프로젝트 빌드:

```bash
./gradlew build
```

특정 모듈 빌드:

```bash
./gradlew :ModuleName:build
```

예시:

```bash
./gradlew :Fcm:build
```

## 모듈 실행

특정 모듈 실행:

```bash
./gradlew :ModuleName:bootRun
```

예시:

```bash
./gradlew :MemberService:bootRun
```

## 테스트

전체 프로젝트 테스트 실행:

```bash
./gradlew test
```

특정 모듈 테스트 실행:

```bash
./gradlew :ModuleName:test
```

## 기술 스택

- Spring Boot 3.2.4
- Kotlin 1.9.23
- JPA/Hibernate
- 다양한 AWS 서비스 (S3, SQS)
- Firebase Cloud Messaging
- Kotest (테스트용)

## 라이센스

이 프로젝트는 오픈 소스이며 [MIT License](LICENSE) 하에 사용 가능합니다.
