(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CustomerDialogController', CustomerDialogController);

    CustomerDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', 'previousState', 'entity', 'Customer'];

    function CustomerDialogController ($timeout, $scope, $state, $stateParams, previousState, entity, Customer) {
        var vm = this;

        vm.customer = entity;
        vm.save = save;
        vm.previousState = previousState.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });


        function save () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:customerUpdate', result);
            vm.isSaving = false
            $state.go(vm.previousState);
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
