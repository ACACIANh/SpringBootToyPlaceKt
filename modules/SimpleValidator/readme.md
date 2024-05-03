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
* HttpMessageNotReadableException 에러처리에 대한 메세지 정리