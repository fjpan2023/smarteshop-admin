(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('OrderPaymentSearch', OrderPaymentSearch);

    OrderPaymentSearch.$inject = ['$resource'];

    function OrderPaymentSearch($resource) {
        var resourceUrl =  'api/_search/order-payments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
