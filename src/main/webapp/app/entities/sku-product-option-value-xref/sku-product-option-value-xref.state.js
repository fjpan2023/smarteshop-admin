(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sku-product-option-value-xref', {
            parent: 'entity',
            url: '/sku-product-option-value-xref?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.skuProductOptionValueXref.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sku-product-option-value-xref/sku-product-option-value-xrefs.html',
                    controller: 'SkuProductOptionValueXrefController',
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
                    $translatePartialLoader.addPart('skuProductOptionValueXref');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sku-product-option-value-xref-detail', {
            parent: 'entity',
            url: '/sku-product-option-value-xref/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.skuProductOptionValueXref.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sku-product-option-value-xref/sku-product-option-value-xref-detail.html',
                    controller: 'SkuProductOptionValueXrefDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('skuProductOptionValueXref');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SkuProductOptionValueXref', function($stateParams, SkuProductOptionValueXref) {
                    return SkuProductOptionValueXref.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sku-product-option-value-xref',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sku-product-option-value-xref-detail.edit', {
            parent: 'sku-product-option-value-xref-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-product-option-value-xref/sku-product-option-value-xref-dialog.html',
                    controller: 'SkuProductOptionValueXrefDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SkuProductOptionValueXref', function(SkuProductOptionValueXref) {
                            return SkuProductOptionValueXref.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sku-product-option-value-xref.new', {
            parent: 'sku-product-option-value-xref',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-product-option-value-xref/sku-product-option-value-xref-dialog.html',
                    controller: 'SkuProductOptionValueXrefDialogController',
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
                    $state.go('sku-product-option-value-xref', null, { reload: 'sku-product-option-value-xref' });
                }, function() {
                    $state.go('sku-product-option-value-xref');
                });
            }]
        })
        .state('sku-product-option-value-xref.edit', {
            parent: 'sku-product-option-value-xref',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-product-option-value-xref/sku-product-option-value-xref-dialog.html',
                    controller: 'SkuProductOptionValueXrefDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SkuProductOptionValueXref', function(SkuProductOptionValueXref) {
                            return SkuProductOptionValueXref.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sku-product-option-value-xref', null, { reload: 'sku-product-option-value-xref' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sku-product-option-value-xref.delete', {
            parent: 'sku-product-option-value-xref',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-product-option-value-xref/sku-product-option-value-xref-delete-dialog.html',
                    controller: 'SkuProductOptionValueXrefDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SkuProductOptionValueXref', function(SkuProductOptionValueXref) {
                            return SkuProductOptionValueXref.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sku-product-option-value-xref', null, { reload: 'sku-product-option-value-xref' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
