(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductOptionValue', ProductOptionValue);

    ProductOptionValue.$inject = ['$resource'];

    function ProductOptionValue ($resource) {
        var resourceUrl =  'api/product-option-values/:id';

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
