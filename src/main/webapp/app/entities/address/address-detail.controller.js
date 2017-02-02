(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('AddressDetailController', AddressDetailController);

    AddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Address'];

    function AddressDetailController($scope, $rootScope, $stateParams, previousState, entity, Address) {
        var vm = this;

        vm.address = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:addressUpdate', function(event, result) {
            vm.address = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
