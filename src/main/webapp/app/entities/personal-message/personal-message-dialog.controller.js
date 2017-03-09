(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('PersonalMessageDialogController', PersonalMessageDialogController);

    PersonalMessageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonalMessage'];

    function PersonalMessageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonalMessage) {
        var vm = this;

        vm.personalMessage = entity;
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
            if (vm.personalMessage.id !== null) {
                PersonalMessage.update(vm.personalMessage, onSaveSuccess, onSaveError);
            } else {
                PersonalMessage.save(vm.personalMessage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:personalMessageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
