(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sku-attribute', {
            parent: 'entity',
            url: '/sku-attribute?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.skuAttribute.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sku-attribute/sku-attributes.html',
                    controller: 'SkuAttributeController',
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
                    $translatePartialLoader.addPart('skuAttribute');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sku-attribute-detail', {
            parent: 'entity',
            url: '/sku-attribute/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.skuAttribute.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sku-attribute/sku-attribute-detail.html',
                    controller: 'SkuAttributeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('skuAttribute');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SkuAttribute', function($stateParams, SkuAttribute) {
                    return SkuAttribute.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sku-attribute',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sku-attribute-detail.edit', {
            parent: 'sku-attribute-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-attribute/sku-attribute-dialog.html',
                    controller: 'SkuAttributeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SkuAttribute', function(SkuAttribute) {
                            return SkuAttribute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sku-attribute.new', {
            parent: 'sku-attribute',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-attribute/sku-attribute-dialog.html',
                    controller: 'SkuAttributeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sku-attribute', null, { reload: 'sku-attribute' });
                }, function() {
                    $state.go('sku-attribute');
                });
            }]
        })
        .state('sku-attribute.edit', {
            parent: 'sku-attribute',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-attribute/sku-attribute-dialog.html',
                    controller: 'SkuAttributeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SkuAttribute', function(SkuAttribute) {
                            return SkuAttribute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sku-attribute', null, { reload: 'sku-attribute' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sku-attribute.delete', {
            parent: 'sku-attribute',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku-attribute/sku-attribute-delete-dialog.html',
                    controller: 'SkuAttributeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SkuAttribute', function(SkuAttribute) {
                            return SkuAttribute.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sku-attribute', null, { reload: 'sku-attribute' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
