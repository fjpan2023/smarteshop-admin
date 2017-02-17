(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('SkuProductOptionValueXrefSearch', SkuProductOptionValueXrefSearch);

    SkuProductOptionValueXrefSearch.$inject = ['$resource'];

    function SkuProductOptionValueXrefSearch($resource) {
        var resourceUrl =  'api/_search/sku-product-option-value-xrefs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
