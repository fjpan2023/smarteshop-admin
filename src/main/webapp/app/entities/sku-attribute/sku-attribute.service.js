(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('SkuAttribute', SkuAttribute);

    SkuAttribute.$inject = ['$resource'];

    function SkuAttribute ($resource) {
        var resourceUrl =  'api/sku-attributes/:id';

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
