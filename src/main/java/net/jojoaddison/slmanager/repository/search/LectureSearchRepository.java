package net.jojoaddison.slmanager.repository.search;

import net.jojoaddison.slmanager.domain.Lecture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lecture entity.
 */
public interface LectureSearchRepository extends ElasticsearchRepository<Lecture, String> {
}
