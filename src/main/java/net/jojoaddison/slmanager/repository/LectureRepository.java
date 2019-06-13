package net.jojoaddison.slmanager.repository;

import net.jojoaddison.slmanager.domain.Lecture;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Lecture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LectureRepository extends MongoRepository<Lecture, String> {

}
