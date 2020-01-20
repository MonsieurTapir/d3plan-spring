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
  @GetMapping("/skills")
  fun allskills() = repository.findAll()
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
  @GetMapping("/items")
  fun allitems() = repository.findAll()

}