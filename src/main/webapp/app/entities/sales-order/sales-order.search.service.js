(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('SalesOrderSearch', SalesOrderSearch);

    SalesOrderSearch.$inject = ['$resource'];

    function SalesOrderSearch($resource) {
        var resourceUrl =  'api/_search/sales-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
