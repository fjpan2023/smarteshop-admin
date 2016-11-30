(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CommentsDeleteController',CommentsDeleteController);

    CommentsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comments'];

    function CommentsDeleteController($uibModalInstance, entity, Comments) {
        var vm = this;

        vm.comments = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Comments.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
