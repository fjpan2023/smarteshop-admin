(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('CommentsSearch', CommentsSearch);

    CommentsSearch.$inject = ['$resource'];

    function CommentsSearch($resource) {
        var resourceUrl =  'api/_search/comments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
