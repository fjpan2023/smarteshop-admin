(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('ProductRelatedProduct', ProductRelatedProduct);

    ProductRelatedProduct.$inject = ['$resource', 'DateUtils'];

    function ProductRelatedProduct ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id/relatedProducts';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.activeStartDate = DateUtils.convertDateTimeFromServer(data.activeStartDate);
                        data.activeEndDate = DateUtils.convertDateTimeFromServer(data.activeEndDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'generateSkusByBatch':{method:'POST'}
        });
    }
})();
