(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('VariantValueSearch', VariantValueSearch);

    VariantValueSearch.$inject = ['$resource'];

    function VariantValueSearch($resource) {
        var resourceUrl =  'api/_search/variant-values/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
