(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ProductOptionValueSearch', ProductOptionValueSearch);

    ProductOptionValueSearch.$inject = ['$resource'];

    function ProductOptionValueSearch($resource) {
        var resourceUrl =  'api/_search/product-option-values/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
