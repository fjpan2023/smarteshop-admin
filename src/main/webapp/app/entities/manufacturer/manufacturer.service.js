(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('Manufacturer', Manufacturer);

    Manufacturer.$inject = ['$resource'];

    function Manufacturer ($resource) {
        var resourceUrl =  'api/manufacturers/:id';

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
