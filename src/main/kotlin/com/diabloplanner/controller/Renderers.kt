package com.diabloplanner

data class LightBuild(
    val name: String,
    val className: String,
    val url: String,
    val author: String,
    val description: String,
    val addedAt: String
)
fun Build.renderLight(uribase: String) : LightBuild {
    var return_url = uribase.trimEnd('/')
    return LightBuild(name, className, return_url+"/"+id, author, description, addedAt.format())
} 


data class RenderedBuild(
    val name: String,
    val className: String,
    val url: String,
    val author: String,
    val description: String,
    val addedAt: String,
    val items: HashMap<BuildSlot,Item?>,
    val gems: HashMap<GemSlot,Item?>,
    val skills: HashMap<SkillSlot,Skill?>,
    val runes: HashMap<RuneSlot,String?>,
    val cubed: HashMap<CubeSlot,Item?>,
    val id: String? = ""
)
fun Build.render(uribase: String):RenderedBuild{
return RenderedBuild(
    name, 
    className, 
    uribase , 
    author, 
    description, 
    addedAt.format(), 
    HashMap(itemsEquipped),
    HashMap(legendaryGems),
    HashMap(skillsEquipped),
    HashMap(runes),
    HashMap(itemsCubed)
)
}
