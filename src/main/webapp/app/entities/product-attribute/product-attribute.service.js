(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductAttribute', ProductAttribute);

    ProductAttribute.$inject = ['$resource'];

    function ProductAttribute ($resource) {
        var resourceUrl =  'api/product-attributes/:id';

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
