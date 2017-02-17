(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductOptionXrefController', ProductOptionXrefController);

    ProductOptionXrefController.$inject = ['$scope', '$state', 'ProductOptionXref', 'ProductOptionXrefSearch'];

    function ProductOptionXrefController ($scope, $state, ProductOptionXref, ProductOptionXrefSearch) {
        var vm = this;

        vm.productOptionXrefs = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ProductOptionXref.query(function(result) {
                vm.productOptionXrefs = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProductOptionXrefSearch.query({query: vm.searchQuery}, function(result) {
                vm.productOptionXrefs = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
