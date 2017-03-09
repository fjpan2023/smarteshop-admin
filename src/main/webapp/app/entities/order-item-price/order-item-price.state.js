(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-item-price', {
            parent: 'entity',
            url: '/order-item-price?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.orderItemPrice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-item-price/order-item-prices.html',
                    controller: 'OrderItemPriceController',
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
                    $translatePartialLoader.addPart('orderItemPrice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-item-price-detail', {
            parent: 'entity',
            url: '/order-item-price/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.orderItemPrice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-item-price/order-item-price-detail.html',
                    controller: 'OrderItemPriceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderItemPrice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderItemPrice', function($stateParams, OrderItemPrice) {
                    return OrderItemPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-item-price',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-item-price-detail.edit', {
            parent: 'order-item-price-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-price/order-item-price-dialog.html',
                    controller: 'OrderItemPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderItemPrice', function(OrderItemPrice) {
                            return OrderItemPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-item-price.new', {
            parent: 'order-item-price',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-price/order-item-price-dialog.html',
                    controller: 'OrderItemPriceDialogController',
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
                    $state.go('order-item-price', null, { reload: 'order-item-price' });
                }, function() {
                    $state.go('order-item-price');
                });
            }]
        })
        .state('order-item-price.edit', {
            parent: 'order-item-price',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-price/order-item-price-dialog.html',
                    controller: 'OrderItemPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderItemPrice', function(OrderItemPrice) {
                            return OrderItemPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-item-price', null, { reload: 'order-item-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-item-price.delete', {
            parent: 'order-item-price',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-price/order-item-price-delete-dialog.html',
                    controller: 'OrderItemPriceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderItemPrice', function(OrderItemPrice) {
                            return OrderItemPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-item-price', null, { reload: 'order-item-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
