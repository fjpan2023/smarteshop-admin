(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('LocaleDeleteController',LocaleDeleteController);

    LocaleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Locale'];

    function LocaleDeleteController($uibModalInstance, entity, Locale) {
        var vm = this;

        vm.locale = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Locale.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
