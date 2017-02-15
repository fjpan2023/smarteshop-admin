(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductBundleDetailController', ProductBundleDetailController);

    ProductBundleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductBundle'];

    function ProductBundleDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductBundle) {
        var vm = this;

        vm.productBundle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:productBundleUpdate', function(event, result) {
            vm.productBundle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
