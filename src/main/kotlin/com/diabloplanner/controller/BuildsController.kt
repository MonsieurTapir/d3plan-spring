package com.diabloplanner

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.dao.DataIntegrityViolationException


@RestController
class BuildsController(private val repository: BuildRepository, 
                      private val itrepository: ItemRepository, 
                      private val skrepository: SkillRepository) {

  @GetMapping("/")
  fun getit(model: Model): String {
    model["title"] = "Home"
    return "Home"
  }

  @GetMapping("/builds")
  fun allbuilds() : List<LightBuild> {
    return repository.findAllByOrderByAddedAtDesc().map { it.renderLight(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()) 
    }
  }

  @GetMapping("/builds/{uuid}")
  fun build(@PathVariable uuid: String): RenderedBuild {
    val build = repository.findById(uuid)
    if(!build.isPresent())
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "This build does not exist")
    return build.get().render(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
  }
}
