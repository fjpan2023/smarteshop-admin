(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('OrderItemPrice', OrderItemPrice);

    OrderItemPrice.$inject = ['$resource'];

    function OrderItemPrice ($resource) {
        var resourceUrl =  'api/order-item-prices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
