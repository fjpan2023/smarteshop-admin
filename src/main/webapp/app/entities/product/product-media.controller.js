(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('ProductMediaController', ProductMediaController);

    ProductMediaController.$inject = ['$scope', '$state', 'DataUtils', 'Media', 'MediaSearch'];

    function ProductMediaController ($scope, $state, DataUtils, Media, MediaSearch) {
        var vm = this;

        vm.media = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Media.query(function(result) {
                vm.media = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MediaSearch.query({query: vm.searchQuery}, function(result) {
                vm.media = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
