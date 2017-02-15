(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ProductBundleSearch', ProductBundleSearch);

    ProductBundleSearch.$inject = ['$resource'];

    function ProductBundleSearch($resource) {
        var resourceUrl =  'api/_search/product-bundles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
