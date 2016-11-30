(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('SkuSearch', SkuSearch);

    SkuSearch.$inject = ['$resource'];

    function SkuSearch($resource) {
        var resourceUrl =  'api/_search/skus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
