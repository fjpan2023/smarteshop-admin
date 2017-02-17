(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('SkuProductOptionValueXref', SkuProductOptionValueXref);

    SkuProductOptionValueXref.$inject = ['$resource'];

    function SkuProductOptionValueXref ($resource) {
        var resourceUrl =  'api/sku-product-option-value-xrefs/:id';

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
