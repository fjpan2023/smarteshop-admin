(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('CustomerGroupSearch', CustomerGroupSearch);

    CustomerGroupSearch.$inject = ['$resource'];

    function CustomerGroupSearch($resource) {
        var resourceUrl =  'api/_search/customer-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
