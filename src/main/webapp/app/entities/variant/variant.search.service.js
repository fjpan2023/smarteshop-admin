(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('VariantSearch', VariantSearch);

    VariantSearch.$inject = ['$resource'];

    function VariantSearch($resource) {
        var resourceUrl =  'api/_search/variants/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
