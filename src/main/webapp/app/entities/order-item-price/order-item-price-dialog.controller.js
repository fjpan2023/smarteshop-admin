(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('OrderItemPriceDialogController', OrderItemPriceDialogController);

    OrderItemPriceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderItemPrice'];

    function OrderItemPriceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderItemPrice) {
        var vm = this;

        vm.orderItemPrice = entity;
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
            if (vm.orderItemPrice.id !== null) {
                OrderItemPrice.update(vm.orderItemPrice, onSaveSuccess, onSaveError);
            } else {
                OrderItemPrice.save(vm.orderItemPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:orderItemPriceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
