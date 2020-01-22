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
  @PostMapping("/builds",consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
  fun createbuild(@RequestBody wrapped : BuildCreationRequestWrapper):  Map<String,String> {
    var klass : ClassName = enumValueOfOrNull<ClassName>(wrapped.classname)?:throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Class " + wrapped.classname + " unknown")
  
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
  @PutMapping("/builds/{uuid}",consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
  fun updatebuild(@RequestBody rbuild : BuildUpdateRequestWrapper, @PathVariable uuid: String): RenderedBuild{
    
    if(!repository.findById(uuid).isPresent())
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "Build "+uuid+" does not exist")
    var build : Build = toBuild(rbuild)
    build.id = uuid
    repository.save(build)
    return build.render(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
  }

  @CrossOrigin
  @GetMapping("/builds/{uuid}")
  fun build(@PathVariable uuid: String): RenderedBuild {
    val build = repository.findById(uuid)
    if(!build.isPresent())
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "Build "+uuid+" does not exist")
    return build.get().render(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
  }

  fun toBuild(b : BuildUpdateRequestWrapper) : Build{
    var build = Build(b.name,b.className,b.author,description = b.description)
    b.items.forEach{ k,v -> 
      if(v != null){
        build.equipItem(k, itrepository.findById(v).orNull()?:throw InvalidItemId("Item not found",v)) 
      }
    }
    b.gems.forEach{ k,v -> 
      if(v != null){
        build.equipGem(k, itrepository.findById(v).orNull()?:throw InvalidItemId("Item not found",v)) 
      }
    }
    b.cubed.forEach{ k,v -> 
      if(v != null){
        build.cubeItem(k, itrepository.findById(v).orNull()?:throw InvalidItemId("Item not found",v)) 
      }
    }
    b.skills.forEach{ k,v -> 
      if(v != null){
        build.equipSkill(k,  skrepository.findById(v).orNull()?:throw InvalidSkillId("Skill not found",v)) 
      }
    }
    b.runes.forEach{ k,v -> 
      if(v != null){
        build.equipRune(k, v) 
      }
    }
    return build
  }

}

data class BuildCreationRequestWrapper(
  var name : String,
  var author : String,
  var description : String,
  var classname : String
)
data class BuildUpdateRequestWrapper(
  val name: String,
  val className: String,
  val url: String,
  val author: String,
  val description: String,
  val items: HashMap<BuildSlot,Long?>,
  val gems: HashMap<GemSlot,Long?>,
  val skills: HashMap<SkillSlot,Long?>,
  val runes: HashMap<RuneSlot,String?>,
  val cubed: HashMap<CubeSlot,Long?>
)