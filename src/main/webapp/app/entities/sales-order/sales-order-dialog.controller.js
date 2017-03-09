(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SalesOrderDialogController', SalesOrderDialogController);

    SalesOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalesOrder'];

    function SalesOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalesOrder) {
        var vm = this;

        vm.salesOrder = entity;
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
            if (vm.salesOrder.id !== null) {
                SalesOrder.update(vm.salesOrder, onSaveSuccess, onSaveError);
            } else {
                SalesOrder.save(vm.salesOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:salesOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
