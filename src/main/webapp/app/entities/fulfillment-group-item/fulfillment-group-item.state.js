(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fulfillment-group-item', {
            parent: 'entity',
            url: '/fulfillment-group-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.fulfillmentGroupItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fulfillment-group-item/fulfillment-group-items.html',
                    controller: 'FulfillmentGroupItemController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fulfillmentGroupItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('fulfillment-group-item-detail', {
            parent: 'entity',
            url: '/fulfillment-group-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.fulfillmentGroupItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fulfillment-group-item/fulfillment-group-item-detail.html',
                    controller: 'FulfillmentGroupItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fulfillmentGroupItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FulfillmentGroupItem', function($stateParams, FulfillmentGroupItem) {
                    return FulfillmentGroupItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fulfillment-group-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fulfillment-group-item-detail.edit', {
            parent: 'fulfillment-group-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fulfillment-group-item/fulfillment-group-item-dialog.html',
                    controller: 'FulfillmentGroupItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FulfillmentGroupItem', function(FulfillmentGroupItem) {
                            return FulfillmentGroupItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fulfillment-group-item.new', {
            parent: 'fulfillment-group-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fulfillment-group-item/fulfillment-group-item-dialog.html',
                    controller: 'FulfillmentGroupItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fulfillment-group-item', null, { reload: 'fulfillment-group-item' });
                }, function() {
                    $state.go('fulfillment-group-item');
                });
            }]
        })
        .state('fulfillment-group-item.edit', {
            parent: 'fulfillment-group-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fulfillment-group-item/fulfillment-group-item-dialog.html',
                    controller: 'FulfillmentGroupItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FulfillmentGroupItem', function(FulfillmentGroupItem) {
                            return FulfillmentGroupItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fulfillment-group-item', null, { reload: 'fulfillment-group-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fulfillment-group-item.delete', {
            parent: 'fulfillment-group-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fulfillment-group-item/fulfillment-group-item-delete-dialog.html',
                    controller: 'FulfillmentGroupItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FulfillmentGroupItem', function(FulfillmentGroupItem) {
                            return FulfillmentGroupItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fulfillment-group-item', null, { reload: 'fulfillment-group-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
