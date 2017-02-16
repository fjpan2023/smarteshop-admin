(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('ManufacturerSearch', ManufacturerSearch);

    ManufacturerSearch.$inject = ['$resource'];

    function ManufacturerSearch($resource) {
        var resourceUrl =  'api/_search/manufacturers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
