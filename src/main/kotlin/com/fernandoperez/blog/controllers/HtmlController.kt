package com.fernandoperez.blog.controllers.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {

    @GetMapping("/")
    fun blog(model: Model): String {
        model["title"] = "Blog" // If we don't import org.springframework.ui.set we must write model.addAttribute("title", "Blog")
        return "blog"
    }
}
