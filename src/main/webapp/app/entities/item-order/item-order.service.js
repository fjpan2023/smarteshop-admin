(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ItemOrder', ItemOrder);

    ItemOrder.$inject = ['$resource'];

    function ItemOrder ($resource) {
        var resourceUrl =  'api/item-orders/:id';

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
