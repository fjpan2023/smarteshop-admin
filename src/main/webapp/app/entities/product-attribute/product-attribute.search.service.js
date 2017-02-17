(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ProductAttributeSearch', ProductAttributeSearch);

    ProductAttributeSearch.$inject = ['$resource'];

    function ProductAttributeSearch($resource) {
        var resourceUrl =  'api/_search/product-attributes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
