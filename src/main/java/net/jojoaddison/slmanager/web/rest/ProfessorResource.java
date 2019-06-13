package net.jojoaddison.slmanager.web.rest;
import net.jojoaddison.slmanager.domain.Professor;
import net.jojoaddison.slmanager.repository.ProfessorRepository;
import net.jojoaddison.slmanager.repository.search.ProfessorSearchRepository;
import net.jojoaddison.slmanager.web.rest.errors.BadRequestAlertException;
import net.jojoaddison.slmanager.web.rest.util.HeaderUtil;
import net.jojoaddison.slmanager.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Professor.
 */
@RestController
@RequestMapping("/api")
public class ProfessorResource {

    private final Logger log = LoggerFactory.getLogger(ProfessorResource.class);

    private static final String ENTITY_NAME = "professor";

    private final ProfessorRepository professorRepository;

    private final ProfessorSearchRepository professorSearchRepository;

    public ProfessorResource(ProfessorRepository professorRepository, ProfessorSearchRepository professorSearchRepository) {
        this.professorRepository = professorRepository;
        this.professorSearchRepository = professorSearchRepository;
    }

    /**
     * POST  /professors : Create a new professor.
     *
     * @param professor the professor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professor, or with status 400 (Bad Request) if the professor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professors")
    public ResponseEntity<Professor> createProfessor(@Valid @RequestBody Professor professor) throws URISyntaxException {
        log.debug("REST request to save Professor : {}", professor);
        if (professor.getId() != null) {
            throw new BadRequestAlertException("A new professor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Professor result = professorRepository.save(professor);
        professorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/professors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professors : Updates an existing professor.
     *
     * @param professor the professor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professor,
     * or with status 400 (Bad Request) if the professor is not valid,
     * or with status 500 (Internal Server Error) if the professor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professors")
    public ResponseEntity<Professor> updateProfessor(@Valid @RequestBody Professor professor) throws URISyntaxException {
        log.debug("REST request to update Professor : {}", professor);
        if (professor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Professor result = professorRepository.save(professor);
        professorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professors : get all the professors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of professors in body
     */
    @GetMapping("/professors")
    public ResponseEntity<List<Professor>> getAllProfessors(Pageable pageable) {
        log.debug("REST request to get a page of Professors");
        Page<Professor> page = professorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /professors/:id : get the "id" professor.
     *
     * @param id the id of the professor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professor, or with status 404 (Not Found)
     */
    @GetMapping("/professors/{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable String id) {
        log.debug("REST request to get Professor : {}", id);
        Optional<Professor> professor = professorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(professor);
    }

    /**
     * DELETE  /professors/:id : delete the "id" professor.
     *
     * @param id the id of the professor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professors/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String id) {
        log.debug("REST request to delete Professor : {}", id);
        professorRepository.deleteById(id);
        professorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/professors?query=:query : search for the professor corresponding
     * to the query.
     *
     * @param query the query of the professor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/professors")
    public ResponseEntity<List<Professor>> searchProfessors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Professors for query {}", query);
        Page<Professor> page = professorSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/professors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
