package com.example.annotationtest

@CustomAnnotation(value = "interface-BaseInterface", priority = 100, enabled = true)
interface BaseInterface {

    @CustomAnnotation(value = "interface-myFunction1", priority = 1, enabled = true)
    fun myFunction1()

    @CustomAnnotation(value = "interface-myFunction2", priority = 2, enabled = false)
    fun myFunction2()
}

@CustomAnnotation(value = "class-FirstClass", priority = 200, enabled = false)
class FirstClass : BaseInterface {

    @CustomAnnotation(value = "firstclass-myFunction1", priority = 10, enabled = false)
    override fun myFunction1() {
        println("Implementation of myFunction1")
    }

    @CustomAnnotation(value = "firstclass-myFunction2", priority = 20, enabled = true)
    override fun myFunction2() {
        println("Implementation of myFunction2")
    }
}

class SecondClass : BaseInterface {

    override fun myFunction1() {
        println("SecondClass implementation of myFunction1")
    }

    override fun myFunction2() {
        println("SecondClass implementation of myFunction2")
    }
}

@CustomAnnotation(value = "class-ThirdClass", priority = 300, enabled = true)
open class ThirdClass

class FourthClass : ThirdClass()
