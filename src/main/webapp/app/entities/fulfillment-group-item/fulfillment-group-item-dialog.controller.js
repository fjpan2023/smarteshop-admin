(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('FulfillmentGroupItemDialogController', FulfillmentGroupItemDialogController);

    FulfillmentGroupItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FulfillmentGroupItem'];

    function FulfillmentGroupItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FulfillmentGroupItem) {
        var vm = this;

        vm.fulfillmentGroupItem = entity;
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
            if (vm.fulfillmentGroupItem.id !== null) {
                FulfillmentGroupItem.update(vm.fulfillmentGroupItem, onSaveSuccess, onSaveError);
            } else {
                FulfillmentGroupItem.save(vm.fulfillmentGroupItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:fulfillmentGroupItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
