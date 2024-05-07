### AWS SQS 를 간단하게 이용해보기

* 개선 포인트
    * Consumer 에서 스레드 수만큼 읽기 되는데 acknowledgementMode 설정이 아닌 messageVisibilitySeconds 설정이며 "1" 대신 좋은방법

* 참고자료
    * [Baeldung](https://www.baeldung.com/java-spring-cloud-aws-v3-message-acknowledgement)