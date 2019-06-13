package net.jojoaddison.slmanager.repository.search;

import net.jojoaddison.slmanager.domain.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Student entity.
 */
public interface StudentSearchRepository extends ElasticsearchRepository<Student, String> {
}
