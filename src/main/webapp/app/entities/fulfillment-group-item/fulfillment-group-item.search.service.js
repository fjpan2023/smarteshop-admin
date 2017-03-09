(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('FulfillmentGroupItemSearch', FulfillmentGroupItemSearch);

    FulfillmentGroupItemSearch.$inject = ['$resource'];

    function FulfillmentGroupItemSearch($resource) {
        var resourceUrl =  'api/_search/fulfillment-group-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
