package net.jojoaddison.slmanager.repository.search;

import net.jojoaddison.slmanager.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, String> {
}
