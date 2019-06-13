package net.jojoaddison.slmanager.repository.search;

import net.jojoaddison.slmanager.domain.Professor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Professor entity.
 */
public interface ProfessorSearchRepository extends ElasticsearchRepository<Professor, String> {
}
