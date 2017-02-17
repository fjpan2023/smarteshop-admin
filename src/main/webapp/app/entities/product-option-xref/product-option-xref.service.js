(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductOptionXref', ProductOptionXref);

    ProductOptionXref.$inject = ['$resource'];

    function ProductOptionXref ($resource) {
        var resourceUrl =  'api/product-option-xrefs/:id';

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
