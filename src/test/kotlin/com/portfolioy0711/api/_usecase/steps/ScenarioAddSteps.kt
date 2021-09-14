package com.portfolioy0711.api._usecase.steps

import com.portfolioy0711.api.ApiApplication
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import junit.framework.Assert.assertEquals
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
        classes = [ApiApplication::class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class ScenarioAddSteps: En {
    init {
        Given("아래와 같이 특정 장소가 등록되어 있음_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

        And("아래와 같이 특정 유저가 등록되어 있음_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

        Given("아래 장소에 대한 리뷰글이 존재하지 않음_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

        Given("유저가 아래와 같이 리뷰글을 작성함_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

        Then("유저의 리워드 레코드가 아래와 같이 생성됨_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

        And("유저의 포인트 총점이 아래와 같아짐_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

        And("유저의 리뷰 레코드가 아래와 같이 생성됨_1") { dataTable: DataTable ->
//            assertEquals(dataTable, "")
        }

    }

}
