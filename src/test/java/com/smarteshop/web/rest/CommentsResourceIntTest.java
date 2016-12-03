package com.smarteshop.web.rest;

import com.smarteshop.SmarteshopApp;

import com.smarteshop.domain.Comments;
import com.smarteshop.repository.CommentsRepository;
import com.smarteshop.service.CommentsService;
import com.smarteshop.repository.search.CommentsSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommentsResource REST controller.
 *
 * @see CommentsController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarteshopApp.class)
public class CommentsResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HANDLE = false;
    private static final Boolean UPDATED_IS_HANDLE = true;

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    @Inject
    private CommentsRepository commentsRepository;

    @Inject
    private CommentsService commentsService;

    @Inject
    private CommentsSearchRepository commentsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCommentsMockMvc;

    private Comments comments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommentsController commentsResource = new CommentsController();
        ReflectionTestUtils.setField(commentsResource, "commentsService", commentsService);
        this.restCommentsMockMvc = MockMvcBuilders.standaloneSetup(commentsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createEntity(EntityManager em) {
        Comments comments = new Comments()
                .email(DEFAULT_EMAIL)
                .userName(DEFAULT_USER_NAME)
                .content(DEFAULT_CONTENT)
                .isHandle(DEFAULT_IS_HANDLE)
                .subject(DEFAULT_SUBJECT);
        return comments;
    }

    @Before
    public void initTest() {
        commentsSearchRepository.deleteAll();
        comments = createEntity(em);
    }

    @Test
    @Transactional
    public void createComments() throws Exception {
        int databaseSizeBeforeCreate = commentsRepository.findAll().size();

        // Create the Comments

        restCommentsMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comments)))
            .andExpect(status().isCreated());

        // Validate the Comments in the database
        List<Comments> comments = commentsRepository.findAll();
        assertThat(comments).hasSize(databaseSizeBeforeCreate + 1);
        Comments testComments = comments.get(comments.size() - 1);
        assertThat(testComments.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testComments.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testComments.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testComments.isIsHandle()).isEqualTo(DEFAULT_IS_HANDLE);
        assertThat(testComments.getSubject()).isEqualTo(DEFAULT_SUBJECT);

        // Validate the Comments in ElasticSearch
        Comments commentsEs = commentsSearchRepository.findOne(testComments.getId());
        assertThat(commentsEs).isEqualToComparingFieldByField(testComments);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get all the comments
        restCommentsMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comments.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].isHandle").value(hasItem(DEFAULT_IS_HANDLE.booleanValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())));
    }

    @Test
    @Transactional
    public void getComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get the comments
        restCommentsMockMvc.perform(get("/api/comments/{id}", comments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comments.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.isHandle").value(DEFAULT_IS_HANDLE.booleanValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComments() throws Exception {
        // Get the comments
        restCommentsMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComments() throws Exception {
        // Initialize the database
        commentsService.save(comments);

        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();

        // Update the comments
        Comments updatedComments = commentsRepository.findOne(comments.getId());
        updatedComments
                .email(UPDATED_EMAIL)
                .userName(UPDATED_USER_NAME)
                .content(UPDATED_CONTENT)
                .isHandle(UPDATED_IS_HANDLE)
                .subject(UPDATED_SUBJECT);

        restCommentsMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComments)))
            .andExpect(status().isOk());

        // Validate the Comments in the database
        List<Comments> comments = commentsRepository.findAll();
        assertThat(comments).hasSize(databaseSizeBeforeUpdate);
        Comments testComments = comments.get(comments.size() - 1);
        assertThat(testComments.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testComments.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testComments.isIsHandle()).isEqualTo(UPDATED_IS_HANDLE);
        assertThat(testComments.getSubject()).isEqualTo(UPDATED_SUBJECT);

        // Validate the Comments in ElasticSearch
        Comments commentsEs = commentsSearchRepository.findOne(testComments.getId());
        assertThat(commentsEs).isEqualToComparingFieldByField(testComments);
    }

    @Test
    @Transactional
    public void deleteComments() throws Exception {
        // Initialize the database
        commentsService.save(comments);

        int databaseSizeBeforeDelete = commentsRepository.findAll().size();

        // Get the comments
        restCommentsMockMvc.perform(delete("/api/comments/{id}", comments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean commentsExistsInEs = commentsSearchRepository.exists(comments.getId());
        assertThat(commentsExistsInEs).isFalse();

        // Validate the database is empty
        List<Comments> comments = commentsRepository.findAll();
        assertThat(comments).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchComments() throws Exception {
        // Initialize the database
        commentsService.save(comments);

        // Search the comments
        restCommentsMockMvc.perform(get("/api/_search/comments?query=id:" + comments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comments.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].isHandle").value(hasItem(DEFAULT_IS_HANDLE.booleanValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())));
    }
}
