(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('VariantValue', VariantValue);

    VariantValue.$inject = ['$resource'];

    function VariantValue ($resource) {
        var resourceUrl =  'api/variant-values/:id';

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
