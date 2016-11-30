(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .controller('CommentsDetailController', CommentsDetailController);

    CommentsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comments'];

    function CommentsDetailController($scope, $rootScope, $stateParams, previousState, entity, Comments) {
        var vm = this;

        vm.comments = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smarteshopApp:commentsUpdate', function(event, result) {
            vm.comments = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
