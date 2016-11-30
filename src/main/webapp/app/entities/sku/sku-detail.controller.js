(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('SkuDetailController', SkuDetailController);

    SkuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sku', 'Product'];

    function SkuDetailController($scope, $rootScope, $stateParams, previousState, entity, Sku, Product) {
        var vm = this;

        vm.sku = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:skuUpdate', function(event, result) {
            vm.sku = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
