### 한것

* Validation 간단하게 확인
* Validation 에 대한 exception advice 적용
    * HttpMessageNotReadableException
    * MethodArgumentNotValidException
    * MethodArgumentTypeMismatchException
    * MissingServletRequestParameterException

* ConstraintViolationException 확인
    * 하위 유효성실패시 발생하는 Exception 이라고 하는데 확인할것
        * @RequestParam
        * @PathVariable
    * -> 확인결과 다른 요소가 있는듯함

### 할것

* ModelAttribute 와 RequestBody 에서의 사례들중 best practice 정리필요
    * ModelAttribute 에 대해서 BindException 으로 내려온다고 봤는데 검증필요
* HttpMessageNotReadableException 에러처리에 대한 메세지 정리

### 참고 링크

[Kotlin Spring bean validation nullability - stackoverflow](https://stackoverflow.com/questions/52444699/kotlin-spring-bean-validation-nullability)

[Kotlin, Spring Validation 이용한 입력 데이터 유효성 검증](https://beaniejoy.tistory.com/72)

[Java 에서 Kotlin, Validation 문제 해결기](https://velog.io/@carrykim/Java-%EC%97%90%EC%84%9C-Kotlin-Validation-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%EA%B8%B0)

[HttpMessageNotReadableException 참고](https://yeongchan1228.tistory.com/133)