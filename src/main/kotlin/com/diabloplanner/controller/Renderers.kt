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
    val items: HashMap<BuildSlot,Long?>,
    val gems: HashMap<GemSlot,Long?>,
    val skills: HashMap<SkillSlot,Long?>,
    val runes: HashMap<RuneSlot,String?>,
    val cubed: HashMap<CubeSlot,Long?>,
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
    HashMap(itemsEquipped.mapValues{it.value?.id}),
    HashMap(legendaryGems.mapValues{it.value?.id}),
    HashMap(skillsEquipped.mapValues{it.value?.id}),
    HashMap(runes),
    HashMap(itemsCubed.mapValues{it.value?.id}),
    id
)
}
