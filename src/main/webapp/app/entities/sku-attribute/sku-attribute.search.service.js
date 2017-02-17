(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('SkuAttributeSearch', SkuAttributeSearch);

    SkuAttributeSearch.$inject = ['$resource'];

    function SkuAttributeSearch($resource) {
        var resourceUrl =  'api/_search/sku-attributes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
