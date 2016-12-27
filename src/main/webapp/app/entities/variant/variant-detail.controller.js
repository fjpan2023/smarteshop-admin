(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('VariantDetailController', VariantDetailController);

    VariantDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Variant', 'VariantValue'];

    function VariantDetailController($scope, $rootScope, $stateParams, previousState, entity, Variant, VariantValue) {
        var vm = this;

        vm.variant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:variantUpdate', function(event, result) {
            vm.variant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
