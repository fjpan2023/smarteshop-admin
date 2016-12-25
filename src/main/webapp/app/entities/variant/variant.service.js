(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('Variant', Variant);

    Variant.$inject = ['$resource'];

    function Variant ($resource) {
        var resourceUrl =  'api/variants/:id';

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
