(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('OrderPayment', OrderPayment);

    OrderPayment.$inject = ['$resource'];

    function OrderPayment ($resource) {
        var resourceUrl =  'api/order-payments/:id';

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
