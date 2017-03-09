(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('OrderPaymentDialogController', OrderPaymentDialogController);

    OrderPaymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderPayment'];

    function OrderPaymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderPayment) {
        var vm = this;

        vm.orderPayment = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderPayment.id !== null) {
                OrderPayment.update(vm.orderPayment, onSaveSuccess, onSaveError);
            } else {
                OrderPayment.save(vm.orderPayment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:orderPaymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
