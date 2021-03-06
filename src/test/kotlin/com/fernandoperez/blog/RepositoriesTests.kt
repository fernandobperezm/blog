package com.fernandoperez.blog

import com.fernandoperez.blog.models.Article
import com.fernandoperez.blog.models.User
import com.fernandoperez.blog.repositories.ArticleRepository
import com.fernandoperez.blog.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class RepositoriesTests(@Autowired val entityManager: TestEntityManager,
                        @Autowired val userRepository: UserRepository,
                        @Autowired val articleRepository: ArticleRepository) {

    @Test
    fun `When findById then return Article`() {
        val fernando = User("fernandotest", "Fernando", "Pérez", "Description for fernando")
        entityManager.persist(fernando)
        val article = Article("Spring", "Fernando", "content", fernando)
        entityManager.persist(article)
        entityManager.flush()

        val found = articleRepository.findById(article.id!!)

        assertThat(found.get()).isEqualTo(article)
    }

    @Test
    fun `When findById then return User`() {
        val fernando = User("fernandotest", "Fernando", "Pérez", "Description for fernando")
        entityManager.persist(fernando)
        entityManager.flush()

        val found = userRepository.findById(fernando.login)

        assertThat(found.get()).isEqualTo(fernando)
    }
}
