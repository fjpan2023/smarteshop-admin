(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('OrderItemPriceSearch', OrderItemPriceSearch);

    OrderItemPriceSearch.$inject = ['$resource'];

    function OrderItemPriceSearch($resource) {
        var resourceUrl =  'api/_search/order-item-prices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
