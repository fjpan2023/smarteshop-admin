(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ItemOrderDetailController', ItemOrderDetailController);

    ItemOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ItemOrder'];

    function ItemOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, ItemOrder) {
        var vm = this;

        vm.itemOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:itemOrderUpdate', function(event, result) {
            vm.itemOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
