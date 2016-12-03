(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('EmailSettingDialogController', EmailSettingDialogController);

    EmailSettingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmailSetting'];

    function EmailSettingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmailSetting) {
        var vm = this;

        vm.emailSetting = entity;
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
            if (vm.emailSetting.id !== null) {
                EmailSetting.update(vm.emailSetting, onSaveSuccess, onSaveError);
            } else {
                EmailSetting.save(vm.emailSetting, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:emailSettingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
