[이전에 작업했던 exception 전략](https://github.com/ACACIANh/ExceptionStrategy)

* 추구하는 방향성 or 요구사항
    1. 도메인별 Exception 을 만들정도로 규모가 크지 않을때
    2. 메세지에 디테일한 옵션을 추가하여 주고싶을때 -> 이미 되어있었지만 Exception 이 많아지는 구조를 변경하고자함
    3. errorCode - interface, enum 을 구분해서 만들수 있게 네이밍 고려 ( enum 이 2번과 호환 )
    4. CustomException 이 errorCode interface 를 구현 및 enum 으로 위임하게 만들기 