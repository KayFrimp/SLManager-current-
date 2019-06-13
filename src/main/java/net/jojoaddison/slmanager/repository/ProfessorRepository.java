package net.jojoaddison.slmanager.repository;

import net.jojoaddison.slmanager.domain.Professor;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Professor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessorRepository extends MongoRepository<Professor, String> {

}
