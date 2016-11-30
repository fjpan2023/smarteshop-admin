(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('RelatedProductSearch', RelatedProductSearch);

    RelatedProductSearch.$inject = ['$resource'];

    function RelatedProductSearch($resource) {
        var resourceUrl =  'api/_search/related-products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
