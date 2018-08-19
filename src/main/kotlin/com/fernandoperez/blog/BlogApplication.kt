package com.fernandoperez.blog

import com.fernandoperez.blog.models.Article
import com.fernandoperez.blog.models.User
import com.fernandoperez.blog.repositories.ArticleRepository
import com.fernandoperez.blog.repositories.UserRepository
import com.samskivert.mustache.Mustache
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import sun.tools.jar.CommandLine

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class BlogApplication{
  @Bean
  fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
          Mustache.compiler().escapeHTML(false).withLoader(loader)

  @Bean
  fun databaseInitializer(
          userRepository: UserRepository,
          articleRepository: ArticleRepository) = CommandLineRunner {
    val fernando = User("fernandobperezm", "Fernando", "Pérez")
    userRepository.save(fernando)

    articleRepository.save(Article(
      "Title",
      "Headline",
      "Content",
      fernando,
      1
    ))
    articleRepository.save(Article(
            "Title 2",
            "headline2",
            "Content 2",
            fernando,
            2
    ))
  }
}

fun main(args: Array<String>) {
  runApplication<BlogApplication>(*args)
}
