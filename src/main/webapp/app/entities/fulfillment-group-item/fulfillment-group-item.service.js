(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('FulfillmentGroupItem', FulfillmentGroupItem);

    FulfillmentGroupItem.$inject = ['$resource'];

    function FulfillmentGroupItem ($resource) {
        var resourceUrl =  'api/fulfillment-group-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
