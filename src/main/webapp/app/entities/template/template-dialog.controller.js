(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('TemplateDialogController', TemplateDialogController);

    TemplateDialogController.$inject = ['$timeout', '$scope', '$state','$stateParams','previousState', 'entity', 'Template'];

    function TemplateDialogController ($timeout, $scope,  $state,  $stateParams, previousState, entity, Template) {
        var vm = this;

        vm.template = entity;
        vm.clear = clear;
        vm.save = save;
        vm.previousState = previousState.name;
        
        vm.summernoteroptions = {
				height: 300,
		};
        

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
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
