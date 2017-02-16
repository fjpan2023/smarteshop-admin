(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionDetailController', ProductOptionDetailController);

    ProductOptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductOption', 'Product'];

    function ProductOptionDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductOption, Product) {
        var vm = this;

        vm.productOption = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:productOptionUpdate', function(event, result) {
            vm.productOption = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
