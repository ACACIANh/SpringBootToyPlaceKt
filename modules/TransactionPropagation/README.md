# 트랜잭션 전파 (Transaction Propagation)

이 모듈은 Spring의 트랜잭션 전파 동작을 테스트하기 위한 예제입니다.

## 주요 기능

- `Propagation.REQUIRES_NEW` 전략을 사용한 중첩 트랜잭션 테스트
- TransactionRollbackService 예외 처리(try-catch)가 트랜잭션 롤백에 미치는 영향 확인
- EntityManagerService 서로 다른 EntityManager 인스턴스 검증
