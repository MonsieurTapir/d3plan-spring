package com.diabloplanner

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.module.kotlin.*
import java.io.File
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

@Configuration
class BuildConfiguration {
   var jsonFilesFolder : String = "resources/data/"

    @Bean
    fun databaseInitializer(itemRepository: ItemRepository,
                            skillRepository: SkillRepository,
							buildRepository: BuildRepository) = ApplicationRunner {

        try{
            var omapper = jacksonObjectMapper()
            var resourceItem : Resource = ClassPathResource("items.json");
            var resourceSkills : Resource = ClassPathResource("skills.json");

            var items : List<Item> = omapper.readValue(resourceItem.inputStream)
            for( x in items){
                if(itemRepository.findByBlizzID(x.blizzID) == null){
                    itemRepository.save(x)
                }
            }
            var skills : List<Skill> = omapper.readValue(resourceSkills.inputStream)
            for( x in skills){
                if(skillRepository.findByUrl(x.url) == null){
                    skillRepository.save(x)
                }
            }
        }catch(e : Throwable) {
            println(e)
        }

        var cube1 : Item? = itemRepository.findByBlizzID("P4_Unique_HandXBow_001")
        var cube2 : Item? = itemRepository.findByBlizzID("P4_Unique_Chest_012")
        var cube3 : Item? = itemRepository.findByBlizzID("Unique_Ring_107_x1")
        var itemscubed: MutableMap<CubeSlot,Item?> = HashMap<CubeSlot,Item?>()
        itemscubed[CubeSlot.JEWELRY] = cube3
        itemscubed[CubeSlot.WEAPON] = cube1
        itemscubed[CubeSlot.ARMOR] = cube2
        
        var itemsEquipped: MutableMap<BuildSlot,Item?> = HashMap<BuildSlot,Item?>()

        itemsEquipped[BuildSlot.HELM] = itemRepository.findByBlizzID("Unique_Helm_Set_14_x1")
        itemsEquipped[BuildSlot.SHOULDERS] = itemRepository.findByBlizzID("blacksmith-recipe-aughilds-power")
        itemsEquipped[BuildSlot.GLOVES] = itemRepository.findByBlizzID("Unique_Gloves_Set_14_x1")
        itemsEquipped[BuildSlot.TORSO] = itemRepository.findByBlizzID("Unique_Chest_Set_14_x1")
        itemsEquipped[BuildSlot.BELT] = itemRepository.findByBlizzID("P4_Unique_Belt_01")
        itemsEquipped[BuildSlot.PANTS] = itemRepository.findByBlizzID("Unique_Pants_Set_14_x1")
        itemsEquipped[BuildSlot.BOOTS] = itemRepository.findByBlizzID("Unique_Boots_Set_14_x1")
        itemsEquipped[BuildSlot.BRACERS] = itemRepository.findByBlizzID("blacksmith-recipe-aughilds-search")
        itemsEquipped[BuildSlot.NECK] = itemRepository.findByBlizzID("Unique_Amulet_008_x1")
        itemsEquipped[BuildSlot.RING1] = itemRepository.findByBlizzID("Unique_Ring_013_x1")
        itemsEquipped[BuildSlot.RING2] = itemRepository.findByBlizzID("P2_Unique_Ring_04")
        itemsEquipped[BuildSlot.MAINHAND] = itemRepository.findByBlizzID("P61_Unique_Dagger_101_x1")
        itemsEquipped[BuildSlot.OFFHAND] = itemRepository.findByBlizzID("P5_Unique_Quiver_004_x1")
        

        var skillsEquipped: MutableMap<SkillSlot,Skill?> = HashMap<SkillSlot,Skill?>()
        skillsEquipped[SkillSlot.Q] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/active/vengeance")
        skillsEquipped[SkillSlot.W] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/active/fan-of-knives")
        skillsEquipped[SkillSlot.E] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/active/vault")
        skillsEquipped[SkillSlot.R] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/active/shadow-power")
        skillsEquipped[SkillSlot.LCLICK] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/active/impale")
        skillsEquipped[SkillSlot.RCLICK] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/active/companion")
        skillsEquipped[SkillSlot.PASSIVE1] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/passive/ambush")
        skillsEquipped[SkillSlot.PASSIVE2] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/passive/numbing-traps")
        skillsEquipped[SkillSlot.PASSIVE3] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/passive/cull-the-weak")
        skillsEquipped[SkillSlot.PASSIVE4] = skillRepository.findByUrl("https://eu.diablo3.com/en/class/demon-hunter/passive/awareness")

        var gems: MutableMap<GemSlot,Item?> = mutableMapOf(GemSlot.GEM1 to itemRepository.findByBlizzID("Unique_Gem_002_x1"),GemSlot.GEM2 to itemRepository.findByBlizzID("Unique_Gem_008_x1"), GemSlot.GEM3 to itemRepository.findByBlizzID("Unique_Gem_006_x1"))

        var runesEquipped: MutableMap<RuneSlot,String?> = HashMap<RuneSlot,String?>()
        runesEquipped[RuneSlot.Q]="Dark Heart"
        runesEquipped[RuneSlot.W]="Bladed Armor"
        runesEquipped[RuneSlot.E]="Tumble"
        runesEquipped[RuneSlot.R]="Gloom"
        runesEquipped[RuneSlot.LCLICK]="Overpenetration"
        runesEquipped[RuneSlot.RCLICK]="Wolf Companion"

        var build = Build("Impalerino","DH", "Brett",
        legendaryGems=gems,
        itemsEquipped=itemsEquipped,
        itemsCubed=itemscubed,
        skillsEquipped=skillsEquipped,
        runes=runesEquipped,
        description="Roll your way through the rift trying not to get one shot before you one shot elite packs")

        if(buildRepository.findBySlug(build.slug)==null)
            buildRepository.save(build)
        else{
            var build2 : Build = buildRepository.findBySlug(build.slug)!!
            build2.legendaryGems=gems
            build2.itemsEquipped=itemsEquipped
            build2.itemsCubed=itemscubed
            build2.runes=runesEquipped
            build2.skillsEquipped=skillsEquipped
            buildRepository.save(build2)
        }
    }
}