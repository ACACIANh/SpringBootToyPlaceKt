# AnnotationTest Module

## 목적

이 모듈은 **Swagger를 사용하는 과정에서 발견된 어노테이션 우선순위 문제**를 Java/Kotlin 기본 생태계에서 검증하기 위해 만들어졌습니다.

### 배경

Swagger 사용 중 다음과 같은 상황이 발생했습니다:

- Interface에 정의된 어노테이션 값
- 구현체 클래스에서 오버라이드된 어노테이션 값

이때 **구현체 클래스의 어노테이션 값이 Interface의 값보다 우선**되는 현상을 확인했습니다.
이것이 Swagger 특유의 동작인지, 아니면 Java/Kotlin의 기본적인 어노테이션 처리 방식인지 확인이 필요했습니다.

# 결론 한줄 요약

재정의한 구현체의 어노테이션이 Interface 어노테이션보다 우선되며, 인터페이스의 어노테이션은 자동으로 상속되지 않음

## 테스트 구조

### 1. CustomAnnotation

```kotlin
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomAnnotation(
    val value: String = "",
    val priority: Int = 0,
    val enabled: Boolean = true
)
```

### 2. BaseInterface

- 클래스 레벨과 메서드 레벨에 `CustomAnnotation` 적용
- 기본값들이 정의됨

### 3. 구현체 클래스들

#### FirstClass

- BaseInterface를 구현
- **모든 어노테이션을 오버라이드**하여 다른 값 설정
- Interface 어노테이션과 구현체 어노테이션이 어떻게 처리되는지 확인

#### SecondClass

- BaseInterface를 구현
- **어노테이션을 전혀 사용하지 않음**
- Interface의 어노테이션이 자동으로 상속되는지 확인

## 주요 검증 사항

### 1. 어노테이션 오버라이드 동작

```kotlin
// Interface
@CustomAnnotation(value = "interface-myFunction1", priority = 1, enabled = true)
fun myFunction1()

// FirstClass 구현체
@CustomAnnotation(value = "firstclass-myFunction1", priority = 10, enabled = false)
override fun myFunction1()
```

**결과**: 구현체의 어노테이션 값이 사용됨 (Interface 값 무시)

### 2. 어노테이션 상속 여부

```kotlin
// SecondClass - 어노테이션 없음
override fun myFunction1()
```

**결과**: Interface의 어노테이션이 자동으로 상속되지 않음

## 테스트 방법

리플렉션을 사용하여 런타임에 어노테이션 값을 확인:

```kotlin
val interfaceMethod = BaseInterface::class.java.getDeclaredMethod("myFunction1")
val implMethod = FirstClass::class.java.getDeclaredMethod("myFunction1")

val interfaceAnnotation = interfaceMethod.getAnnotation(CustomAnnotation::class.java)
val implAnnotation = implMethod.getAnnotation(CustomAnnotation::class.java)
```

## 결론

이 모듈을 통해 다음을 확인할 수 있습니다:

1. **어노테이션 오버라이드**: Java/Kotlin에서 구현체의 어노테이션이 Interface 어노테이션보다 우선됨
2. **어노테이션 상속**: 메서드 어노테이션은 자동으로 상속되지 않으며, 명시적으로 선언해야 함
3. **Swagger 동작**: Swagger의 어노테이션 우선순위 처리는 Java/Kotlin의 기본 동작과 일치함

## 실행 방법

```bash
./gradlew :modules:AnnotationTest:test
```

각 테스트 케이스를 통해 어노테이션의 동작 방식을 명확히 확인할 수 있습니다.
