package com.example.annotationtest

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.*

@SpringBootTest
class AnnotationTestApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun `인터페이스와 재정의한 구현체의 어노테이션 값이 다른지 확인`() {
        // given
        val interfaceMethod = BaseInterface::class.java.getDeclaredMethod("myFunction1")
        val firstClassMethod = FirstClass::class.java.getDeclaredMethod("myFunction1")

        val interfaceAnnotation = interfaceMethod.getAnnotation(CustomAnnotation::class.java)
        val firstClassAnnotation = firstClassMethod.getAnnotation(CustomAnnotation::class.java)

        // then
        assertEquals("interface-myFunction1", interfaceAnnotation.value)
        assertEquals("firstclass-myFunction1", firstClassAnnotation.value)
        assertNotEquals(interfaceAnnotation.value, firstClassAnnotation.value)
    }

    @Test
    fun `SecondClass는 Interface 의 Annotation 값을 가져올수 있다?`() {
        // given
        val interfaceMethod = BaseInterface::class.java.getDeclaredMethod("myFunction1")
        val secondClassMethod = SecondClass::class.java.getDeclaredMethod("myFunction1")

        val interfaceAnnotation = interfaceMethod.getAnnotation(CustomAnnotation::class.java)
        val secondClassAnnotation = secondClassMethod.getAnnotation(CustomAnnotation::class.java)

        // then
        assertNotNull(interfaceAnnotation)
        assertNull(secondClassAnnotation)
        assertEquals("interface-myFunction1", interfaceAnnotation.value)
    }

    @Test
    fun `@Inherited가 추가된 클래스 레벨 어노테이션 상속 확인`() {
        // given
        val firstClassAnnotation = FirstClass::class.java.getAnnotation(CustomAnnotation::class.java)
        val secondClassAnnotation = SecondClass::class.java.getAnnotation(CustomAnnotation::class.java)
        val thirdClassAnnotation = ThirdClass::class.java.getAnnotation(CustomAnnotation::class.java)
        val fourthClassAnnotation = FourthClass::class.java.getAnnotation(CustomAnnotation::class.java)

        // then
        assertNotNull(firstClassAnnotation)
        assertNull(secondClassAnnotation) // SecondClass는 BaseInterface를 implements하므로 클래스 어노테이션 상속되지 않음
        assertNotNull(thirdClassAnnotation)
        assertNotNull(fourthClassAnnotation) // @Inherited 덕분에 상속받음

        assertEquals("class-FirstClass", firstClassAnnotation.value)
        assertEquals(200, firstClassAnnotation.priority)
        assertFalse(firstClassAnnotation.enabled)

        assertEquals("class-ThirdClass", thirdClassAnnotation.value)
        assertEquals("class-ThirdClass", fourthClassAnnotation.value) // 부모 클래스의 어노테이션 값 상속
        assertEquals(300, fourthClassAnnotation.priority)
        assertTrue(fourthClassAnnotation.enabled)
    }
}
