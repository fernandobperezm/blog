package com.fernandoperez.blog

import com.fernandoperez.blog.models.Article
import com.fernandoperez.blog.models.User
import com.fernandoperez.blog.repositories.ArticleRepository
import com.fernandoperez.blog.repositories.UserRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
class HttpApiTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var articleRepository: ArticleRepository

    @MockBean
    private lateinit var markdownConverter: MarkdownConverter

    @Test
    fun `List articles`() {
        // Arrange
        val fernando = User("fernandobperezm", "fernando", "perez")
        val spring5Article = Article("Spring 5", "Dear", "Content", fernando, 1)
        val spring4Article = Article("Spring 4", "Dear", "Content", fernando, 2)

        // Mock calls
        whenever(articleRepository.findAllByOrderByAddedAtDesc()).thenReturn(listOf(spring5Article, spring4Article))
        whenever(markdownConverter.invoke(any())).thenAnswer { it.arguments[0] }

        // Test and Assert.
        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                .andExpect( status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].author.login").value(fernando.login))
                .andExpect(jsonPath("\$.[0].id").value(spring5Article.id!!))
                .andExpect(jsonPath("\$.[1].author.login").value(fernando.login))
                .andExpect(jsonPath("\$.[1].id").value(spring4Article.id!!))
    }

    @Test
    fun `List users`() {
        val fernando = User("fernandobperezm", "Fernando", "Perez")
        val fernando2 = User("fernandoperez", "Fernando", "Perez")

        // Mock calls
        whenever(userRepository.findAll()).thenReturn(listOf(fernando, fernando2))

        // Test and assert
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].login").value(fernando.login))
                .andExpect(jsonPath("\$.[1].login").value(fernando2.login))
    }
}