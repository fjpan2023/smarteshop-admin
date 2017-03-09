(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ItemOrderDialogController', ItemOrderDialogController);

    ItemOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ItemOrder'];

    function ItemOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ItemOrder) {
        var vm = this;

        vm.itemOrder = entity;
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
            if (vm.itemOrder.id !== null) {
                ItemOrder.update(vm.itemOrder, onSaveSuccess, onSaveError);
            } else {
                ItemOrder.save(vm.itemOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:itemOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
