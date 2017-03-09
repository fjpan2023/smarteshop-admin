(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('Locale', Locale);

    Locale.$inject = ['$resource'];

    function Locale ($resource) {
        var resourceUrl =  'api/locales/:id';

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
