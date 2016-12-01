(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('TemplateDetailController', TemplateDetailController);

    TemplateDetailController.$inject = ['$scope', '$rootScope', '$state','$stateParams', 'previousState', 'entity', 'Template'];

    function TemplateDetailController($scope, $rootScope, $state, $stateParams, previousState, entity, Template) {
        var vm = this;

        vm.template = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:templateUpdate', function(event, result) {
            vm.template = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
