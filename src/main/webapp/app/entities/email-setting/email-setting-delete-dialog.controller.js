(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('EmailSettingDeleteController',EmailSettingDeleteController);

    EmailSettingDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmailSetting'];

    function EmailSettingDeleteController($uibModalInstance, entity, EmailSetting) {
        var vm = this;

        vm.emailSetting = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmailSetting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
