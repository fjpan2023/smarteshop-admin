(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('LocaleDetailController', LocaleDetailController);

    LocaleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Locale'];

    function LocaleDetailController($scope, $rootScope, $stateParams, previousState, entity, Locale) {
        var vm = this;

        vm.locale = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:localeUpdate', function(event, result) {
            vm.locale = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
