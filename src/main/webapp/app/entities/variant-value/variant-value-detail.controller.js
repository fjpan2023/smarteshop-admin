(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('VariantValueDetailController', VariantValueDetailController);

    VariantValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VariantValue', 'Variant'];

    function VariantValueDetailController($scope, $rootScope, $stateParams, previousState, entity, VariantValue, Variant) {
        var vm = this;

        vm.variantValue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:variantValueUpdate', function(event, result) {
            vm.variantValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
