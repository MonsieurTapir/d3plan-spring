package com.diablo.planner

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BuildConfiguration {

    @Bean
    fun databaseInitializer(itemRepository: ItemRepository,
                            skillRepository: SkillRepository,
							buildRepository: BuildRepository) = ApplicationRunner {

        try{
            var cube1 : Item = itemRepository.save(Item("Dawn","http://us.diablo3.com/en/item/dawn-P4_Unique_HandXBow_001", ItemSlot.THWEAPON,"P4_Unique_HandXBow_001"))
            var cube2 : Item = itemRepository.save(Item("Aquila Cuirasse","http://us.diablo3.com/en/item/aquila-cuirass-P4_Unique_Chest_012", ItemSlot.TORSO,"P4_Unique_Chest_012"))
            var cube3 : Item = itemRepository.save(Item("Ring Of Royal Grandeur","http://us.diablo3.com/en/item/ring-of-royal-grandeur-Unique_Ring_107_x1", ItemSlot.RING,"Unique_Ring_107_x1"))
            var helm : Item = itemRepository.save(Item("The Shadow's Mask","http://us.diablo3.com/en/item/the-shadows-mask-Unique_Helm_Set_14_x1", ItemSlot.HELM,"Unique_Helm_Set_14_x1"))
            var shoulders: Item = itemRepository.save(Item("Aughild's Power","http://us.diablo3.com/en/artisan/blacksmith/recipe/aughilds-power", ItemSlot.SHOULDERS,"blacksmith/recipe/aughilds-power"))
            var gloves: Item = itemRepository.save(Item("The Shadow's Grasp","http://us.diablo3.com/en/item/the-shadows-grasp-Unique_Gloves_Set_14_x1", ItemSlot.GLOVES,"Unique_Gloves_Set_14_x1"))
            var torso: Item = itemRepository.save(Item("The Shadow's Bane","http://us.diablo3.com/en/item/the-shadows-bane-Unique_Chest_Set_14_x1", ItemSlot.TORSO,"Unique_Chest_Set_14_x1"))
            var belt: Item = itemRepository.save(Item("Chain of Shadows","http://us.diablo3.com/en/item/chain-of-shadows-P4_Unique_Belt_01", ItemSlot.BELT,"P4_Unique_Belt_01"))
            var pants: Item = itemRepository.save(Item("The Shadow's Coil","http://us.diablo3.com/en/item/the-shadows-coil-Unique_Pants_Set_14_x1", ItemSlot.PANTS,"Unique_Pants_Set_14_x1"))
            var boots: Item = itemRepository.save(Item("The Shadow's Heels","http://us.diablo3.com/en/item/the-shadows-heels-Unique_Boots_Set_14_x1", ItemSlot.BOOTS,"Unique_Boots_Set_14_x1"))
            var bracers: Item = itemRepository.save(Item("Aughild's Search","http://us.diablo3.com/en/artisan/blacksmith/recipe/aughilds-search", ItemSlot.BRACERS,"blacksmith/recipe/aughilds-search"))
            var neck: Item = itemRepository.save(Item("The Traveler's Pledge","http://us.diablo3.com/en/item/the-travelers-pledge-Unique_Amulet_008_x1", ItemSlot.NECK,"Unique_Amulet_008_x1"))
            var ringa: Item = itemRepository.save(Item("The Compass Rose","http://us.diablo3.com/en/item/the-compass-rose-Unique_Ring_013_x1", ItemSlot.RING,"Unique_Ring_013_x1"))
            var ringb: Item = itemRepository.save(Item("Convention of Elements","http://us.diablo3.com/en/item/convention-of-elements-P2_Unique_Ring_04", ItemSlot.RING,"P2_Unique_Ring_04"))
            var weapon: Item = itemRepository.save(Item("Karlei's Point","http://us.diablo3.com/en/item/karleis-point-P61_Unique_Dagger_101_x1", ItemSlot.OHWEAPON,"P61_Unique_Dagger_101_x1"))
            var offhand: Item = itemRepository.save(Item("Holy Point Shot","http://us.diablo3.com/en/item/holy-point-shot-P5_Unique_Quiver_004_x1", ItemSlot.HELM,"P5_Unique_Quiver_004_x1"))
            var skill1 = skillRepository.save(Skill("Impale","Demon Hunter","http://us.diablo3.com/en/class/demon-hunter/active/impale","impale",SkillType.ACTIVE, arrayOf(SkillRune("Impact","rune/demon-hunter/impale/b"), SkillRune("Chemical Burn", "rune/demon-hunter/impale/c"))))
            var pass1 = skillRepository.save(Skill("Ambush","Demon Hunter","http://us.diablo3.com/en/class/demon-hunter/passive/ambush","ambush",SkillType.PASSIVE, arrayOf()))
            var build = Build("Impalerino","Demon Hunter", "Brett",helm,shoulders,gloves,boots,pants,belt,torso,bracers,neck,ringa,ringb,weapon,offhand,
            cube1,cube2,cube3,
            skill1,skill1,skill1,skill1,skill1,skill1,
            "Impact","Impact","Impact","Impact","Impact","Impact",pass1,pass1,pass1,pass1,"Roll your way through the rift trying not to get one shot before you one shot elite packs")
            buildRepository.save(build)
        }catch(e : Throwable) {
            println(e)
        }
    }
}