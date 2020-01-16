package com.diablo.planner

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
class SkillController(private val repository: SkillRepository){
  @PostMapping(path = arrayOf("/addskills"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
  fun addSkills(@RequestBody pBody: List<Skill>) {
    for(x : Skill in pBody){
      try{
        repository.save(x)
      }
      catch(e : DataIntegrityViolationException){
        println("Duplicate skill: " + x.name)
      }
    }
  }
}
@RestController
class ItemController(private val repository: ItemRepository){
  @PostMapping(path = arrayOf("/additems"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
  fun addItems(@RequestBody pBody: List<Item>) {
    for(x : Item in pBody){
      try{
        repository.save(x)
      }
      catch(e : DataIntegrityViolationException){
        println("Duplicate item: " + x.name)
      }
    }
  }
}

@RestController
class BuildController(private val repository: BuildRepository) {

  @GetMapping("/")
  fun getit(model: Model): String {
    model["title"] = "Home"
    return "Home"
  }

  @GetMapping("/builds")
  fun allbuilds() = repository.findAllByOrderByAddedAtDesc().map { it.renderLight(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()) }

  @GetMapping("/builds/{slug}")
  fun build(@PathVariable slug: String): RenderedBuild {
    val build = repository
        .findBySlug(slug)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This build does not exist")

    return build.render(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
  }

  fun Build.renderLight(uribase: String) = LightBuild(name, className, uribase+"/"+slug, author, description, addedAt.format())

  data class LightBuild(
    val name: String,
    val className: String,
    val url: String,
    val author: String,
    val description: String,
    val addedAt: String
  )
  fun Build.render(uribase: String):RenderedBuild{
    return RenderedBuild(
      name, 
      className, 
      uribase , 
      author, 
      description, 
      addedAt.format(), 
      listOf(helm,
            shoulders,
            gloves,
            boots,
            pants,
            belt,
            torso,
            bracers,
            neck,
            ringA,
            ringB,
            mainHand,
            offHand),
      listOf(skillA, skillB,skillC, skillD, skillE, skillF),
      listOf(runeA,runeB,runeC,runeD,runeE,runeF),
      listOf(cubeA, cubeB, cubeC)
    )
  }
  data class RenderedBuild(
    val name: String,
    val className: String,
    val url: String,
    val author: String,
    val description: String,
    val addedAt: String,
    val items: List<Item?>,
    val skills: List<Skill?>,
    val runes: List<String?>,
    val cubed: List<Item?>
  )
}
