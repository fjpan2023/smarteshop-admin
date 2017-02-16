(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ManufacturerDialogController', ManufacturerDialogController);

    ManufacturerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Manufacturer'];

    function ManufacturerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Manufacturer) {
        var vm = this;

        vm.manufacturer = entity;
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
            if (vm.manufacturer.id !== null) {
                Manufacturer.update(vm.manufacturer, onSaveSuccess, onSaveError);
            } else {
                Manufacturer.save(vm.manufacturer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:manufacturerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
