package net.jojoaddison.slmanager.web.rest;
import net.jojoaddison.slmanager.domain.Lecture;
import net.jojoaddison.slmanager.repository.LectureRepository;
import net.jojoaddison.slmanager.repository.search.LectureSearchRepository;
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
 * REST controller for managing Lecture.
 */
@RestController
@RequestMapping("/api")
public class LectureResource {

    private final Logger log = LoggerFactory.getLogger(LectureResource.class);

    private static final String ENTITY_NAME = "lecture";

    private final LectureRepository lectureRepository;

    private final LectureSearchRepository lectureSearchRepository;

    public LectureResource(LectureRepository lectureRepository, LectureSearchRepository lectureSearchRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureSearchRepository = lectureSearchRepository;
    }

    /**
     * POST  /lectures : Create a new lecture.
     *
     * @param lecture the lecture to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lecture, or with status 400 (Bad Request) if the lecture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lectures")
    public ResponseEntity<Lecture> createLecture(@Valid @RequestBody Lecture lecture) throws URISyntaxException {
        log.debug("REST request to save Lecture : {}", lecture);
        if (lecture.getId() != null) {
            throw new BadRequestAlertException("A new lecture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lecture result = lectureRepository.save(lecture);
        lectureSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lectures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lectures : Updates an existing lecture.
     *
     * @param lecture the lecture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lecture,
     * or with status 400 (Bad Request) if the lecture is not valid,
     * or with status 500 (Internal Server Error) if the lecture couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lectures")
    public ResponseEntity<Lecture> updateLecture(@Valid @RequestBody Lecture lecture) throws URISyntaxException {
        log.debug("REST request to update Lecture : {}", lecture);
        if (lecture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lecture result = lectureRepository.save(lecture);
        lectureSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lecture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lectures : get all the lectures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lectures in body
     */
    @GetMapping("/lectures")
    public ResponseEntity<List<Lecture>> getAllLectures(Pageable pageable) {
        log.debug("REST request to get a page of Lectures");
        Page<Lecture> page = lectureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lectures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lectures/:id : get the "id" lecture.
     *
     * @param id the id of the lecture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lecture, or with status 404 (Not Found)
     */
    @GetMapping("/lectures/{id}")
    public ResponseEntity<Lecture> getLecture(@PathVariable String id) {
        log.debug("REST request to get Lecture : {}", id);
        Optional<Lecture> lecture = lectureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lecture);
    }

    /**
     * DELETE  /lectures/:id : delete the "id" lecture.
     *
     * @param id the id of the lecture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable String id) {
        log.debug("REST request to delete Lecture : {}", id);
        lectureRepository.deleteById(id);
        lectureSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/lectures?query=:query : search for the lecture corresponding
     * to the query.
     *
     * @param query the query of the lecture search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lectures")
    public ResponseEntity<List<Lecture>> searchLectures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lectures for query {}", query);
        Page<Lecture> page = lectureSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lectures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
