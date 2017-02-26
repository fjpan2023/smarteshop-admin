package com.smarteshop.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.smarteshop.domain.QSku;
import com.smarteshop.domain.catalog.Product;
import com.smarteshop.domain.catalog.Sku;
import com.smarteshop.repository.SkuRepository;
import com.smarteshop.repository.search.SkuSearchRepository;
import com.smarteshop.service.SkuService;

/**
 * Service Implementation for managing Sku.
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService{

    private final Logger log = LoggerFactory.getLogger(SkuServiceImpl.class);

    @Inject
    private SkuRepository skuRepository;

    @Inject
    private SkuSearchRepository skuSearchRepository;

    /**
     * Save a sku.
     *
     * @param sku the entity to save
     * @return the persisted entity
     */
    @Override
    public Sku save(Sku sku) {
        log.debug("Request to save Sku : {}", sku);
        Sku result = skuRepository.save(sku);
        skuSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the skus.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Sku> findAll(Pageable pageable) {
        log.debug("Request to get all Skus");
        Page<Sku> result = skuRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one sku by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Sku findOne(Long id) {
        log.debug("Request to get Sku : {}", id);
        Sku sku = skuRepository.findOne(id);
        return sku;
    }

    /**
     *  Delete the  sku by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sku : {}", id);
        skuRepository.delete(id);
        skuSearchRepository.delete(id);
    }

    /**
     * Search for the sku corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Sku> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Skus for query {}", query);
        Page<Sku> result = skuSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    public List<Sku> findSkusByProduct(Product product){
      QSku qSku = QSku.sku;
      Predicate predicate = qSku.product.eq(product);
      return (List<Sku>) this.skuRepository.findAll(predicate);

    }
}
