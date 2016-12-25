(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('VariantValueDialogController', VariantValueDialogController);

    VariantValueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VariantValue', 'Variant'];

    function VariantValueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VariantValue, Variant) {
        var vm = this;

        vm.variantValue = entity;
        vm.clear = clear;
        vm.save = save;
        vm.variants = Variant.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.variantValue.id !== null) {
                VariantValue.update(vm.variantValue, onSaveSuccess, onSaveError);
            } else {
                VariantValue.save(vm.variantValue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:variantValueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
