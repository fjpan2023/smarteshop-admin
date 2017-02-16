(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('manufacturer', {
            parent: 'entity',
            url: '/manufacturer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.manufacturer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/manufacturer/manufacturers.html',
                    controller: 'ManufacturerController',
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
                    $translatePartialLoader.addPart('manufacturer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('manufacturer-detail', {
            parent: 'entity',
            url: '/manufacturer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.manufacturer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/manufacturer/manufacturer-detail.html',
                    controller: 'ManufacturerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('manufacturer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Manufacturer', function($stateParams, Manufacturer) {
                    return Manufacturer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'manufacturer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('manufacturer-detail.edit', {
            parent: 'manufacturer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/manufacturer/manufacturer-dialog.html',
                    controller: 'ManufacturerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Manufacturer', function(Manufacturer) {
                            return Manufacturer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('manufacturer.new', {
            parent: 'manufacturer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/manufacturer/manufacturer-dialog.html',
                    controller: 'ManufacturerDialogController',
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
                    $state.go('manufacturer', null, { reload: 'manufacturer' });
                }, function() {
                    $state.go('manufacturer');
                });
            }]
        })
        .state('manufacturer.edit', {
            parent: 'manufacturer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/manufacturer/manufacturer-dialog.html',
                    controller: 'ManufacturerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Manufacturer', function(Manufacturer) {
                            return Manufacturer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('manufacturer', null, { reload: 'manufacturer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('manufacturer.delete', {
            parent: 'manufacturer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/manufacturer/manufacturer-delete-dialog.html',
                    controller: 'ManufacturerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Manufacturer', function(Manufacturer) {
                            return Manufacturer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('manufacturer', null, { reload: 'manufacturer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
