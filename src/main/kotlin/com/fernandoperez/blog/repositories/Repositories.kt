package com.fernandoperez.blog.repositories

import com.fernandoperez.blog.models.Article
import com.fernandoperez.blog.models.User
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository : CrudRepository<User, String>
