(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('AttachmentSearch', AttachmentSearch);

    AttachmentSearch.$inject = ['$resource'];

    function AttachmentSearch($resource) {
        var resourceUrl =  'api/_search/attachments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
