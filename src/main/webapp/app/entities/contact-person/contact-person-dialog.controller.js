(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ContactPersonDialogController', ContactPersonDialogController);

    ContactPersonDialogController.$inject = ['$timeout', '$scope','$state', '$stateParams','previousState', 'entity', 'ContactPerson'];

    function ContactPersonDialogController ($timeout, $scope, $state, $stateParams, previousState, entity, ContactPerson) {
        var vm = this;
        vm.contactPerson = entity;
        vm.save = save;
        vm.previousState = previousState.name;
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.contactPerson.id !== null) {
                ContactPerson.update(vm.contactPerson, onSaveSuccess, onSaveError);
            } else {
                ContactPerson.save(vm.contactPerson, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smarteshopApp:contactPersonUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }
})();
