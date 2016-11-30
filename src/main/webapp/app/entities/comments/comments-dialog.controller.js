(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CommentsDialogController', CommentsDialogController);

    CommentsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comments'];

    function CommentsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Comments) {
        var vm = this;

        vm.comments = entity;
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
            if (vm.comments.id !== null) {
                Comments.update(vm.comments, onSaveSuccess, onSaveError);
            } else {
                Comments.save(vm.comments, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:commentsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
