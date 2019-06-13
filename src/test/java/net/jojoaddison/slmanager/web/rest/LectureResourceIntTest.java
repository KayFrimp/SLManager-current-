package net.jojoaddison.slmanager.web.rest;

import net.jojoaddison.slmanager.SlmanagerApp;

import net.jojoaddison.slmanager.domain.Lecture;
import net.jojoaddison.slmanager.repository.LectureRepository;
import net.jojoaddison.slmanager.repository.search.LectureSearchRepository;
import net.jojoaddison.slmanager.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;


import static net.jojoaddison.slmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LectureResource REST controller.
 *
 * @see LectureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SlmanagerApp.class)
public class LectureResourceIntTest {

    private static final String DEFAULT_COURSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_HOURS = 1;
    private static final Integer UPDATED_CREDIT_HOURS = 2;

    @Autowired
    private LectureRepository lectureRepository;

    /**
     * This repository is mocked in the net.jojoaddison.slmanager.repository.search test package.
     *
     * @see net.jojoaddison.slmanager.repository.search.LectureSearchRepositoryMockConfiguration
     */
    @Autowired
    private LectureSearchRepository mockLectureSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restLectureMockMvc;

    private Lecture lecture;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LectureResource lectureResource = new LectureResource(lectureRepository, mockLectureSearchRepository);
        this.restLectureMockMvc = MockMvcBuilders.standaloneSetup(lectureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lecture createEntity() {
        Lecture lecture = new Lecture()
            .courseName(DEFAULT_COURSE_NAME)
            .creditHours(DEFAULT_CREDIT_HOURS);
        return lecture;
    }

    @Before
    public void initTest() {
        lectureRepository.deleteAll();
        lecture = createEntity();
    }

    @Test
    public void createLecture() throws Exception {
        int databaseSizeBeforeCreate = lectureRepository.findAll().size();

        // Create the Lecture
        restLectureMockMvc.perform(post("/api/lectures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecture)))
            .andExpect(status().isCreated());

        // Validate the Lecture in the database
        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeCreate + 1);
        Lecture testLecture = lectureList.get(lectureList.size() - 1);
        assertThat(testLecture.getCourseName()).isEqualTo(DEFAULT_COURSE_NAME);
        assertThat(testLecture.getCreditHours()).isEqualTo(DEFAULT_CREDIT_HOURS);

        // Validate the Lecture in Elasticsearch
        verify(mockLectureSearchRepository, times(1)).save(testLecture);
    }

    @Test
    public void createLectureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lectureRepository.findAll().size();

        // Create the Lecture with an existing ID
        lecture.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLectureMockMvc.perform(post("/api/lectures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecture)))
            .andExpect(status().isBadRequest());

        // Validate the Lecture in the database
        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lecture in Elasticsearch
        verify(mockLectureSearchRepository, times(0)).save(lecture);
    }

    @Test
    public void checkCourseNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lectureRepository.findAll().size();
        // set the field null
        lecture.setCourseName(null);

        // Create the Lecture, which fails.

        restLectureMockMvc.perform(post("/api/lectures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecture)))
            .andExpect(status().isBadRequest());

        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCreditHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = lectureRepository.findAll().size();
        // set the field null
        lecture.setCreditHours(null);

        // Create the Lecture, which fails.

        restLectureMockMvc.perform(post("/api/lectures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecture)))
            .andExpect(status().isBadRequest());

        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLectures() throws Exception {
        // Initialize the database
        lectureRepository.save(lecture);

        // Get all the lectureList
        restLectureMockMvc.perform(get("/api/lectures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecture.getId())))
            .andExpect(jsonPath("$.[*].courseName").value(hasItem(DEFAULT_COURSE_NAME.toString())))
            .andExpect(jsonPath("$.[*].creditHours").value(hasItem(DEFAULT_CREDIT_HOURS)));
    }
    
    @Test
    public void getLecture() throws Exception {
        // Initialize the database
        lectureRepository.save(lecture);

        // Get the lecture
        restLectureMockMvc.perform(get("/api/lectures/{id}", lecture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lecture.getId()))
            .andExpect(jsonPath("$.courseName").value(DEFAULT_COURSE_NAME.toString()))
            .andExpect(jsonPath("$.creditHours").value(DEFAULT_CREDIT_HOURS));
    }

    @Test
    public void getNonExistingLecture() throws Exception {
        // Get the lecture
        restLectureMockMvc.perform(get("/api/lectures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLecture() throws Exception {
        // Initialize the database
        lectureRepository.save(lecture);

        int databaseSizeBeforeUpdate = lectureRepository.findAll().size();

        // Update the lecture
        Lecture updatedLecture = lectureRepository.findById(lecture.getId()).get();
        updatedLecture
            .courseName(UPDATED_COURSE_NAME)
            .creditHours(UPDATED_CREDIT_HOURS);

        restLectureMockMvc.perform(put("/api/lectures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLecture)))
            .andExpect(status().isOk());

        // Validate the Lecture in the database
        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeUpdate);
        Lecture testLecture = lectureList.get(lectureList.size() - 1);
        assertThat(testLecture.getCourseName()).isEqualTo(UPDATED_COURSE_NAME);
        assertThat(testLecture.getCreditHours()).isEqualTo(UPDATED_CREDIT_HOURS);

        // Validate the Lecture in Elasticsearch
        verify(mockLectureSearchRepository, times(1)).save(testLecture);
    }

    @Test
    public void updateNonExistingLecture() throws Exception {
        int databaseSizeBeforeUpdate = lectureRepository.findAll().size();

        // Create the Lecture

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLectureMockMvc.perform(put("/api/lectures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecture)))
            .andExpect(status().isBadRequest());

        // Validate the Lecture in the database
        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lecture in Elasticsearch
        verify(mockLectureSearchRepository, times(0)).save(lecture);
    }

    @Test
    public void deleteLecture() throws Exception {
        // Initialize the database
        lectureRepository.save(lecture);

        int databaseSizeBeforeDelete = lectureRepository.findAll().size();

        // Delete the lecture
        restLectureMockMvc.perform(delete("/api/lectures/{id}", lecture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lecture> lectureList = lectureRepository.findAll();
        assertThat(lectureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lecture in Elasticsearch
        verify(mockLectureSearchRepository, times(1)).deleteById(lecture.getId());
    }

    @Test
    public void searchLecture() throws Exception {
        // Initialize the database
        lectureRepository.save(lecture);
        when(mockLectureSearchRepository.search(queryStringQuery("id:" + lecture.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lecture), PageRequest.of(0, 1), 1));
        // Search the lecture
        restLectureMockMvc.perform(get("/api/_search/lectures?query=id:" + lecture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecture.getId())))
            .andExpect(jsonPath("$.[*].courseName").value(hasItem(DEFAULT_COURSE_NAME)))
            .andExpect(jsonPath("$.[*].creditHours").value(hasItem(DEFAULT_CREDIT_HOURS)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lecture.class);
        Lecture lecture1 = new Lecture();
        lecture1.setId("id1");
        Lecture lecture2 = new Lecture();
        lecture2.setId(lecture1.getId());
        assertThat(lecture1).isEqualTo(lecture2);
        lecture2.setId("id2");
        assertThat(lecture1).isNotEqualTo(lecture2);
        lecture1.setId(null);
        assertThat(lecture1).isNotEqualTo(lecture2);
    }
}
