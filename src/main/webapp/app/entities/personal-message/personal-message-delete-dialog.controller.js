(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('PersonalMessageDeleteController',PersonalMessageDeleteController);

    PersonalMessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonalMessage'];

    function PersonalMessageDeleteController($uibModalInstance, entity, PersonalMessage) {
        var vm = this;

        vm.personalMessage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonalMessage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
