(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('Product', Product);

    Product.$inject = ['$resource', 'DateUtils'];

    function Product ($resource, DateUtils) {
        var resourceUrl =  'api/products/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.activieStartDate = DateUtils.convertDateTimeFromServer(data.activeStartDate);
                        data.activeEndDate = DateUtils.convertDateTimeFromServer(data.activeEndDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
