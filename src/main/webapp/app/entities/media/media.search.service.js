(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('MediaSearch', MediaSearch);

    MediaSearch.$inject = ['$resource'];

    function MediaSearch($resource) {
        var resourceUrl =  'api/_search/media/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
