(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('PersonalMessageDetailController', PersonalMessageDetailController);

    PersonalMessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonalMessage'];

    function PersonalMessageDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonalMessage) {
        var vm = this;

        vm.personalMessage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:personalMessageUpdate', function(event, result) {
            vm.personalMessage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
