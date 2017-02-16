(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ProductOptionSearch', ProductOptionSearch);

    ProductOptionSearch.$inject = ['$resource'];

    function ProductOptionSearch($resource) {
        var resourceUrl =  'api/_search/product-options/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
