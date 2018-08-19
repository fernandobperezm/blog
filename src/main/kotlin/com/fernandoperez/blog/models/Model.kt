package com.fernandoperez.blog.models

import java.time.LocalDateTime
import javax.persistence.*

// Data classes are designed to hold data and automatically provide equals(), hashCode(), toString(), componentN() and copy() functions.
@Entity
data class Article(
    val title: String,
    val headline: String,
    val content: String,
    @ManyToOne @JoinColumn val author: User,
    @Id @GeneratedValue val id: Long? = null,
    val addedAt: LocalDateTime = LocalDateTime.now()
)

@Entity
data class User(
    @Id val login: String,
    val firstname: String,
    val lastname: String,
    val description: String? = null
)
