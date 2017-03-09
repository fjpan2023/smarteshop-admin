(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('FulfillmentGroupItemDetailController', FulfillmentGroupItemDetailController);

    FulfillmentGroupItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FulfillmentGroupItem'];

    function FulfillmentGroupItemDetailController($scope, $rootScope, $stateParams, previousState, entity, FulfillmentGroupItem) {
        var vm = this;

        vm.fulfillmentGroupItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:fulfillmentGroupItemUpdate', function(event, result) {
            vm.fulfillmentGroupItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
