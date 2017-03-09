package com.smarteshop.service.impl;

import com.smarteshop.service.SalesOrderService;
import com.smarteshop.domain.Customer;
import com.smarteshop.domain.OrderItem;
import com.smarteshop.domain.OrderPayment;
import com.smarteshop.domain.SalesOrder;
import com.smarteshop.dto.OrderItemDTO;
import com.smarteshop.exception.BusinessException;
import com.smarteshop.payment.PaymentType;
import com.smarteshop.payment.Referenced;
import com.smarteshop.repository.SalesOrderRepository;
import com.smarteshop.repository.search.SalesOrderSearchRepository;
import com.smarteshop.salesorder.OrderStatus;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SalesOrder.
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService{

    private final Logger log = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
    
    @Inject
    private SalesOrderRepository salesOrderRepository;

    @Inject
    private SalesOrderSearchRepository salesOrderSearchRepository;

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save
     * @return the persisted entity
     */
    public SalesOrder save(SalesOrder salesOrder) {
        log.debug("Request to save SalesOrder : {}", salesOrder);
        SalesOrder result = salesOrderRepository.save(salesOrder);
        salesOrderSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the salesOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SalesOrder> findAll(Pageable pageable) {
        log.debug("Request to get all SalesOrders");
        Page<SalesOrder> result = salesOrderRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one salesOrder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalesOrder findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        SalesOrder salesOrder = salesOrderRepository.findOne(id);
        return salesOrder;
    }

    /**
     *  Delete the  salesOrder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);
        salesOrderRepository.delete(id);
        salesOrderSearchRepository.delete(id);
    }

    /**
     * Search for the salesOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalesOrder> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalesOrders for query {}", query);
        Page<SalesOrder> result = salesOrderSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

	@Override
	public SalesOrder createNewCartForCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder createNamedOrderForCustomer(String name, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder findNamedOrderForCustomer(String name, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder findOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SalesOrder> findOrdersByIds(List<Long> orderIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder findOrderById(Long orderId, boolean refresh) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder findCartForCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SalesOrder> findOrdersForCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SalesOrder> findOrdersForCustomer(Customer customer, OrderStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder findOrderByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderPayment> findPaymentsForOrder(SalesOrder order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderPayment addPaymentToOrder(SalesOrder order, OrderPayment payment, Referenced securePaymentInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder save(SalesOrder order, Boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder save(SalesOrder order, boolean priceOrder, boolean repriceItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelOrder(SalesOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesOrder getNullOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAutomaticallyMergeLikeItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutomaticallyMergeLikeItems(boolean automaticallyMergeLikeItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesOrder confirmOrder(SalesOrder order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderItem findLastMatchingItem(SalesOrder order, Long skuId, Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder addItem(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder addItemWithPriceOverrides(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder updateItemQuantity(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder removeItem(Long orderId, Long orderItemId, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMoveNamedOrderItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMoveNamedOrderItems(boolean moveNamedOrderItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDeleteEmptyNamedOrders() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDeleteEmptyNamedOrders(boolean deleteEmptyNamedOrders) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesOrder addItemFromNamedOrder(SalesOrder namedOrder, OrderItem orderItem, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder addItemFromNamedOrder(SalesOrder namedOrder, OrderItem orderItem, int quantity,
			boolean priceOrder) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder addAllItemsFromNamedOrder(SalesOrder namedOrder, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllPaymentsFromOrder(SalesOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePaymentsFromOrder(SalesOrder order, PaymentType paymentInfoType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePaymentFromOrder(SalesOrder order, OrderPayment paymentInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(SalesOrder cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesOrder removeInactiveItems(Long orderId, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalesOrder updateProductOptionsForItem(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printOrder(SalesOrder order, Log log) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preValidateCartOperation(SalesOrder cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesOrder reloadOrder(SalesOrder order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean acquireLock(SalesOrder order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseLock(SalesOrder order) {
		// TODO Auto-generated method stub
		return false;
	}
}
