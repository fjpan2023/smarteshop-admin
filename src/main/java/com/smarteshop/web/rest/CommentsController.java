package com.smarteshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smarteshop.domain.Comments;
import com.smarteshop.service.CommentsService;
import com.smarteshop.web.rest.util.HeaderUtil;
import com.smarteshop.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Comments.
 */
@RestController
@RequestMapping("/api")
public class CommentsController {

    private final Logger log = LoggerFactory.getLogger(CommentsController.class);
        
    @Inject
    private CommentsService commentsService;

    /**
     * POST  /comments : Create a new comments.
     *
     * @param comments the comments to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comments, or with status 400 (Bad Request) if the comments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comments")
    @Timed
    public ResponseEntity<Comments> createComments(@RequestBody Comments comments) throws URISyntaxException {
        log.debug("REST request to save Comments : {}", comments);
        if (comments.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("comments", "idexists", "A new comments cannot already have an ID")).body(null);
        }
        Comments result = commentsService.save(comments);
        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comments", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comments : Updates an existing comments.
     *
     * @param comments the comments to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comments,
     * or with status 400 (Bad Request) if the comments is not valid,
     * or with status 500 (Internal Server Error) if the comments couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comments")
    @Timed
    public ResponseEntity<Comments> updateComments(@RequestBody Comments comments) throws URISyntaxException {
        log.debug("REST request to update Comments : {}", comments);
        if (comments.getId() == null) {
            return createComments(comments);
        }
        Comments result = commentsService.save(comments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comments", comments.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comments : get all the comments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/comments")
    @Timed
    public ResponseEntity<List<Comments>> getAllComments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Comments");
        Page<Comments> page = commentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comments/:id : get the "id" comments.
     *
     * @param id the id of the comments to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comments, or with status 404 (Not Found)
     */
    @GetMapping("/comments/{id}")
    @Timed
    public ResponseEntity<Comments> getComments(@PathVariable Long id) {
        log.debug("REST request to get Comments : {}", id);
        Comments comments = commentsService.findOne(id);
        return Optional.ofNullable(comments)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comments/:id : delete the "id" comments.
     *
     * @param id the id of the comments to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteComments(@PathVariable Long id) {
        log.debug("REST request to delete Comments : {}", id);
        commentsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comments", id.toString())).build();
    }

    /**
     * SEARCH  /_search/comments?query=:query : search for the comments corresponding
     * to the query.
     *
     * @param query the query of the comments search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/comments")
    @Timed
    public ResponseEntity<List<Comments>> searchComments(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Comments for query {}", query);
        Page<Comments> page = commentsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
