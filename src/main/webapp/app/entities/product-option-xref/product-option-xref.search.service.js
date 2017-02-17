(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ProductOptionXrefSearch', ProductOptionXrefSearch);

    ProductOptionXrefSearch.$inject = ['$resource'];

    function ProductOptionXrefSearch($resource) {
        var resourceUrl =  'api/_search/product-option-xrefs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
