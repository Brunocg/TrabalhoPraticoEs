Ext.define('TrabalhoPraticoEsApp.view.main.Main', {
    extend: 'Ext.container.Container',
    plugins: 'viewport',
    requires: [
        'TrabalhoPraticoEsApp.view.main.MainController',
        'TrabalhoPraticoEsApp.view.main.MainModel'
    ],

    xtype: 'app-main',

    controller: 'main',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        bind: {
            title: '{name}'
        },
        region: 'west',
        html: '<ul><li>=]</li></ul>',
        width: 250,
        split: true,
        tbar: [{
            text: 'Sair',
            handler: 'onClickButton'
        }]
    },{
        region: 'center',
        xtype: 'tabpanel',
        items:[{
            title: 'Tab 1',
            html: '<h2>=] =] =] =]</h2>'
        }]
    }]
});