package com.diabloplanner

data class LightBuild(
    val name: String,
    val className: String,
    val url: String,
    val author: String,
    val description: String,
    val addedAt: String
)
fun Build.renderLight(uribase: String) = LightBuild(name, className, uribase+"/"+id, author, description, addedAt.format())


data class RenderedBuild(
    val name: String,
    val className: String,
    val url: String,
    val author: String,
    val description: String,
    val addedAt: String,
    val items: Map<BuildSlot,Item?>,
    val gems: Map<GemSlot,Item?>,
    val skills: Map<SkillSlot,Skill?>,
    val runes: Map<RuneSlot,String?>,
    val cubed: Map<CubeSlot,Item?>
)
fun Build.render(uribase: String):RenderedBuild{
return RenderedBuild(
    name, 
    className, 
    uribase , 
    author, 
    description, 
    addedAt.format(), 
    itemsEquipped,
    legendaryGems,
    skillsEquipped,
    runes,
    itemsCubed
)
}
