package com.fernandoperez.blog.controllers.web

import com.fernandoperez.blog.MarkdownConverter
import com.fernandoperez.blog.models.Article
import com.fernandoperez.blog.models.User
import com.fernandoperez.blog.repositories.ArticleRepository
import format
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class HtmlController(
        private val repository: ArticleRepository,
        private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog" // If we don't import org.springframework.ui.set we must write model.addAttribute("title", "Blog")
        model["articles"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "blog"
    }

    @GetMapping("/article/{id}")
    fun article(@PathVariable id: Long, model: Model): String {
        val article = repository
                .findById(id)
                .orElseThrow {IllegalArgumentException("Wrong article id provided") }
                .render()
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

    fun Article.render() = RenderedArticle(
            title,
            markdownConverter.invoke(headline),
            markdownConverter.invoke(content),
            author,
            id,
            addedAt.format()
    )

    data class RenderedArticle(
            val title: String,
            val headline: String,
            val content: String,
            val author: User,
            val id: Long?,
            val addedAt: String
    )
}
