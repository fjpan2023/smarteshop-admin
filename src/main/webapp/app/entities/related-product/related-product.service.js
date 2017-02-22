(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('RelatedProduct', RelatedProduct);

    RelatedProduct.$inject = ['$resource'];

    function RelatedProduct ($resource) {
        var resourceUrl =  'api/products/:id/relatedProducts/';

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
