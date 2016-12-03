(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('TemplateDialogController', TemplateDialogController);

    TemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Template'];

    function TemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Template) {
        var vm = this;

        vm.template = entity;
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
            if (vm.template.id !== null) {
                Template.update(vm.template, onSaveSuccess, onSaveError);
            } else {
                Template.save(vm.template, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:templateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
