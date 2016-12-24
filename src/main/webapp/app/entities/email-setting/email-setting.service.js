(function() {
    'use strict';
    angular
        .module('smarteshopApp')
        .factory('EmailSetting', EmailSetting);

    EmailSetting.$inject = ['$resource'];

    function EmailSetting ($resource) {
        var resourceUrl =  'api/emailSetting/:id';

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
