package nl.my888.resume.repository.work;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by ejl on 04/07/15.
 */
public interface SkillRepository extends CrudRepository<Skill, Long> {

    Skill findByName(String name);

}
