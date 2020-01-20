package com.diabloplanner

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val itemRepository: ItemRepository,
        val skillRepository: SkillRepository,
        val buildRepository: BuildRepository) {

	@Test
	fun `When findByIdOrNull then return Build`() {

		val juergen = Item("springjuergen","test://url",ItemSlot.HELM)
		entityManager.persist(juergen)
		val dash = Skill("dash","barb")
        entityManager.persist(dash)
        val mockbuild = Build("WW", "barb", 
		author= "Tapir",
		description = "Petit build monk des familles", itemsEquipped=HashMap(mapOf(BuildSlot.HELM to juergen)), legendaryGems=HashMap(mapOf()),skillsEquipped=HashMap(mapOf(SkillSlot.Q to dash)), itemsCubed=HashMap(mapOf(CubeSlot.ARMOR to juergen)), runes =HashMap(mapOf(RuneSlot.Q to "Dashou")))
        entityManager.persist(mockbuild)
        entityManager.flush()
        
		val found = buildRepository.findByIdOrNull(mockbuild.id!!)
		assertThat(found).isEqualTo(mockbuild)
	}

	@Test
	fun `When findByName then return Item`() {
		val juergen = Item("springjuergen","test://url",ItemSlot.HELM)
		entityManager.persist(juergen)
		entityManager.flush()
		val itemFetched = itemRepository.findByName(juergen.name)
		assertThat(itemFetched).isEqualTo(juergen)
    }
    
    @Test
	fun `When findByName then return Skill`() {
		val juergen = Skill("springjuergen","barb")
		entityManager.persist(juergen)
		entityManager.flush()
		val skillFetched =  skillRepository.findByName(juergen.name)
		assertThat(skillFetched).isEqualTo(juergen)
	}
}