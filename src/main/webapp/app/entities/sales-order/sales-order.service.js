(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('SalesOrder', SalesOrder);

    SalesOrder.$inject = ['$resource'];

    function SalesOrder ($resource) {
        var resourceUrl =  'api/sales-orders/:id';

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
