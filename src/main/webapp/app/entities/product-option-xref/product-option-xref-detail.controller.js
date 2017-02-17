(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionXrefDetailController', ProductOptionXrefDetailController);

    ProductOptionXrefDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductOptionXref'];

    function ProductOptionXrefDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductOptionXref) {
        var vm = this;

        vm.productOptionXref = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:productOptionXrefUpdate', function(event, result) {
            vm.productOptionXref = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
