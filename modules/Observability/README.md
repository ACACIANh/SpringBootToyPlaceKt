# Observability Stack

Prometheus, Loki, Tempo, Grafana를 Docker Compose로 실행하는 초간단 Observability 스택입니다

DockerCompose 로 손쉽게 구성할수 있게 세팅해봤습니다

## 구성 요소

- **Prometheus**: 메트릭 수집 및 저장 (포트: 9090)
- **Loki**: 로그 수집 및 저장 (포트: 3100)
- **Tempo**: 분산 추적 수집 및 저장 (포트: 3200, 4317, 4318, 9411)
- **Grafana**: 시각화 대시보드 (포트: 3000)

## 빠른 시작

### 1. 스택 실행

```bash
docker-compose up -d
```

### 2. 스택 중지

```bash
docker-compose down
```

### 3. 스택 중지 및 데이터 삭제

```bash
docker-compose down -v
```

## 접속 정보

### Grafana
- URL: http://localhost:3000
- Username: `admin`
- Password: `admin`

### Prometheus
- URL: http://localhost:9090

### Loki
- URL: http://localhost:3100

### Tempo
- HTTP URL: http://localhost:3200
- OTLP gRPC: localhost:4317
- OTLP HTTP: localhost:4318
- Zipkin: http://localhost:9411

## Spring Boot 애플리케이션 연동

### 1. Prometheus (Metrics)

`build.gradle.kts`에 의존성 추가:
```kotlin
implementation("org.springframework.boot:spring-boot-starter-actuator")
implementation("io.micrometer:micrometer-registry-prometheus")
```

`application.yml`:
```yaml
management:
  endpoints:
    web:
      exposure:
        include: prometheus,health,info
  metrics:
    export:
      prometheus:
        enabled: true
```

### 2. Loki (Logs)

`build.gradle.kts`에 의존성 추가:
```kotlin
implementation("com.github.loki4j:loki-logback-appender:1.4.1")
```

`logback-spring.xml` 설정:
```xml
<appender name="LOKI" class="com.github.loki4j.logback.Loki4jAppender">
    <http>
        <url>http://localhost:3100/loki/api/v1/push</url>
    </http>
    <format>
        <label>
            <pattern>app=spring-boot-app,host=${HOSTNAME},level=%level</pattern>
        </label>
        <message>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </message>
    </format>
</appender>
```

### 3. Tempo (Traces)

`build.gradle.kts`에 의존성 추가:
```kotlin
implementation("io.micrometer:micrometer-tracing-bridge-otel")
implementation("io.opentelemetry:opentelemetry-exporter-otlp")
```

`application.yml`:
```yaml
management:
  tracing:
    sampling:
      probability: 1.0
  otlp:
    tracing:
      endpoint: http://localhost:4318/v1/traces
```

## 디렉토리 구조

```
Observability/
├── docker-compose.yml
├── prometheus/
│   └── prometheus.yml
├── loki/
│   └── loki-config.yml
├── tempo/
│   └── tempo-config.yml
├── grafana/
│   └── datasources.yml
└── README.md
```

## 주요 기능

### 통합된 관측성
- Grafana에서 Prometheus, Loki, Tempo 모두 자동으로 연결됩니다
- 트레이스에서 로그로, 로그에서 트레이스로 쉽게 이동할 수 있습니다
- Exemplar를 통해 메트릭에서 트레이스로 점프할 수 있습니다

### 데이터 영속성
- 모든 데이터는 Docker Volume에 저장됩니다
- 컨테이너를 재시작해도 데이터가 유지됩니다

## 문제 해결

### Spring Boot 앱이 연결되지 않을 때

Prometheus 설정에서 `host.docker.internal`을 사용하고 있습니다.
- macOS/Windows: 기본적으로 동작합니다
- Linux: Docker 실행 시 `--add-host=host.docker.internal:host-gateway` 옵션이 필요할 수 있습니다

또는 `prometheus.yml`에서 타겟을 실제 IP 주소로 변경하세요.

### 로그 확인

```bash
docker-compose logs -f [service-name]
```

예시:
```bash
docker-compose logs -f grafana
```
