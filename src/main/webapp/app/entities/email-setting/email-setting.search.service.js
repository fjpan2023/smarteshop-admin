(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .factory('EmailSettingSearch', EmailSettingSearch);

    EmailSettingSearch.$inject = ['$resource'];

    function EmailSettingSearch($resource) {
        var resourceUrl =  'api/_search/email-settings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
