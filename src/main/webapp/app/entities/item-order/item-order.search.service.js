(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ItemOrderSearch', ItemOrderSearch);

    ItemOrderSearch.$inject = ['$resource'];

    function ItemOrderSearch($resource) {
        var resourceUrl =  'api/_search/item-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
