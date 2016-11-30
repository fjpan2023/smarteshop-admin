(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('BrandSearch', BrandSearch);

    BrandSearch.$inject = ['$resource'];

    function BrandSearch($resource) {
        var resourceUrl =  'api/_search/brands/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
