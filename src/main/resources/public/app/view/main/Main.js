Ext.define('TrabalhoPraticoEsApp.view.main.Main', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.main',

    requires: [
        'TrabalhoPraticoEsApp.view.main.MainViewModel',
        'Ext.menu.Menu',
        'Ext.menu.Item'
    ],

    viewModel: {
        type: 'main'
    },
    itemId: 'mainView',
    layout: 'border',

    items: [
        {
            xtype: 'panel',
            region: 'north',
            height: 100,
            itemId: 'headerPanel',
            title: 'Header'
        },
        {
            xtype: 'panel',
            region: 'west',
            split: true,
            itemId: 'menuPanel',
            width: 250,
            layout: 'accordion',
            collapseDirection: 'left',
            title: 'Menu',
            items: [
                {
                    xtype: 'panel',
                    title: 'Perfil',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'Perfil',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Gerentes',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'gerentes',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Colaboradores',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'colaboradores',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Moderadores',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'moderadores',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Especialidades',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'especialidades',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Projetos',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'projetos',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Gerenciar Contas',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'gerenciar_contas',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Todos',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Novo',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'panel',
            flex: 1,
            region: 'center',
            itemId: 'contentPanel',
            title: 'Content'
        }
    ]

});