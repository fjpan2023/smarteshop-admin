(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('EmailSettingDetailController', EmailSettingDetailController);

    EmailSettingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmailSetting'];

    function EmailSettingDetailController($scope, $rootScope, $stateParams, previousState, entity, EmailSetting) {
        var vm = this;

        vm.emailSetting = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:emailSettingUpdate', function(event, result) {
            vm.emailSetting = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
