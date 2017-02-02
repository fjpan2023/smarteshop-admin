(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CurrencyDialogController', CurrencyDialogController);

    CurrencyDialogController.$inject = ['$timeout', '$scope','$state', '$stateParams', 'previousState', 'entity', 'Currency'];

    function CurrencyDialogController ($timeout, $scope, $state, $stateParams, previousState, entity, Currency) {
        var vm = this;
        vm.currency = entity;
        vm.clear = clear;
        vm.save = save;
        vm.previousState = previousState.name;
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
        }

        function save () {
            vm.isSaving = true;
            if (vm.currency.id !== null) {
                Currency.update(vm.currency, onSaveSuccess, onSaveError);
            } else {
                Currency.save(vm.currency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:currencyUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
