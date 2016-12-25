(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('VariantDialogController', VariantDialogController);

    VariantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Variant'];

    function VariantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Variant) {
        var vm = this;

        vm.variant = entity;
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
            if (vm.variant.id !== null) {
                Variant.update(vm.variant, onSaveSuccess, onSaveError);
            } else {
                Variant.save(vm.variant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:variantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
