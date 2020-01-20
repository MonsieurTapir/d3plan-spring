package com.diabloplanner

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {


  @BeforeAll
  fun setup() {
    println(">> Setup")
  }

  @Test
  fun `Assert home page title, content and status code`() {
    println(">> Assert home page title, content and status code")
    val entity = restTemplate.getForEntity<String>("/")
    assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(entity.body).contains("Home")
  }

  @Test
  fun `Assert build page title, content and status code`() {
    println(">> Assert build page title, content and status code")
    val entity = restTemplate.getForEntity<String>("/builds/")
    assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(entity.body).contains("[")
  }

  @AfterAll
  fun teardown() {
    println(">> Tear down")
  }
}