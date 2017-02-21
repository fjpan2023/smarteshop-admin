(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Category', 'CategoryProduct'];

    function CategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, Category, CategoryProduct) {
        var vm = this;

        vm.category = entity;
        vm.previousState = previousState.name;
        vm.products = CategoryProduct.query({id:entity.id});

        var unsubscribe = $rootScope.$on('smarteshopApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
