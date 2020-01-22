package com.diabloplanner

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
class BuildsController(private val repository: BuildRepository, 
                      private val itrepository: ItemRepository, 
                      private val skrepository: SkillRepository) {
                        
  @CrossOrigin
  @GetMapping("/")
  fun getit(model: Model): String {
    model["title"] = "Home"
    return "Home"
  }

  @CrossOrigin
  @GetMapping("/builds")
  fun allbuilds() : List<LightBuild> {
    return repository.findAllByOrderByAddedAtDesc().map { it.renderLight(ServletUriComponentsBuilder.fromCurrentRequest().toUriString()) 
    }
  }
  @CrossOrigin
  @GetMapping("/builds/new")
  fun newbuild(): RenderedBuild {
    val build = Build()
    build.init_empty()
    //repository.save(build)
    //var return_uri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString().replace("/builds/new", "/builds/"+build.id)

    return build.render("")
  }

  @CrossOrigin
  @DeleteMapping("/builds/{uuid}")
  fun delbuild(@PathVariable uuid: String): ResponseEntity<String> {
    val build = repository.findById(uuid)
    if(!build.isPresent())
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "This build does not exist")
    else
      repository.deleteById(uuid)
    return ResponseEntity<String>(uuid, HttpStatus.OK)
  }

  @CrossOrigin
  @PostMapping("/builds")
  fun createbuild(@RequestBody wrapped : BuildCreationRequestWrapper):  Map<String,String> {
    var klass : ClassName = enumValueOfOrNull<ClassName>(wrapped.classname)?:throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Class " + wrapped.classname + " unknown")
  
    val build = Build(
      name = wrapped.name,
      author = wrapped.author,
      description = wrapped.description,
      className = klass.name
    )
    build.init_empty()
    repository.save(build)
    return mapOf("url" to ServletUriComponentsBuilder.fromCurrentRequest().toUriString().trimEnd('/')+"/"+build.id,
                  "id" to build.id!!)
  }

  @CrossOrigin
  @PutMapping("/builds/{uuid}")
  fun updatebuild(@RequestBody build : Build, @PathVariable uuid: String): RenderedBuild{
    return build.render("")
  }

  @CrossOrigin
  @GetMapping("/builds/{uuid}")
  fun build(@PathVariable uuid: String): RenderedBuild {
    val build = repository.findById(uuid)
    if(!build.isPresent())
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "This build does not exist")
    return build.get().render(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
  }
}

data class BuildCreationRequestWrapper(
  var name : String,
  var author : String,
  var description : String,
  var classname : String
)
