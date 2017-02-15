(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductBundle', ProductBundle);

    ProductBundle.$inject = ['$resource'];

    function ProductBundle ($resource) {
        var resourceUrl =  'api/product-bundles/:id';

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
