(function() {
    'use strict';

    angular
        .module('smarteshopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('email-setting-detail', {
            parent: 'entity',
            url: '/emailSetting',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smarteshopApp.emailSetting.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-setting/email-setting-detail.html',
                    controller: 'EmailSettingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('emailSetting');
                    $translatePartialLoader.addPart('sMTPSecurityEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmailSetting', function($stateParams, EmailSetting) {
                    return EmailSetting.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-setting',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('email-setting-detail.edit', {
            parent: 'email-setting-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-setting/email-setting-dialog.html',
                    controller: 'EmailSettingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailSetting', function(EmailSetting) {
                            return EmailSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('email-setting.edit', {
            parent: 'email-setting',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-setting/email-setting-dialog.html',
                    controller: 'EmailSettingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailSetting', function(EmailSetting) {
                            return EmailSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-setting', null, { reload: 'email-setting' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
