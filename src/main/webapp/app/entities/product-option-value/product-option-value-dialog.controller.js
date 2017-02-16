(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionValueDialogController', ProductOptionValueDialogController);

    ProductOptionValueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductOptionValue'];

    function ProductOptionValueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductOptionValue) {
        var vm = this;

        vm.productOptionValue = entity;
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
            if (vm.productOptionValue.id !== null) {
                ProductOptionValue.update(vm.productOptionValue, onSaveSuccess, onSaveError);
            } else {
                ProductOptionValue.save(vm.productOptionValue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:productOptionValueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
