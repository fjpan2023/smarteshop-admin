package com.smarteshop.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smarteshop.domain.Customer;
import com.smarteshop.domain.OrderItem;
import com.smarteshop.domain.OrderPayment;
import com.smarteshop.domain.SalesOrder;
import com.smarteshop.dto.OrderItemDTO;
import com.smarteshop.exception.BusinessException;
import com.smarteshop.payment.PaymentType;
import com.smarteshop.payment.Referenced;
import com.smarteshop.salesorder.OrderStatus;

/**
 * Service Interface for managing SalesOrder.
 */
public interface SalesOrderService {

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save
     * @return the persisted entity
     */
    SalesOrder save(SalesOrder salesOrder);

    /**
     *  Get all the salesOrders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SalesOrder> findAll(Pageable pageable);

    /**
     *  Get the "id" salesOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SalesOrder findOne(Long id);

    /**
     *  Delete the "id" salesOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the salesOrder corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SalesOrder> search(String query, Pageable pageable);
    
    /**
     * Creates a new SalesOrder for the given customer. Generally, you will want to use the customer
     * that is on the current request, which can be grabbed by utilizing the CustomerState 
     * utility class.
     * 
     * The default Broadleaf implementation of this method will provision a new SalesOrder in the 
     * database and set the current customer as the owner of the order. If the customer has an
     * email address associated with their profile, that will be copied as well. If the customer
     * is a new, anonymous customer, his username will be set to his database id.
     * 
     * @see org.broadleafcommerce.profile.web.core.CustomerState#getCustomer()
     * 
     * @param customer
     * @return the newly created order
     */
    public SalesOrder createNewCartForCustomer(Customer customer);

    /**
     * Creates a new SalesOrder for the given customer with the given name. Typically, this represents
     * a "wishlist" order that the customer can save but not check out with.
     *
     * @param name the wishlist name
     * @param customer
     * @param args additional arguments to be used by Broadleaf extensions
     * @return the newly created named order
     */
    public SalesOrder createNamedOrderForCustomer(String name, Customer customer);

    /**
     * Looks up an SalesOrder by the given customer and a specified order name.
     * 
     * This is typically used to retrieve a "wishlist" order.
     * 
     * @see #createNamedOrderForCustomer(String name, Customer customer)
     * 
     * @param name
     * @param customer
     * @param args additional arguments to be used by Broadleaf extensions
     * @return the named order requested
     */
    public SalesOrder findNamedOrderForCustomer(String name, Customer customer);
    
    /**
     * Looks up an SalesOrder by its database id
     * 
     * @param orderId
     * @return the requested SalesOrder
     */
    public SalesOrder findOrderById(Long orderId);

    /**
     * Looks up a list of Orders by their database ids
     *
     * @param orderIds
     * @return a list of Orders
     */
    public List<SalesOrder> findOrdersByIds(List<Long> orderIds);

    /**
     * Looks up an SalesOrder by its database id
     * and optionally calls refresh to ensure that the entity manager pulls the instance from the DB and not from cache
     *
     * @param orderId
     * @return the requested SalesOrder
     */
    public SalesOrder findOrderById(Long orderId, boolean refresh);
    
    /**
     * Looks up the current shopping cart for the customer. Note that a shopping cart is
     * simply an SalesOrder with OrderStatus = IN_PROCESS. If for some reason the given customer
     * has more than one current IN_PROCESS SalesOrder, the default Broadleaf implementation will
     * return the first match found. Furthermore, also note that the current shopping cart
     * for a customer must never be named -- an SalesOrder with a non-null "name" property indicates
     * that it is a wishlist and not a shopping cart.
     * 
     * @param customer
     * @return the current shopping cart for the customer
     */
    public SalesOrder findCartForCustomer(Customer customer);
    
    /**
     * Looks up all Orders for the specified customer, regardless of current OrderStatus
     * 
     * @param customer
     * @return the requested Orders
     */
    public List<SalesOrder> findOrdersForCustomer(Customer customer);
    
    /**
     * Looks up all Orders for the specified customer that are in the specified OrderStatus.
     * 
     * @param customer
     * @param status
     * @return the requested Orders
     */
    public List<SalesOrder> findOrdersForCustomer(Customer customer, OrderStatus status);
    
    /**
     * Looks up Orders and returns the order matching the given orderNumber
     * 
     * @param orderNumber
     * @return the requested SalesOrder
     */
    public SalesOrder findOrderByOrderNumber(String orderNumber);
    
    /**
     * Returns all OrderPayment objects that are associated with the given order
     * 
     * @param order
     * @return the list of all OrderPayment objects
     */
    public List<OrderPayment> findPaymentsForOrder(SalesOrder order);

    /**
     * Associates a given OrderPayment with an SalesOrder and then saves the order. Note that it is acceptable for the 
     * securePaymentInfo to be null. For example, if the secure credit card details are 
     * handled by a third party, a given application may never have associated securePaymentInfos
     * 
     * @param order
     * @param payment
     * @param securePaymentInfo - null if it doesn't exist
     * @return the persisted version of the OrderPayment
     */
    public OrderPayment addPaymentToOrder(SalesOrder order, OrderPayment payment, Referenced securePaymentInfo);
    
    /**
     * Persists the given order to the database. If the priceOrder flag is set to true,
     * the pricing workflow will execute before the order is written to the database.
     * Generally, you will want to price the order in every request scope once, and
     * preferrably on the last call to save() for performance reasons.
     * 
     * However, if you have logic that depends on the SalesOrder being priced, there are no
     * issues with saving as many times as necessary.
     * 
     * @param order
     * @param priceOrder
     * @return the persisted SalesOrder, which will be a different instance than the SalesOrder passed in
     * @throws PricingException
     */
    public SalesOrder save(SalesOrder order, Boolean priceOrder);
    
    /**
     * Saves the given <b>order</b> while optionally repricing the order (meaning, going through the pricing workflow)
     * along with updating the prices of individual items in the order, as opposed to just pricing taxes/shipping/etc.
     * 
     * @param order
     * @param priceOrder
     * @param repriceItems whether or not to reprice the items inside of the order via {@link SalesOrder#updatePrices()}
     * @return the persisted SalesOrder, which will be a different instance than the SalesOrder passed in
     * @throws PricingException
     */
    public SalesOrder save(SalesOrder order, boolean priceOrder, boolean repriceItems);
    
    /**
     * Deletes the given order. Note that the default Broadleaf implementation in 
     * OrderServiceImpl will actually remove the SalesOrder instance from the database.
     * 
     * @param order
     */
    public void cancelOrder(SalesOrder order);
    
    /**
     * Adds the given OfferCode to the order. Optionally prices the order as well.
     * 
     * @param order
     * @param offerCode
     * @param priceOrder
     * @return the modified SalesOrder
     * @throws PricingException
     * @throws OfferMaxUseExceededException
     * @throws OfferException 
     */
 //   public SalesOrder addOfferCode(SalesOrder order, OfferCode offerCode, boolean priceOrder) throws PricingException, OfferException;
    
    /**
     * Adds the given OfferCodes to the order. Optionally prices the order as well.
     * 
     * @param order
     * @param offerCodes
     * @param priceOrder
     * @return
     * @throws PricingException
     * @throws OfferMaxUseExceededException
     * @throws OfferException 
     */
 //   public SalesOrder addOfferCodes(SalesOrder order, List<OfferCode> offerCodes, boolean priceOrder) throws PricingException, OfferException;

    /**
     * Remove the given OfferCode for the order. Optionally prices the order as well.
     * 
     * @param order
     * @param offerCode 
     * @param priceOrder
     * @return the modified SalesOrder
     * @throws PricingException
     */
 //   public SalesOrder removeOfferCode(SalesOrder order, OfferCode offerCode, boolean priceOrder) throws PricingException;
    
    /**
     * Removes all offer codes for the given order. Optionally prices the order as well.
     * 
     * @param order
     * @param priceOrder
     * @return the modified SalesOrder
     * @throws PricingException
     */
  //  public SalesOrder removeAllOfferCodes(SalesOrder order, boolean priceOrder) throws PricingException;
    
    /**
     * The null order is the default order for all customers when they initially
     * enter the site. Upon the first addition of a product to a cart, a non-null order
     * will be provisioned for the user.
     * 
     * @see org.broadleafcommerce.core.order.domain.NullOrderImpl for more information
     * 
     * @return a shared, static, unmodifiable NullOrder
     */
    public SalesOrder getNullOrder();
    
    /**
     * @see #setAutomaticallyMergeLikeItems(boolean)
     * 
     * @return whether or not like-items will be automatically merged
     */
    public boolean getAutomaticallyMergeLikeItems();

    /**
     * When set to true, the system when items are added to the cart, they will
     * automatically be merged. For example, when a user adds an item to the cart
     * and then adds the item again, the item will have its quantity changed to 2
     * instead of the cart containing two separate items.
     *
     * If this logic needs to be more complex, it is possible to extend the behavior by
     * overriding OrderOfferProcessor.buildIdentifier().
     *
     * @param automaticallyMergeLikeItems
     */
    public void setAutomaticallyMergeLikeItems(boolean automaticallyMergeLikeItems);
    
    /**
     * Changes the OrderStatus to SUBMITTED
     * 
     * @param order to confirm
     * @return the order that was confirmed
     */
    public SalesOrder confirmOrder(SalesOrder order);
    
    /**
     * Looks through the given order and returns the latest added OrderItem that matches on the skuId
     * and productId. Generally, this is used to retrieve the OrderItem that was just added to the cart.
     * The default Broadleaf implementation will attempt to match on skuId first, and failing that, it will
     * look at the productId.
     * 
     * Note that the behavior is slightly undeterministic in the case that {@link setAutomaticallyMergeLikeItems}
     * is set to true and the last added sku matches on a previously added sku. In this case, the sku that has the
     * merged items would be returned, so the total quantity of the OrderItem might not match exactly what was 
     * just added.
     * 
     * @param order
     * @param skuId
     * @param productId
     * @return the best matching OrderItem with highest index in the list of OrderItems in the order
     */
    public OrderItem findLastMatchingItem(SalesOrder order, Long skuId, Long productId);
    
    /**
     * Adds a GiftWrapItem to the order based on the itemRequest. A GiftWrapItem is a product (for example,
     * a "Gift Box with Red Ribbon") that contains a list of OrderItems that should be wrapped by this
     * GiftWrapItem.
     * 
     * The OrderItems must already exist and belong to an order before they are able to be wrapped by the
     * GiftWrapItem
     * 
     * @param order
     * @param itemRequest
     * @param priceOrder
     * @return the GiftWrapItem instance that was created and attached to the order
     * @throws PricingException
     */
  //  public OrderItem addGiftWrapItemToOrder(SalesOrder order, GiftWrapOrderItemRequest itemRequest, boolean priceOrder) throws PricingException;
    
    /**
     * Initiates the addItem workflow that will attempt to add the given quantity of the specified item
     * to the SalesOrder. The item to be added can be determined in a few different ways. For example, the 
     * SKU can be specified directly or it can be determine based on a Product and potentially some
     * specified ProductOptions for that given product.
     *
     * The minimum required parameters for OrderItemRequest are: productId and quantity or alternatively, skuId and quantity
     *
     * When priceOrder is false, the system will not reprice the order.   This is more performant in
     * cases such as bulk adds where the repricing could be done for the last item only.
     * 
     * This method differs from the {@link #addItemWithPriceOverrides(Long, OrderItemDTO, boolean)} in that it
     * will clear any values set on the {@link OrderItemDTO} for the overrideSalePrice or overrideRetailPrice.
     * 
     * This design is intended to ensure that override pricing is not called by mistake.   Implementors should
     * use this method when no manual price overrides are allowed.
     *
     * @see OrderItemDTO
     * @param orderId
     * @param orderItemRequest
     * @param priceOrder
     * @return the order the item was added to
     * @throws WorkflowException 
     * @throws Throwable 
     */
    public SalesOrder addItem(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder);
    
    /**
     * Initiates the addItem workflow that will attempt to add the given quantity of the specified item
     * to the SalesOrder. The item to be added can be determined in a few different ways. For example, the 
     * SKU can be specified directly or it can be determine based on a Product and potentially some
     * specified ProductOptions for that given product.
     *
     * The minimum required parameters for OrderItemRequest are: productId and quantity or alternatively, skuId and quantity
     *
     * When priceOrder is false, the system will not reprice the order.   This is more performant in
     * cases such as bulk adds where the repricing could be done for the last item only.
     * 
     * As opposed to the {@link #addItem(Long, OrderItemDTO, boolean)} method, this method allows
     * the passed in {@link OrderItemDTO} to contain values for the overrideSale or overrideRetail
     * price fields.
     * 
     * This design is intended to ensure that override pricing is not called by mistake.   Implementors should
     * use this method when manual price overrides are allowed.
     *
     * @see OrderItemDTO
     * @param orderId
     * @param orderItemRequest
     * @param priceOrder
     * @return the order the item was added to
     * @throws WorkflowException 
     * @throws Throwable 
     */
    public SalesOrder addItemWithPriceOverrides(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder);

    /**
     * Initiates the updateItem workflow that will attempt to update the item quantity for the specified
     * OrderItem in the given SalesOrder. The new quantity is specified in the OrderItemDTO
     * 
     * Minimum required parameters for OrderItemRequest: orderItemId, quantity
     * 
     * @see OrderItemDTO
     * @param orderId
     * @param orderItemRequest
     * @param priceOrder
     * @return the order the item was added to
     * @throws UpdateCartException
     * @throws RemoveFromCartException 
     */
    public SalesOrder updateItemQuantity(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder);
    
    /**
     * Initiates the removeItem workflow that will attempt to remove the specified OrderItem from 
     * the given SalesOrder
     * 
     * @see OrderItemDTO
     * @param orderId
     * @param orderItemId
     * @param priceOrder
     * @return the order the item was added to
     * @throws RemoveFromCartException 
     */
    public SalesOrder removeItem(Long orderId, Long orderItemId, boolean priceOrder);
    
    /**
     * @see #setMoveNamedOrderItems(boolean)
     * @return whether items will be removed from the wishlist when added to the cart
     */
    public boolean isMoveNamedOrderItems();

    /**
     * Determines whether or not items will be removed from the named order (wishlist)
     * when they are moved to the Customer's current cart.
     * 
     * @param moveNamedOrderItems
     */
    public void setMoveNamedOrderItems(boolean moveNamedOrderItems);

    /**
     * @see #setDeleteEmptyNamedOrders(boolean)
     * @return whether empty wishlists will be deleted automatically
     */
    public boolean isDeleteEmptyNamedOrders();

    /**
     * Sets whether or not to delete named orders once all items have been removed.
     * 
     * @param deleteEmptyNamedOrders
     */
    public void setDeleteEmptyNamedOrders(boolean deleteEmptyNamedOrders);

    /**
     * Adds the passed in orderItem to the current cart for the same Customer that owns the
     * named order. This method will remove the item from the wishlist based on whether the 
     * {@link setMoveNamedOrderItems} flag is set.
     * 
     * Note that if an item was in a wishlist and is no longer able to be added to the cart,
     * the item will still be removed from the wishlist.
     * 
     * Note that this method does not change the association of the OrderItems to the new
     * order -- instead, those OrderItems is completely removed and a new OrderItem that mirrors
     * it is created.
     * 
     * @param namedOrder 
     * @param orderItem 
     * @param priceOrder 
     * @return the cart with the requested orderItem added to it
     * @throws RemoveFromCartException
     * @throws AddToCartException
     */
    public SalesOrder addItemFromNamedOrder(SalesOrder namedOrder, OrderItem orderItem, boolean priceOrder);
    
    /**
     * This method performs the same function as addItemFromNamedOrder(SalesOrder, OrderItem, boolean)
     * except that instead of adding all of the quantity from the named order to the cart, it will
     * only add/move the specific quantity requested.
     * 
     * @see #addItemFromNamedOrder(SalesOrder, OrderItem, boolean)
     * 
     * @param namedOrder 
     * @param orderItem 
     * @param quantity
     * @param priceOrder 
     * @return the cart with the requested orderItem added to it
     * @throws RemoveFromCartException
     * @throws AddToCartException
     * @throws UpdateCartException 
     */
    public SalesOrder addItemFromNamedOrder(SalesOrder namedOrder, OrderItem orderItem, int quantity, boolean priceOrder) throws BusinessException;

    /**
     * Adds all orderItems to the current cart from the same Customer that owns the named
     * order. This method will remove the item from the wishlist based on whether the 
     * {@link setMoveNamedOrderItems} flag is set.
     * 
     * Note that any items that are in the wishlist but are no longer able to be added to a cart
     * will still be removed from the wishlist.
     * 
     * Note that this method does not change the association of the OrderItems to the new
     * order -- instead, those OrderItems is completely removed and a new OrderItem that mirrors
     * it is created.
     * 
     * @param namedOrder
     * @param priceOrder
     * @return
     * @throws RemoveFromCartException
     * @throws AddToCartException
     */
    public SalesOrder addAllItemsFromNamedOrder(SalesOrder namedOrder, boolean priceOrder);

    /**
     * Deletes all the OrderPayment Info's on the order.
     *
     * @param order
     */
    public void removeAllPaymentsFromOrder(SalesOrder order);

    /**
     * Deletes the OrderPayment Info of the passed in type from the order
     * Note that this method will also delete any associated Secure OrderPayment Infos if necessary.
     *
     * @param order
     * @param paymentInfoType
     */
    public void removePaymentsFromOrder(SalesOrder order, PaymentType paymentInfoType);

    /**
     * Deletes the OrderPayment Info from the order.
     * Note that this method will also delete any associated Secure OrderPayment Infos if necessary.
     *
     * @param order
     * @param paymentInfo
     */
    public void removePaymentFromOrder(SalesOrder order, OrderPayment paymentInfo);

    public void deleteOrder(SalesOrder cart);

    SalesOrder removeInactiveItems(Long orderId, boolean priceOrder);

    /**
     * Since required product option can be added after the item is in the cart, we use this method 
     * to apply product option on an existing item in the cart. No validation will happen at this time, as the validation 
     * at checkout will take care of any missing product options. 
     * 
     * @param orderId
     * @param orderItemRequestDTO
     * @param priceOrder
     * @return SalesOrder
     * @throws UpdateCartException
     */
    SalesOrder updateProductOptionsForItem(Long orderId, OrderItemDTO orderItemRequestDTO, boolean priceOrder);

    /**
     * This debugging method will print out a console-suitable representation of the current state of the order, including
     * the items in the order and all pricing related information for the order.
     * 
     * @param order the order to debug
     * @param log the Log to use to print a debug-level message
     */
    public void printOrder(SalesOrder order, Log log);

    /**
     * Invokes the extension handler of the same name to provide the ability for a module to throw an exception
     * and interrupt a cart operation.
     * 
     * @param cart
     */
    public void preValidateCartOperation(SalesOrder cart);

    /**
     * Invokes the extension handler of the same name to provide the ability for a module to throw an exception
     * and interrupt an update quantity operation.
     * 
     * @param cart
     */
  //  public void preValidateUpdateQuantityOperation(SalesOrder cart, OrderItemDTO dto);
    
    /**
     * Detaches the given order from the current entity manager and then reloads a fresh version from
     * the database.
     * 
     * @param order
     * @return the newly read order
     */
    public SalesOrder reloadOrder(SalesOrder order);


    /**
     * @see OrderDao#acquireLock(SalesOrder)
     * @param order
     * @return whether or not the lock was acquired
     */
    public boolean acquireLock(SalesOrder order);

    /**
     * @see OrderDao#releaseLock(SalesOrder)
     * @param order
     * @return whether or not the lock was released
     */
    public boolean releaseLock(SalesOrder order);
}
