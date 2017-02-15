(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-bundle', {
            parent: 'entity',
            url: '/product-bundle?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.productBundle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-bundle/product-bundles.html',
                    controller: 'ProductBundleController',
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
                    $translatePartialLoader.addPart('productBundle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('product-bundle-detail', {
            parent: 'entity',
            url: '/product-bundle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.productBundle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-bundle/product-bundle-detail.html',
                    controller: 'ProductBundleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productBundle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProductBundle', function($stateParams, ProductBundle) {
                    return ProductBundle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-bundle',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('product-bundle-detail.edit', {
            parent: 'product-bundle-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-bundle/product-bundle-dialog.html',
                    controller: 'ProductBundleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductBundle', function(ProductBundle) {
                            return ProductBundle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-bundle.new', {
            parent: 'product-bundle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-bundle/product-bundle-dialog.html',
                    controller: 'ProductBundleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                priceModel: null,
                                autoBundle: null,
                                bundlePromotable: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('product-bundle', null, { reload: 'product-bundle' });
                }, function() {
                    $state.go('product-bundle');
                });
            }]
        })
        .state('product-bundle.edit', {
            parent: 'product-bundle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-bundle/product-bundle-dialog.html',
                    controller: 'ProductBundleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductBundle', function(ProductBundle) {
                            return ProductBundle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-bundle', null, { reload: 'product-bundle' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-bundle.delete', {
            parent: 'product-bundle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-bundle/product-bundle-delete-dialog.html',
                    controller: 'ProductBundleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProductBundle', function(ProductBundle) {
                            return ProductBundle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-bundle', null, { reload: 'product-bundle' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
