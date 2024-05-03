package com.example.simplevalidator

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Negative
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SimpleController {

    @GetMapping("/")
    fun index() = "Hello World!"

    @GetMapping("/ping")
    fun ping() = MyData("pong")

    @PostMapping("/valid")
    fun exceptionBody(@RequestBody @Valid request: ValidatedData) = "validation all pass"

    // * 로 표현된것 권장함
    //    @GetMapping("/valid/{pathNotNull}/{pathNull}")
    @GetMapping("/valid/{pathNotNull}")
    fun exceptionParamPath(
        @PathVariable pathNotNull: Long, // String 입력시 MethodArgumentTypeMismatchException 로 발생
//        @PathVariable(required = false) pathNull: Long?,  // not null 로 사용할것을 권장
        @RequestParam paramRequiredNotNullLong: Long,   // *MissingServletRequestParameterException 로 발생하며 권장함
//        @RequestParam paramRequiredNullLong: Long?, // required true 임에도 불구하고 valid x 다른방법 권장
//        @RequestParam(required = false) paramNotNullLong: Long,  // IllegalStateException 로 표현되어 사용x 다른방법 권장
        @RequestParam(required = false) paramNullLong: Long?, // *느슨한 valid 형태로 권장
        @RequestParam paramRequiredNotNullString: String, // *MissingServletRequestParameterException 로 발생하여 권장함
//        @RequestParam(required = false) paramNotNullString: String, // NullPointerException 로 표현되어 사용x 다른방법 권장
        @RequestParam(required = false) paramNullString: String?, // *느슨한 valid 형태로 권장
    ) = "validation all pass"

    @GetMapping("/valid-model")
    fun exceptionModelAttribute(
        @Valid @ModelAttribute data: ValidatedModelAttribute, // @ModelAttribute 생략가능
    ) = "validation all pass"
}

data class MyData(val name: String)

data class ValidatedData(
    val notNullString: String,
    @field:NotNull
    val nullString: String?,
    @field:NotBlank
    val blankString: String?,
    @field:Negative @field:NotNull
    val negative: Int?,
    @field:Email @field:NotNull // ValidatedModelAttribute 주석참고
    val email: String?,
)

data class ValidatedModelAttribute(
    val notNullString: String,  // error 메세지가 깔끔하지 않은데 개선방법 찾아보기
    @field:NotNull
    val nullString: String?,
    @field:NotBlank
    val blankString: String?,
    @field:Negative @field:NotNull
    val negative: Int?,
    @field:Email @field:NotEmpty    // email 과 notnull 을 같이쓰게 될경우 공백으로 올때 pass 하게된다 -> notEmpty 로 사용할것
    val email: String?,
)