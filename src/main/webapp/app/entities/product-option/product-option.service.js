(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductOption', ProductOption);

    ProductOption.$inject = ['$resource'];

    function ProductOption ($resource) {
        var resourceUrl =  'api/productOptions/:id';

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
