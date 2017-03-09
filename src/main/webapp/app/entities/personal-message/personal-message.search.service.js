(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('PersonalMessageSearch', PersonalMessageSearch);

    PersonalMessageSearch.$inject = ['$resource'];

    function PersonalMessageSearch($resource) {
        var resourceUrl =  'api/_search/personal-messages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
