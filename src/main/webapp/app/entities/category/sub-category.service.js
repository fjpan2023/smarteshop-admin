(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('SubCategory', SubCategory);

    SubCategory.$inject = ['$resource'];

    function SubCategory ($resource) {
        var resourceUrl =  'api/categories/:id/subcategories';

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

(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('CategoryProduct', CategoryProduct);

    CategoryProduct.$inject = ['$resource'];

    function CategoryProduct ($resource) {
        var resourceUrl =  'api/categories/:id/products';

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
