(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionValueDetailController', ProductOptionValueDetailController);

    ProductOptionValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductOptionValue'];

    function ProductOptionValueDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductOptionValue) {
        var vm = this;

        vm.productOptionValue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:productOptionValueUpdate', function(event, result) {
            vm.productOptionValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
