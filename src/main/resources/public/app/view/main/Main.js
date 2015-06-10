Ext.define('TrabalhoPraticoEsApp.view.main.Main', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.main',

    requires: [
        'TrabalhoPraticoEsApp.view.main.MainViewModel',
        'TrabalhoPraticoEsApp.view.user.UserController',
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
                    title: 'Group 1',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'menu1',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Group 2',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'menu2',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    title: 'Group 3',
                    items: [
                        {
                            xtype: 'menu',
                            floating: false,
                            itemId: 'menu3',
                            items: [
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
                                    focusable: true
                                },
                                {
                                    xtype: 'menuitem',
                                    text: 'Menu Item',
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