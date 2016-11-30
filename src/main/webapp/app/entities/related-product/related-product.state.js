(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('related-product', {
            parent: 'entity',
            url: '/related-product?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.relatedProduct.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/related-product/related-products.html',
                    controller: 'RelatedProductController',
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
                    $translatePartialLoader.addPart('relatedProduct');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('related-product-detail', {
            parent: 'entity',
            url: '/related-product/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.relatedProduct.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/related-product/related-product-detail.html',
                    controller: 'RelatedProductDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('relatedProduct');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RelatedProduct', function($stateParams, RelatedProduct) {
                    return RelatedProduct.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'related-product',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('related-product-detail.edit', {
            parent: 'related-product-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/related-product/related-product-dialog.html',
                    controller: 'RelatedProductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RelatedProduct', function(RelatedProduct) {
                            return RelatedProduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('related-product.new', {
            parent: 'related-product',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/related-product/related-product-dialog.html',
                    controller: 'RelatedProductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                relatedProductId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('related-product', null, { reload: 'related-product' });
                }, function() {
                    $state.go('related-product');
                });
            }]
        })
        .state('related-product.edit', {
            parent: 'related-product',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/related-product/related-product-dialog.html',
                    controller: 'RelatedProductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RelatedProduct', function(RelatedProduct) {
                            return RelatedProduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('related-product', null, { reload: 'related-product' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('related-product.delete', {
            parent: 'related-product',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/related-product/related-product-delete-dialog.html',
                    controller: 'RelatedProductDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RelatedProduct', function(RelatedProduct) {
                            return RelatedProduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('related-product', null, { reload: 'related-product' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
